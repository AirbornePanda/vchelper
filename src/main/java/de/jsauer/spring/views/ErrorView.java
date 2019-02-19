package de.jsauer.spring.views;

import com.vaadin.external.org.slf4j.Logger;
import com.vaadin.external.org.slf4j.LoggerFactory;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Error")
@Route(value = ErrorView.VIEW_NAME, layout = MenuLayout.class)
public class ErrorView extends AbstractView {
    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorView.class);
    public static final String VIEW_NAME = "error";

    public ErrorView() {
        add(new Label("There was an error navigating!"));
    }
}
