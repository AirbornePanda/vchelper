package de.jsauer.valhalla.utility;

import com.vaadin.flow.server.VaadinServlet;
import de.jsauer.valhalla.Application;
import de.jsauer.valhalla.backend.entities.Gear;
import de.jsauer.valhalla.backend.entities.Hero;
import de.jsauer.valhalla.backend.repositories.GearRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Grabs information from <a href="http://jam-capture-vcon-ww.ateamid.com/en/">Valkypedia</a>.
 */
@Service
public class ValkypediaGrabber {
    /**
     * {@link Logger} for this class.
     */
    private static final Logger log = LoggerFactory.getLogger(ValkypediaGrabber.class);
    /**
     * Link to the site containing basic {@link Hero} information of melee heroes.
     */
    private static final String HERO_PAGE_MELEE = "http://jam-capture-vcon-ww.ateamid.com/en/character_list/1.html";
    /**
     * Link to the site containing basic {@link Hero} information of magic heroes.
     */
    private static final String HERO_PAGE_MAGIC = "http://jam-capture-vcon-ww.ateamid.com/en/character_list/2.html";
    /**
     * Link to the site containing basic {@link Hero} information of ranged heroes.
     */
    private static final String HERO_PAGE_RANGE = "http://jam-capture-vcon-ww.ateamid.com/en/character_list/3.html";
    /**
     * Link to the hero detail pages.
     */
    private static final String HERO_DETAIL_PAGE = "http://jam-capture-vcon-ww.ateamid.com/en/character_detail/";
    /**
     * Link to the hero portraits.
     */
    private static final String HERO_IMAGE_URL = "http://jam-capture-vcon-ww.ateamid.com/images/character/icon_character_";
    /**
     * Link to the site containing basic {@link de.jsauer.valhalla.backend.entities.Gear} information of gear of rarity 1.
     */
    public static final String GEAR_PAGE_1 = "http://jam-capture-vcon-ww.ateamid.com/en/equip_list/1.html";
    /**
     * Link to the site containing basic {@link de.jsauer.valhalla.backend.entities.Gear} information of gear of rarity 2.
     */
    public static final String GEAR_PAGE_2 = "http://jam-capture-vcon-ww.ateamid.com/en/equip_list/2.html";
    /**
     * Link to the site containing basic {@link de.jsauer.valhalla.backend.entities.Gear} information of gear of rarity 3.
     */
    public static final String GEAR_PAGE_3 = "http://jam-capture-vcon-ww.ateamid.com/en/equip_list/3.html";
    /**
     * Link to the gear detail pages.
     */
    public static final String GEAR_DETAIL_PAGE = "http://jam-capture-vcon-ww.ateamid.com/en/equip_detail/";
    /**
     * Link to the gear portraits.
     */
    public static final String GEAR_IMAGE_URL = "http://jam-capture-vcon-ww.ateamid.com/images/equip/icon_equip_";

    @Autowired
    private GearRepository gearRepository;

    /**
     * Grab all information on heroes.
     */
    public void grabAllHeroes() {
        grabHeroes(HERO_PAGE_MELEE);
        grabHeroes(HERO_PAGE_MAGIC);
        grabHeroes(HERO_PAGE_RANGE);
    }

    /**
     * Grab all {@link Hero}s and their information from the given site.
     * @param url the site that contains an overview of heroes
     */
    private void grabHeroes(final String url) {
        for (String heroId : getIdList(url)) {
            grabHeroDetail(heroId);
        }
    }

    /**
     * Grab detail information on one hero.
     * @param heroId the id of the hero
     */
    private void grabHeroDetail(final String heroId) {
        Document detailSite = null;
        String detailUrl = HERO_DETAIL_PAGE + heroId + ".html";

        try {
            detailSite = Jsoup.connect(detailUrl).get();
        } catch (IOException e) {
            log.error("Could not connect to detail page! Aborting import from: " + detailUrl);
            return;
        }

        //TODO check if hero exists

        URL imageUrl = null;
        try {
            imageUrl = new URL(HERO_IMAGE_URL + heroId + "_1.png");
            BufferedImage heroImage = ImageIO.read(imageUrl);
            ImageIO.write(heroImage, "png",new File(VaadinServlet.getCurrent().getServletContext().getRealPath(Application.HERO_IMAGE_LOCATION) + heroId + ".png"));
        } catch (MalformedURLException e) {
            log.error("Could not create for hero image of: " + heroId);
        } catch (IOException e) {
            log.error("Could not read or save hero image from: " + imageUrl.toString());
        }
    }

    /**
     * Grab all information on gear.
     */
    public void grabAllGear() {
        //grabGear(GEAR_PAGE_1);
        //grabGear(GEAR_PAGE_2);
        grabGear(GEAR_PAGE_3);
    }

    /**
     * Grab all {@link de.jsauer.valhalla.backend.entities.Gear} and their information from the given site.
     * @param url the site that contains an overview of the gear
     */
    private void grabGear(final String url) {
        for (String gearId : getIdList(url)) {
            grabGearDetail(gearId);
        }
    }

    /**
     * Grab detail information on one gear item.
     * @param gearId the id of the hero
     */
    private void grabGearDetail (final String gearId){
        Document detailSite = null;
        String detailUrl = GEAR_DETAIL_PAGE + gearId + ".html";

        try {
            detailSite = Jsoup.connect(detailUrl).get();
        } catch (IOException e) {
            log.error("Could not connect to detail page! Aborting import from: " + detailUrl);
            return;
        }

        Gear gear = gearRepository.findOneByValkypediaId(gearId);
        //Gear doesn't exist, so it is new
        if(gear == null) {
            gear = new Gear();
            gear.setValkypediaId(gearId);
            //Attention: two underscores
            gear.setName(detailSite.selectFirst("p.name__text").text());
        }

        gearRepository.save(gear);

        URL imageUrl = null;
        try {
            imageUrl = new URL(GEAR_IMAGE_URL + gearId + ".png");
            BufferedImage gearImage = ImageIO.read(imageUrl);
            ImageIO.write(gearImage, "png",new File(VaadinServlet.getCurrent().getServletContext().getRealPath(Application.GEAR_IMAGE_LOCATION) + gearId + ".png"));
        } catch (MalformedURLException e) {
            log.error("Could not create for hero image of: " + gearId);
        } catch (IOException e) {
            log.error("Could not read or save hero image from: " + imageUrl.toString());
        }
    }

    /**
     * Generate a list of valkypedia ids for the given overview-page
     * @param url of the overview page
     * @return a list of id's from the overview page
     */
    private ArrayList<String> getIdList (final String url) {
        ArrayList<String> idList = new ArrayList<>();

        Document overviewSite = null;

        try {
            overviewSite = Jsoup.connect(url).get();
        } catch (IOException e) {
            log.error("Could not connect to hero page! Aborting import from: " + url);
            return idList;
        }

        Elements elementTable = overviewSite.select("table.main > tbody");

        for (Element tableRow: elementTable) {
            Elements elementEntries = tableRow.select("td");

            for (Element elementEntry: elementEntries) {
                //Filters "empty" hero boxes
                if (!elementEntry.className().equals("no_boder")){
                    try {
                        String htmlName = elementEntry.select("a").first().attr("href").substring(3).replaceAll(".*/", "");
                        String valkypediaId = htmlName.substring(0, htmlName.length() - 5);
                        idList.add(valkypediaId);
                    } catch (NullPointerException e) {
                        log.error("Error working on " + elementEntry);
                    }
                }
            }
        }
        return idList;
    }
}