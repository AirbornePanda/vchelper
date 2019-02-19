package de.jsauer.valhalla.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexLayout;

public class InformationWrapper extends Composite<FlexLayout> {
    private final Label descriptionLabel = new Label();
    private final Div containerDiv = new Div();
    private Component component;

    public InformationWrapper(final String description, final Component component){
        descriptionLabel.setText(description);
        descriptionLabel.setWidth("50%");
        descriptionLabel.getStyle().set("background", "aliceblue");
        descriptionLabel.getStyle().set("font-weight", "bold");
        descriptionLabel.getStyle().set("text-align", "center");
        containerDiv.setWidth("50%");

        if(component != null) {
            this.component = component;
            containerDiv.add(component);
        }
        getContent().getStyle().set("min-width", "min-content");
        getContent().getStyle().set("min-height", "min-content");
        getContent().add(descriptionLabel, containerDiv);
    }

    public InformationWrapper(final Component component){
        this("", component);
    }

    public InformationWrapper() {
        this(null);
    }

    public void setDescription(final String description) {
        descriptionLabel.setText(description);
    }

    public Label getDescriptionLabel() {
        return descriptionLabel;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
        containerDiv.add(component);
    }
}
