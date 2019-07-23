package de.jsauer.valhalla.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Location;

/**
 * Class for making menu inside the navbar.
 *
 * @author Julian Sauer
 * @since 1.0
 */
public class MenuRibbon extends Tabs {

    /**
     * Checks if the navigation target is inside the menu.
     * Selects if the target is found.
     * @param target the target of the navigation
     */
    public void checkSelected(final Location target) {
        getChildren().forEach(tab -> {
            if (((MenuRibbonEntry) tab).getNavigationTarget().getHref().equals(target.getPath())){
                this.setSelectedTab((MenuRibbonEntry)tab);
            }
        });
    }

    /**
     * Adds an entry to the menu.
     * @param target the navigation target
     * @param name display name
     */
    public void addRibbonEntry(final Class<? extends Component> target, final String name) {
            this.add(new MenuRibbonEntry(name, target));
    }
}
