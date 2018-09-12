package de.jsauer.spring.views;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Error")
@Route(value = "error", layout = MenuLayout.class)
public class ErrorView extends VerticalLayout {
    public ErrorView() {
        add(new Label("There was an error navigating!"));
    }
}
