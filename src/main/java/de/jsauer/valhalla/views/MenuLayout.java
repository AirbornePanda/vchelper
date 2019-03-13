package de.jsauer.valhalla.views;

import com.vaadin.flow.component.applayout.AbstractAppRouterLayout;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.AppLayoutMenu;
import com.vaadin.flow.component.applayout.AppLayoutMenuItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import de.jsauer.valhalla.components.LoginDialog;
import org.springframework.beans.factory.annotation.Autowired;

public class MenuLayout extends AbstractAppRouterLayout {
    @Autowired
    private LoginDialog loginDialog;

    @Override
    protected void configure(AppLayout appLayout, AppLayoutMenu appLayoutMenu) {
        appLayoutMenu.addMenuItems(
                new AppLayoutMenuItem(VaadinIcon.USER.create(), "Start", MainView.VIEW_NAME),
                new AppLayoutMenuItem(VaadinIcon.USER.create(), "Login", event -> loginDialog.setOpened(true))
        );
        appLayout.setBranding(new Span("Valhalla"));
    }
}
