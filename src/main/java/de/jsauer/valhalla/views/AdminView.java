package de.jsauer.valhalla.views;


import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import de.jsauer.valhalla.components.MenuLayout;

@Route(value = AdminView.VIEW_NAME, layout = MenuLayout.class)
public class AdminView extends Div {
    public static final String VIEW_NAME = "adminView";
}
