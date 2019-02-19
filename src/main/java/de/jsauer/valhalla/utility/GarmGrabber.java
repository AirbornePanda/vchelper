package de.jsauer.valhalla.utility;

import com.vaadin.external.org.slf4j.Logger;
import com.vaadin.external.org.slf4j.LoggerFactory;
import de.jsauer.valhalla.backend.entities.Hero;
import de.jsauer.valhalla.backend.entities.LimitBurst;
import de.jsauer.valhalla.backend.enums.EDamageType;
import de.jsauer.valhalla.backend.enums.EElement;
import de.jsauer.valhalla.backend.enums.EFieldEffect;
import de.jsauer.valhalla.backend.enums.EGender;
import de.jsauer.valhalla.backend.enums.ERace;
import de.jsauer.valhalla.backend.enums.ETrait;
import de.jsauer.valhalla.backend.enums.EType;
import de.jsauer.valhalla.backend.repositories.HeroRepository;
import de.jsauer.valhalla.backend.repositories.LimitBurstRepository;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Grabs information from <a href="https://garm.ml/">Garm</a>.
 */
@Service
public class GarmGrabber {
    /**
     * {@link Logger} for this class.
     */
    private static final Logger log = LoggerFactory.getLogger(GarmGrabber.class);
    /**
     * Link to the site containing basic {@link Hero} information.
     */
    private static String HERO_PAGE = "https://garm.ml/hero/all";
    /**
     * Link to the hero detail pages.
     */
    private static String HERO_DETAIL_PAGE = "https://garm.ml/hero/";
    /**
     * Link to details of a {@link LimitBurst}.
     */
    private static String LIMITBURST_PAGE = "https://garm.ml/limit-burst/";
    /**
     * Link to the hero portraits.
     */
    private static String IMAGE_URL = "https://garm.ml/sites/default/files/styles/hero_icon_small/public/";
    /**
     * Fallback link to the normal hero portraits.
     */
    private static String IMAGE_FALLBACK_URL = "https://garm.ml/sites/default/files/icon_character_";
    /**
     * Fallback link to the awakened hero portraits.
     */
    private static String IMAGE_AWAKENED_FALLBACK_URL = "https://garm.ml/sites/default/files/styles/hero_icon_small/public/icon_character_";

    @Autowired
    private HeroRepository heroRepository;

    @Autowired
    private LimitBurstRepository limitBurstRepository;

    /**
     * Grabs basic hero information
     */
    public void grabBasicHeroInformation() {
        int currentPage = 1;
        Document heroSite = null;
        boolean error = false;

        try {
            heroSite = Jsoup.connect(HERO_PAGE).get();
        } catch (IOException e) {
            log.error("Could not connect to hero page " + currentPage + ". Aborting import");
            error = true;
        }

        while (heroSite != null && !error) {
            Elements heroElements = heroSite.select("div.views-fluid-grid > ul > li");
            //If there are no heroes found we reached the end and can exit the loop by setting the error flag
            if (heroElements.size() < 1) {
                error = true;
            }
            for (Element heroElement : heroElements) {

                String garmId = heroElement.select("div.views-field-field-profile-image > span > a").first().attr("href").substring(6);
                //Check if the hero is alreade in the database
                Hero hero = heroRepository.findOneByGarmId(garmId);

                if (hero == null) {
                    //Hero is not in database, so create a new one
                    hero = new Hero();
                    hero.setGarmId(garmId);
                    hero.setName(heroElement.select("div.views-field-title > span").text());
                }

                log.debug("Currently working on: " + hero.getName());
                log.debug("Started getting the portrait of " + hero.getName());

                //Retrieve the hero image
                try {
                    InputStream heroImageStream = grabHeroImage(hero);
                    if (heroImageStream != null) {
                        hero.setImage(IOUtils.toByteArray(heroImageStream));
                        heroImageStream.close();
                    }
                } catch (IOException e) {
                    log.error("Could no grab hero image. Will be empty");
                }

                log.debug("Finished getting the portrait of " + hero.getName());

                grabHeroDetails(hero);

                //Save the hero
                heroRepository.save(hero);
            }

            currentPage++;
            try {
                heroSite = Jsoup.connect(HERO_PAGE + "?page=" + currentPage).get();
            } catch (IOException e) {
                log.error("Could not connect to hero page " + currentPage + ". Aborting import");
                error = true;
            }
        }
    }

    /**
     * Grabs the portrait image of a {@link Hero}.
     *
     * @param hero the {@link Hero} to grab the image for
     * @return the image as an {@link InputStream} or null if no image could be retrieved
     * @throws MalformedURLException if a problem with an {@link URL} occurs
     */
    private InputStream grabHeroImage(final Hero hero) throws IOException {
        String garmId = hero.getGarmId();

        URL imageSource = null;

        //Check if hero is awakened
        //An awakened hero has a _ in is garmId
        if (garmId.contains("_")) {
            //Try to get the awakened hero image
            imageSource = new URL(IMAGE_URL + hero.getGarmId() + ".png");
            if (isValidURL(imageSource)) {
                return imageSource.openStream();
            } else {
                //Try fallback-URL for awakened heroes
                imageSource = new URL(IMAGE_AWAKENED_FALLBACK_URL + hero.getGarmId() + ".png");
                if (isValidURL(imageSource)) {
                    return imageSource.openStream();
                } else {
                    //Try second fallback URL for awakened heroes
                    imageSource = new URL(IMAGE_URL + hero.getGarmId().substring(0, hero.getGarmId().indexOf('_')) + "_1_" + hero.getGarmId().substring(hero.getGarmId().indexOf('_') + 1) + ".png");
                }
            }
        } else {
            imageSource = new URL(IMAGE_URL + hero.getGarmId() + "_1.png");
            if (isValidURL(imageSource)) {
                return imageSource.openStream();
            } else {
                //Try fallback URL for normal heries
                imageSource = new URL(IMAGE_FALLBACK_URL + hero.getGarmId() + "_1.png");
            }
        }
        //final check for validity
        if (isValidURL(imageSource)) {
            return imageSource.openStream();
        } else {
            return null;
        }
    }
    /**
     * Grabs basic information for a {@link Hero}.
     * @param hero a {@link Hero} to grab information for
     */
    private void grabHeroDetails(final Hero hero) {
        Document heroDetailSite = null;

        try {
            heroDetailSite = Jsoup.connect(HERO_DETAIL_PAGE + hero.getGarmId()).get();
        } catch (IOException e) {
            log.error("Could not get detail page for: " + hero.getName() + " (" + hero.getGarmId() + ")");
            return;
        }
        //Set initial rarity
        Element rarityElement = heroDetailSite.select("td.views-field-field-initial-rarity").first();
        if (rarityElement != null) {
            hero.setInitialRarity(Integer.valueOf(rarityElement.text().substring(0, 1)));
        }

        //Set the type of hero
        Element heroTypeElement = heroDetailSite.select("td.views-field-field-type > a").first();
        if (heroTypeElement != null) {
            switch (heroTypeElement.text()) {
                case "Melee":
                    hero.setType(EType.MELEE);
                    break;
                case "Magic":
                    hero.setType(EType.MAGIC);
                    break;
                case "Ranged":
                    hero.setType(EType.RANGED);
                    break;
                default:
                    hero.setType(EType.UNKNOWN);
                    break;
            }
        }

        //Set the gender
        Element genderElement = heroDetailSite.select("td.views-field-field-gender").first();
        if (genderElement != null) {
            switch (genderElement.text()){
                case "Male":
                    hero.setGender(EGender.MALE);
                    break;
                case "Female":
                    hero.setGender(EGender.FEMALE);
                    break;
                case "???":
                    hero.setGender(EGender.UNDEFINED);
                    break;
                default:
                    hero.setGender(EGender.UNKNOWN);
            }
        }

        //Add traits
        Elements traitElements = heroDetailSite.select("td.views-field-field-hidden-traits > a");
        if (traitElements != null) {
            traitElements.forEach(
                    hiddenTrait -> {
                        switch (hiddenTrait.text()) {
                            case "Grounded":
                                hero.addTrait(ETrait.GROUNDED);
                                break;
                            case "Airborne":
                                hero.addTrait(ETrait.AIRBORNE);
                                break;
                            default:
                                hero.addTrait(ETrait.UNKNOWN);
                                break;
                        }
                    }
            );
        }

        //Add attack type
        Element attackTypeElement = heroDetailSite.select("td.views-field-field-damage-type").first();
        if (attackTypeElement != null) {
            switch (attackTypeElement.text()){
                case "ATK":
                    hero.setDamageType(EDamageType.P_ATK);
                    break;
                case "MATK":
                    hero.setDamageType(EDamageType.M_ATK);
                    break;
                default:
                    hero.setDamageType(EDamageType.UNKNOWN);
                    break;
            }
        }

        //Add race
        Element raceElement = heroDetailSite.select("td.views-field-field-race > a").first();
        if (raceElement != null) {
            switch (raceElement.text()){
                case "Aesir":
                    hero.setRace(ERace.AESIR);
                    break;
                case "Beast":
                    hero.setRace(ERace.BEAST);
                    break;
                case "Dwarf":
                    hero.setRace(ERace.DWARF);
                    break;
                case "Elf":
                    hero.setRace(ERace.ELF);
                    break;
                case "Human":
                    hero.setRace(ERace.HUMAN);
                    break;
                case "Jotum":
                    hero.setRace(ERace.JOTUM);
                    break;
                case "Therian":
                    hero.setRace(ERace.THERIAN);
                    break;
                default:
                    hero.setRace(ERace.UNKNOWN);
                    break;
            }
        }

        //Set field resistance
        Element  fieldResistanceElement = heroDetailSite.select("td.views-field-field-passive-field > a").first();
        if (fieldResistanceElement != null) {
            switch (fieldResistanceElement.text()){
                case "Gravity Field":
                    hero.setFieldResistance(EFieldEffect.GRAVITY);
                    break;
                case "Eclipse Field":
                    hero.setFieldResistance(EFieldEffect.ECLIPSE);
                    break;
                case "Mist Field":
                    hero.setFieldResistance(EFieldEffect.MIST);
                    break;
                case "Void Field":
                    hero.setFieldResistance(EFieldEffect.VOID);
                    break;
                default:
                    hero.setFieldResistance(EFieldEffect.UNKNOWN);
                    break;
            }
        }

        //Set the heroes bio
        Element bioElement = heroDetailSite.selectFirst("div.field-type-text-with-summary > div > div");
        if(bioElement != null){
            hero.setBio(bioElement.text());
        }

        //Set skill element
        Element skillElement = heroDetailSite.selectFirst("td.views-field-field-element");
        if(skillElement != null) {
            switch (skillElement.text()){
                case "Dark":
                    hero.setSkillElement(EElement.DARKNESS);
                    break;
                case "Earth":
                    hero.setSkillElement(EElement.EARTH);
                    break;
                case "Fire":
                    hero.setSkillElement(EElement.FIRE);
                    break;
                case "Light":
                    hero.setSkillElement(EElement.LIGHT);
                    break;
                case "Water":
                    hero.setSkillElement(EElement.WATER);
                    break;
                case "Other":
                    hero.setSkillElement(EElement.OTHER);
                    break;
                default:
                    hero.setSkillElement(EElement.NEUTRAL);
                    break;
            }
        }

        //The name of the heroes action skill
        Element skillNameElement = heroDetailSite.selectFirst("section.pane-node-field-action-skill > div > div > div > div > div");
        if(skillNameElement != null) {
            hero.setSkillName(skillNameElement.text());
        }

        //The description of the heroes action skill
        Element skillDescriptionElement = heroDetailSite.selectFirst("div.field-name-field-action-skill-effect > div > div");
        if(skillDescriptionElement != null) {
            hero.setSkillDescription(skillDescriptionElement.text());
        }

        Element limitBurstElement = heroDetailSite.selectFirst("div.field-name-field-limit-burst > ul > li > article > h2 > a");
        if (limitBurstElement != null) {
            String limitBurstGarmId = limitBurstElement.attr("href").substring(13);
            if (limitBurstGarmId != null && !limitBurstGarmId.equals(""))  {
                LimitBurst limitBurst = fetchLimitBurst(limitBurstGarmId);
                if (limitBurst != null) {
                    hero.setLimitBurst(limitBurst);
                    limitBurstRepository.save(limitBurst);
                }
            }
        }
    }

    private LimitBurst fetchLimitBurst(final String garmID){
        LimitBurst limitBurst = limitBurstRepository.findOneByGarmId(garmID);

        Document limitBurstSite;

        try {
            limitBurstSite = Jsoup.connect(LIMITBURST_PAGE + garmID).get();
        } catch (IOException e) {
            log.error("Could not connect to hero page " + LIMITBURST_PAGE + garmID + ". Aborting import!");
            return null;
        }

        if(limitBurstSite != null) {
            if (limitBurst == null) {
                limitBurst = new LimitBurst();
                limitBurst.setGarmId(garmID);
            }

            //Name of the limitBurst
            Element limitBurstNameElement = limitBurstSite.selectFirst("h1#page-title");
            if (limitBurstNameElement != null) {
                limitBurst.setName(limitBurstNameElement.text());
            }

            //Description of the limitBurst
            Element limitBurstDescriptionElement = limitBurstSite.selectFirst("div.taxonomy-term-description > p");
            if (limitBurstDescriptionElement != null) {
                limitBurst.setDescription(limitBurstDescriptionElement.text());
            }

            limitBurstRepository.save(limitBurst);

            return limitBurst;
        } else {
            return null;
        }
    }

    /**
     * Checks if an {@link URL} is valid.
     *
     * @param url the {@link URL} to check
     * @return true if valid (Response code starts with a 2), false else
     */
    private boolean isValidURL(final URL url) {
        if (url == null) {
            return false;
        }
        Integer response = null;
        try {
            response = ((HttpURLConnection) url.openConnection()).getResponseCode();
        } catch (IOException e) {
            log.error("Failed Opening URL");
            return false;
        }

        return response.toString().substring(0, 1).equals("2");
    }
}
