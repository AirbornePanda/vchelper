package de.jsauer.valhalla.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.router.RouterLink;

/**
 * Represents one entry inside the {@link MenuRibbon}.
 *
 * @author Julian Sauer
 * @since 1.0
 */
public class MenuRibbonEntry extends Tab {
    /**
     * The target to navigate to.
     */
    private RouterLink navigationTarget;

    /**
     * Constructor to make new entry.
     * @param name display name
     * @param target navigation target
     */
    public MenuRibbonEntry(final String name, final Class<? extends Component> target) {
        navigationTarget = new RouterLink(name, target);
        this.add(navigationTarget);
    }

    /**
     * Get the navigation target.
     * @return the contained router link.
     */
    public RouterLink getNavigationTarget() {
        return navigationTarget;
    }
}
