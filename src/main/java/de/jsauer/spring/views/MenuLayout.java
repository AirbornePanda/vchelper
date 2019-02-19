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
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.RouterLayout;
import de.jsauer.spring.Application;
import de.jsauer.spring.components.LoginDialog;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
