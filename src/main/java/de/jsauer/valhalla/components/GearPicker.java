package de.jsauer.valhalla.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import de.jsauer.valhalla.Application;
import de.jsauer.valhalla.backend.entities.Gear;
import de.jsauer.valhalla.backend.repositories.GearRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Picker for a {@link Gear}.
 */
@Component
@Scope(scopeName = "prototype")
public class GearPicker extends Dialog {

    /**
     * The list of gear.
     */
    private final ComboBox<Gear> cbGear = new ComboBox<>("Gear");
    /**
     * Button for closing the dialog and confirming selection.
     */
    private final Button btConfirm = new Button("Save", VaadinIcon.CHECK.create());
    /**
     * Button for closing the dialog and canceling selection.
     */
    private final Button btCancel = new Button("Cancel", VaadinIcon.CLOSE.create());
    /**
     * Layout of this dialog.
     */
    private final VerticalLayout contentLayout = new VerticalLayout();
    /**
     * Header of this dialog.
     */
    private final H3 header = new H3("Select Gear");
    /**
     * Internal representation of the selected gear.
     */
    private Gear gear;

    public GearPicker(@Autowired GearRepository gearRepository) {
        cbGear.setItems(gearRepository.findAll());
        cbGear.setItemLabelGenerator(Gear::getName);
        cbGear.setRenderer(new ComponentRenderer<>(gear -> {
            HorizontalLayout component = new HorizontalLayout();
            Label label = new Label(gear.getName());
            Image image = new Image();
            image.setMaxWidth("40px");
            image.setMaxHeight("40px");

            image.setSrc(Application.GEAR_IMAGE_LOCATION + gear.getValkypediaId() + ".png");

            image.setAlt(gear.getName());

            component.add(image, label);
            return component;
        }));

        btConfirm.addClickListener(event -> {
            gear = cbGear.getValue();
            this.close();
        });
        btConfirm.setSizeFull();
        btCancel.addClickListener(event -> this.close());

        btCancel.setSizeFull();
        contentLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        contentLayout.add(header, cbGear, btConfirm, btCancel);

        this.add(contentLayout);
        this.setCloseOnEsc(true);
        this.setCloseOnOutsideClick(true);
    }

    public Gear getGear() {
        return gear;
    }

    public void setGear(Gear gear) {
        cbGear.setValue(gear);
        this.gear = gear;
    }
}
