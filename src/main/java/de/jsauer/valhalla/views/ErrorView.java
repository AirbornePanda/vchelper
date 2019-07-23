package de.jsauer.valhalla.views;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.jsauer.valhalla.components.MenuLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@PageTitle("Error")
@Route(value = ErrorView.VIEW_NAME, layout = MenuLayout.class)
public class ErrorView extends AbstractView {
    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorView.class);
    public static final String VIEW_NAME = "error";

    public ErrorView() {
        add(new Label("There was an error navigating!"));
    }
}
