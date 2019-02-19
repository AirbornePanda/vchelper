package de.jsauer.spring.views;

import com.vaadin.external.org.slf4j.Logger;
import com.vaadin.external.org.slf4j.LoggerFactory;
import com.vaadin.flow.router.Route;

@Route(value = LoginView.VIEW_NAME, layout = MenuLayout.class)
public class LoginView extends AbstractView {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginView.class);
    public static final String VIEW_NAME = "login";
}
