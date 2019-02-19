package de.jsauer.valhalla.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.AppLayoutMenu;
import com.vaadin.flow.component.applayout.AppLayoutMenuItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.RouterLayout;
import de.jsauer.valhalla.components.LoginDialog;
import org.springframework.beans.factory.annotation.Autowired;

public class MenuLayout extends AppLayout implements RouterLayout {
    @Autowired
    private LoginDialog loginDialog;

    public MenuLayout() {
        AppLayoutMenu menu = this.createMenu();
        menu.addMenuItems(
                new AppLayoutMenuItem(VaadinIcon.USER.create(), "Start", ""),
                new AppLayoutMenuItem(VaadinIcon.USER.create(), "Login", event -> loginDialog.setOpened(true))
        );
        this.setBranding(new Span("Valhalla"));
    }

    @Override
    public void showRouterLayoutContent(HasElement content) {
        this.setContent((Component) content);
    }
}
