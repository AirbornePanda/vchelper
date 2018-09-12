package de.jsauer.spring.views;

import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLayout;

import java.util.Objects;

public class MenuLayout extends Div implements RouterLayout {

    public MenuLayout() {
        FlexLayout menuLayout = new FlexLayout();
        menuLayout.setWidth("100%");
        menuLayout.setHeight("10%");

        H3 applicationName = new H3("Valkyrie Connect Helper");
        applicationName.getStyle().set("margin-top", "14px");

        Tabs menuItems = new Tabs();
        menuItems.add(new Tab(new Label("Heroes"), VaadinIcon.USERS.create()));
        menuItems.add(new Tab("test2"));
        menuItems.add(new Tab("test3"));

        menuLayout.getStyle().set("background-color", "aliceblue");
        menuLayout.add(applicationName);
        menuLayout.add(menuItems);
        menuLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        menuLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        menuLayout.getStyle().set("flex-direction", "column");
        this.add(menuLayout);
        this.setSizeFull();
    }

    @Override
    public void showRouterLayoutContent(HasElement content) {
        if (content != null) {
            content.getElement().getStyle().set("height", "90%");
            content.getElement().getStyle().set("width", "100%");
            getElement().appendChild(Objects.requireNonNull(content.getElement()));
        }
    }
}
