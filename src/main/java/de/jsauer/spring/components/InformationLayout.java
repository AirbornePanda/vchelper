package de.jsauer.spring.components;

import com.vaadin.flow.component.orderedlayout.FlexLayout;

public class InformationLayout extends FlexLayout {
    public InformationLayout(){
        this.getStyle().set("min-width", "min-content");
        this.getStyle().set("min-height", "min-content");
        this.getStyle().set("max-height", "min-content");
        this.getStyle().set("flex-direction", "column");
        this.getStyle().set("flex-grow", "1");
        this.getStyle().set("margin-right", "40px");
    }
}
