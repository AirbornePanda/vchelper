package de.jsauer.valhalla.components;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import de.jsauer.valhalla.Application;
import de.jsauer.valhalla.backend.entities.Gear;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * A container for a {@link Gear} image and its label.
 * Intended for the {@link HeroCard}.
 */
@Component
@Scope(scopeName = "prototype")
public class GearBox extends VerticalLayout {
    /**
     * The gear of this box.
     */
    private Gear gear;
    /**
     * Shall only icons be displayed.
     */
    private Boolean iconOnly = false;
    /**
     * The image.
     */
    private final Image image = new Image();
    /**
     * The label.
     */
    private final Label label = new Label();

    @Autowired
    private GearPicker gearPicker;

    /**
     * Basic constructor.
     */
    public GearBox() {
        image.setMaxWidth("75px");
        image.setSrc("/frontend/img/placeholder.png");
        this.setMargin(false);
        this.setSpacing(false);
        this.setPadding(false);
        this.setAlignItems(Alignment.CENTER);
        this.add(image, label);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        image.addClickListener(clickEvent -> {
            if(this.gear != null) {
                gearPicker.setGear(gear);
            }
            gearPicker.open();
            gearPicker.addOpenedChangeListener(closeEvent -> {
                if (!closeEvent.isOpened()) {
                    this.setGear(gearPicker.getGear());
                }
                if (!closeEvent.isFromClient()) {
                    closeEvent.unregisterListener();
                }
            });
        });
    }

    /**
     * Constructor that also sets the gear.
     * @param gear the {@link Gear} to set
     */
    public GearBox(final Gear gear) {
        this();
        this.setGear(gear);
    }

    /**
     * Get the gear of this box.
     * @return the {@link Gear}
     */
    public Gear getGear() {
        return gear;
    }

    /**
     * Set the gear of this box.
     * @param gear the {@link Gear}
     */
    public void setGear(Gear gear) {
        if (gear != null) {
            this.gear = gear;
            this.image.setSrc(Application.GEAR_IMAGE_LOCATION + gear.getValkypediaId() + ".png");
            this.image.setAlt(gear.getName());
            this.image.setTitle(gear.getName());
            this.label.setText(gear.getName());
        }
    }

    /**
     * Get icon only mode.
     * @return the state of the icon only mode
     */
    public Boolean getIconOnly() {
        return iconOnly;
    }

    /**
     * Set the icon only mode.
     * @param iconOnly true, if only icons shall be displayed
     */
    public void setIconOnly(Boolean iconOnly) {
        this.iconOnly = iconOnly;
        label.setVisible(iconOnly);
    }
}
