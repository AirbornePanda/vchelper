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
import de.jsauer.valhalla.backend.entities.Hero;
import de.jsauer.valhalla.backend.repositories.HeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Picker for a {@link Hero}.
 */
@Component
@Scope(scopeName = "prototype")
public class HeroPicker extends Dialog {

    /**
     * The list of heroes.
     */
    private final ComboBox<Hero> cbHero = new ComboBox<>("Hero");
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
    private final H3 header = new H3("Select Hero");
    /**
     * Internal representation of the selected hero.
     */
    private Hero hero;

    public HeroPicker(@Autowired HeroRepository heroRepository) {
        cbHero.setItems(heroRepository.findAll());
        cbHero.setItemLabelGenerator(Hero::getName);
        cbHero.setRenderer(new ComponentRenderer<>(hero -> {
            HorizontalLayout component = new HorizontalLayout();
            Label label = new Label(hero.getName());
            Image image = new Image();
            image.setMaxWidth("40px");
            image.setMaxHeight("40px");

            image.setSrc(Application.HERO_IMAGE_LOCATION + hero.getGarmId() + ".png");

            image.setAlt(hero.getName());

            component.add(image, label);
            return component;
        }));

        btConfirm.addClickListener(event -> {
            hero = cbHero.getValue();
            this.close();
        });
        btConfirm.setSizeFull();
        btCancel.addClickListener(event -> this.close());
        btCancel.setSizeFull();

        contentLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        contentLayout.add(header, cbHero, btConfirm, btCancel);

        this.add(contentLayout);
        this.setCloseOnEsc(true);
        this.setCloseOnOutsideClick(true);
    }

    public void setHero(final Hero hero) {
        cbHero.setValue(hero);
        this.hero = hero;
    }

    public Hero getHero() {
        return hero;
    }
}
