package de.jsauer.spring.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.StreamResource;
import de.jsauer.spring.backend.entities.Hero;
import de.jsauer.spring.backend.entities.LimitBurst;
import de.jsauer.spring.backend.enums.EElement;
import de.jsauer.spring.backend.repositories.HeroRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.util.Optional;

@Route(value = "skillElementDetail", layout = MenuLayout.class)
public class SkillElementDetailView extends DetailPage<EElement> {

    private final VerticalLayout masterLayout = new VerticalLayout();
    private final HorizontalLayout headerLayout = new HorizontalLayout();
    private final FlexLayout containerLayout = new FlexLayout();

    private final H1 pageHeading = new H1( "Skill with Element");
    private final H4 descriptionHeading = new H4("Skill Element");
    private final H2 relatedHeroesHeading = new H2("Heroes with this Element as their Action Skill");

    @Autowired
    HeroRepository heroRepository;

    @Override
    public void setParameter(final BeforeEvent event, final Long elementIndex) {
        createBindings();
        if(elementIndex == null) {
            event.rerouteTo(ErrorView.class);
        } else {
            EElement elementOptional = EElement.values()[Math.toIntExact(elementIndex)];
            getBinder().setBean(elementOptional);
        }
        addRelatedHeroes();
    }

    public SkillElementDetailView() {
        pageHeading.getStyle().set("margin-top", "40px");
        pageHeading.getStyle().set("margin-bottom", "40px");

        headerLayout.setDefaultVerticalComponentAlignment(
                FlexComponent.Alignment.CENTER);
        headerLayout.add(pageHeading);

        relatedHeroesHeading.setWidth("100%");

        containerLayout.setSizeFull();
        containerLayout.getStyle().set("flex-wrap", "wrap");

        masterLayout.setSizeFull();
        masterLayout.add(headerLayout);
        masterLayout.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER,
                headerLayout);
        masterLayout.add(descriptionHeading);
        masterLayout.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER,
                descriptionHeading);
        masterLayout.add(relatedHeroesHeading);
        masterLayout.add(containerLayout);
        this.add(masterLayout);
    }

    /**
     * Add the related {@link Hero}s to the {@link EElement}
     */
    private void addRelatedHeroes() {
        for (Hero hero: heroRepository.findAllBySkillElement(getBinder().getBean())) {
            FlexLayout outerContainer = new FlexLayout();
            FlexLayout innerContainer = new FlexLayout();

            outerContainer.getStyle().set("margin-right", "30px");
            outerContainer.setWidth("75px");
            innerContainer.getStyle().set("position", "relative");
            innerContainer.setAlignItems(FlexComponent.Alignment.CENTER);
            innerContainer.getStyle().set("flex-direction", "column");

            Image image = new Image();
            image.setSrc(new StreamResource("", () -> new ByteArrayInputStream(hero.getImage())));
            image.setHeight("75px");
            image.setWidth("75px");

            RouterLink imageTitle = new RouterLink(hero.getName(), HeroDetailView.class, hero.getId());
            imageTitle.getStyle().set("text-align", "center");

            RouterLink imageLink = new RouterLink("", HeroDetailView.class, hero.getId());
            imageLink.getStyle().set("position", "absolute");
            imageLink.getStyle().set("left", "0px");
            imageLink.getStyle().set("top", "0px");
            imageLink.getStyle().set("height", "100%");
            imageLink.getStyle().set("width", "100%");

            innerContainer.add(image);
            innerContainer.add(imageLink);
            innerContainer.add(imageTitle);
            outerContainer.add(innerContainer);
            containerLayout.add(outerContainer);
        }
    }

    @Override
    public String getPageTitle() {
        return getBinder().getBean().getName() + " Element Action Skill Heroes";
    }

    @Override
    void createBindings() {
        generateAndBindReadOnly(element -> pageHeading.setText("Heroes with sction skills of the " + element.getName() + " element."));
        //TODO Make other elements clickable in descriptionHeading
        generateAndBindReadOnly(element -> descriptionHeading.setText("Other elements here with icon"));
    }




}
