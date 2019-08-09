package de.jsauer.valhalla.components;

import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.server.Command;
import com.vaadin.flow.shared.communication.PushMode;
import de.jsauer.valhalla.views.AdminView;
import de.jsauer.valhalla.views.MainView;
import de.jsauer.valhalla.views.TeamBuilderView;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * General layout for the application.
 * At the moment {@link PushMode#MANUAL} has to be used to work in the Spring-Boot application.
 * So make sure to use {@link UI#push()} after you use {@link UI#access(Command)}.
 *
 * Every {@link NpmPackage}, {@link JsModule} and {@link StyleSheet} annotation are needed to use bootstrap.
 *
 * @author Julian Sauer
 * @since 1.0
 */
@Push(value = PushMode.MANUAL)
@NpmPackage(value = "jquery", version = "3.4.1")
@NpmPackage(value = "popper.js", version = "1.15.0")
@NpmPackage(value = "bootstrap", version = "4.3.1")
@JsModule("jquery/dist/jquery.slim.js")
@JsModule("bootstrap/dist/js/bootstrap.js")
@StyleSheet("styles/bootstrap.min.css")
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
