package de.jsauer.valhalla.components;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import de.jsauer.vaadintoolbox.Card;
import de.jsauer.valhalla.Application;
import de.jsauer.valhalla.backend.entities.Gear;
import de.jsauer.valhalla.backend.entities.Hero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * A {@link Card} for displaying a {@link Hero} and his/her {@link Gear}.
 */
@Component
@Scope(scopeName = "prototype")
@HtmlImport("frontend://paper-card.html")
public class HeroCard extends Card {
    /**
     * Hero set with this card.
     */
    private Hero hero;
    /**
     * State of the icon only mode of this card.
     */
    private Boolean iconOnly = false;
    /**
     * Name of the hero displayed on this card.
     */
    private final Label laHeroName = new Label();
    /**
     * The image of the set hero.
     */
    private final Image heroImage = new Image();
    /**
     * The first gear.
     */
    @Autowired
    private GearBox gear1;
    /**
     * The second gear.
     */
    @Autowired
    private GearBox gear2;
    /**
     * The third gear.
     */
    @Autowired
    private GearBox gear3;
    /**
     * The fourth gear.
     */
    @Autowired
    private GearBox gear4;
    /**
     * The first accessory.
     */
    @Autowired
    private GearBox acc1;
    /**
     * The second accessory.
     */
    @Autowired
    private GearBox acc2;
    /**
     * The third accessory.
     */
    @Autowired
    private GearBox acc3;
    /**
     * The fourth accessory.
     */
    @Autowired
    private GearBox acc4;
    /**
     * The layout keeping all elements together.
     */
    private final VerticalLayout contentLayout = new VerticalLayout();
    /**
     * Layout of the first row.
     */
    private final HorizontalLayout gearLayout1 = new HorizontalLayout();
    /**
     * Layout of the second row.
     */
    private final HorizontalLayout gearLayout2 = new HorizontalLayout();
    /**
     * Layout of the third row.
     */
    private final HorizontalLayout gearLayout3 = new HorizontalLayout();
    /**
     * Layout of the fourth row.
     */
    private final HorizontalLayout gearLayout4 = new HorizontalLayout();

    @Autowired
    private HeroPicker heroPicker;

    @Autowired
    private GearPicker gearPicker;

    /**
     * Basic constructor.
     */
    public HeroCard() {
        heroImage.setMaxWidth("75px");
        heroImage.setSrc("/frontend/img/placeholder.png");
        gearLayout1.setMargin(false);
        gearLayout1.setSpacing(false);
        gearLayout2.setMargin(false);
        gearLayout2.setSpacing(false);
        gearLayout3.setMargin(false);
        gearLayout3.setSpacing(false);
        gearLayout4.setMargin(false);
        gearLayout4.setSpacing(false);

        contentLayout.setPadding(false);
        contentLayout.add(heroImage, laHeroName, gearLayout1, gearLayout2, gearLayout3, gearLayout4);
        contentLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        this.addContent(contentLayout);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        gearLayout1.add(gear1, acc1);
        gearLayout2.add(gear2, acc2);
        gearLayout3.add(gear3, acc3);
        gearLayout4.add(gear4, acc4);

        //Set all the listeners
        heroImage.addClickListener(clickEvent -> {
            if(this.hero != null) {
                heroPicker.setHero(hero);
            }
            heroPicker.open();
            heroPicker.addOpenedChangeListener(closeEvent -> {
                if (!closeEvent.isOpened()) {
                    this.setHero(heroPicker.getHero());
                }
                if (!closeEvent.isFromClient()) {
                    closeEvent.unregisterListener();
                }
            });
        });
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(final Hero hero) {
        if (hero != null) {
            this.hero = hero;
            laHeroName.setText(hero.getName());
            heroImage.setSrc(Application.HERO_IMAGE_LOCATION + hero.getGarmId() + ".png");
            heroImage.setAlt(hero.getName());
            heroImage.setTitle(hero.getName());
        }
    }

    public Boolean getIconOnly() {
        return iconOnly;
    }

    public void setIconOnly(Boolean iconOnly) {
        this.iconOnly = iconOnly;
        laHeroName.setVisible(iconOnly);
        gear1.setIconOnly(iconOnly);
        gear2.setIconOnly(iconOnly);
        gear3.setIconOnly(iconOnly);
        gear4.setIconOnly(iconOnly);
        acc1.setIconOnly(iconOnly);
        acc2.setIconOnly(iconOnly);
        acc3.setIconOnly(iconOnly);
        acc4.setIconOnly(iconOnly);
    }
}
