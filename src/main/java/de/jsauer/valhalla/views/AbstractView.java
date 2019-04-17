package de.jsauer.valhalla.views;

import com.vaadin.external.org.slf4j.Logger;
import com.vaadin.external.org.slf4j.LoggerFactory;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import de.jsauer.vaadintoolbox.CookieConsent;
import de.jsauer.valhalla.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public abstract class AbstractView extends Div implements BeforeEnterObserver {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractView.class);

    @Autowired
    private HttpServletRequest request;

    @Override
    public void beforeEnter(BeforeEnterEvent enterEvent) {
        //TODO finalize role management
        if (SecurityConfig.isAuthenticationRequired(enterEvent.getNavigationTarget())
                && !request.isUserInRole("ADMIN")
                //Prevents loop
                && enterEvent.getNavigationTarget() != ErrorView.class) {

            enterEvent.rerouteTo(ErrorView.class);
        }
    }
}
