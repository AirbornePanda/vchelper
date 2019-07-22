package de.jsauer.valhalla.views;

import com.vaadin.flow.router.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Route(value = LoginView.VIEW_NAME, layout = MenuLayout.class)
public class LoginView extends AbstractView {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginView.class);
    public static final String VIEW_NAME = "login";
}
