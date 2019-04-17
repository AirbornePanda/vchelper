package de.jsauer.valhalla.components;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Layout containing a team of heroes.
 */
@Component
@Scope(scopeName = "prototype")
public class TeamLayout extends HorizontalLayout {
    /**
     * Determines the state of the cards
     */
    private Boolean iconOnly = false;
    /**
     * The first hero card.
     */
    @Autowired
    private HeroCard card1;
    /**
     * The second hero card.
     */
    @Autowired
    private HeroCard card2;
    /**
     * The third hero card.
     */
    @Autowired
    private HeroCard card3;
    /**
     * The fourth hero card.
     */
    @Autowired
    private HeroCard card4;
    /**
     * The fifth hero card.
     */
    @Autowired
    private HeroCard card5;

    /**
     * Basic constructor.
     */
    public TeamLayout() {

    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        this.add(card1, card2, card3, card4, card5);
    }

    /**
     * Get the state of the icon only mode.
     * @return the state of the icon only mode
     */
    public Boolean getIconOnly() {
        return iconOnly;
    }

    /**
     * Set the state of the icon only mode.
     * @param iconOnly the new state of the icon only mode.
     */
    public void setIconOnly(final Boolean iconOnly) {
        this.iconOnly = iconOnly;
        card1.setIconOnly(iconOnly);
        card2.setIconOnly(iconOnly);
        card3.setIconOnly(iconOnly);
        card4.setIconOnly(iconOnly);
        card5.setIconOnly(iconOnly);
    }
}
