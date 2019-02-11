package de.jsauer.spring.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Shortcuts;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.AppLayoutMenu;
import com.vaadin.flow.component.applayout.AppLayoutMenuItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.RouterLayout;

public class MenuLayout extends AppLayout implements RouterLayout {
    private final LoginOverlay loginOverlay = new LoginOverlay();

    public MenuLayout() {
        AppLayoutMenu menu = this.createMenu();
        menu.addMenuItems(
                new AppLayoutMenuItem(VaadinIcon.USER.create(), "Start", ""),
                new AppLayoutMenuItem(VaadinIcon.USER.create(), "Login", event -> loginOverlay.setOpened(true))
        );
        this.setBranding(new Span("Valhalla"));

        loginOverlay.setTitle("Valhalla");
        loginOverlay.setDescription("Keeping it all in valhalla.");
        Shortcuts.addShortcutListener(loginOverlay, loginOverlay::close, Key.ESCAPE);
    }

    @Override
    public void showRouterLayoutContent(HasElement content) {
        this.setContent((Component) content);
    }
}
