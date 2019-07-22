package de.jsauer.valhalla.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import de.jsauer.valhalla.SecurityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
