package de.jsauer.valhalla.components;

import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import de.jsauer.valhalla.views.AdminView;
import de.jsauer.valhalla.views.MainView;
import de.jsauer.valhalla.views.TeamBuilderView;
import org.springframework.beans.factory.annotation.Autowired;

public class MenuLayout extends AppLayout implements HasComponents, AfterNavigationObserver {
    @Autowired
    private LoginDialog loginDialog;

    private MenuRibbon menuRibbon = new MenuRibbon();

    public MenuLayout() {
        HorizontalLayout navbarLayout = new HorizontalLayout();
        navbarLayout.setWidthFull();

        menuRibbon.addRibbonEntry(MainView.class, "Home");
        menuRibbon.addRibbonEntry(AdminView.class, "Administration");
        menuRibbon.addRibbonEntry(TeamBuilderView.class, "TeamBuilder");

        //This centers the tabs, but they won't be mobile responsive
        navbarLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        navbarLayout.add(menuRibbon);

        this.addToNavbar(navbarLayout);
    }

    /**
     * Switches the color theme between light and dark.
     */
    private void changeTheme() {
        String style = this.getElement().getAttribute("theme");

        if (style == null || style.equals("")) {
            this.getElement().setAttribute("theme", "dark");
        } else {
            this.getElement().setAttribute("theme", "");
        }
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        menuRibbon.checkSelected(event.getLocation());
    }
}
