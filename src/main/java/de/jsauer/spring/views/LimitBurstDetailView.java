package de.jsauer.spring.views;

import com.vaadin.flow.component.html.Div;
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
import de.jsauer.spring.backend.repositories.LimitBurstRepository;
import de.jsauer.spring.components.InformationLayout;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.util.Optional;

@Route("limitBurstDetail")
public class LimitBurstDetailView extends DetailPage<LimitBurst> {

    private final VerticalLayout masterLayout = new VerticalLayout();
    private final HorizontalLayout headerLayout = new HorizontalLayout();
    private final FlexLayout containerLayout = new FlexLayout();

    private final H1 pageHeading = new H1("Limit Burst Name");
    private final H4 descriptionHeading = new H4("Limit Burst Description");
    private final H2 relatedHeroesHeading = new H2("Heroes using this Limit Burst");

    @Autowired
    private LimitBurstRepository limitBurstRepository;

    @Override
    public void setParameter(final BeforeEvent event, final Long limitBurstId) {
        createBindings();
        if(limitBurstId == null) {
            event.rerouteTo(ErrorView.class);
        }
        else {
            Optional<LimitBurst> limitBurstOptional = limitBurstRepository.findById(limitBurstId);
            limitBurstOptional.ifPresentOrElse(limitBurst -> getBinder().setBean(limitBurst), () ->  event.rerouteTo(ErrorView.class));
        }
        addRelatedHeroes();
    }

    public LimitBurstDetailView() {
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

    private void addRelatedHeroes() {
        for (Hero hero: getBinder().getBean().getHeroes()) {
            FlexLayout layout = new FlexLayout();
            layout.getStyle().set("flex-direction", "column");
            layout.getStyle().set("margin-right", "30px");
            layout.setWidth("75px");
            layout.setAlignItems(Alignment.CENTER);
            Image image = new Image();
            image.setSrc(new StreamResource("", () -> new ByteArrayInputStream(hero.getImage())));
            image.setHeight("75px");
            image.setWidth("75px");

            RouterLink routerLink = new RouterLink(hero.getName(), HeroDetailView.class, hero.getId());
            routerLink.getStyle().set("text-align", "center");

            layout.add(image);
            layout.add(routerLink);

            containerLayout.add(layout);
        }
    }

    @Override
    public String getPageTitle() {
        return getBinder().getBean().getName() + " Details";
    }

    @Override
    void createBindings() {
        generateAndBindReadOnly(limitBurst -> pageHeading.setText(limitBurst.getName()));
        generateAndBindReadOnly(limitBurst -> descriptionHeading.setText(limitBurst.getDescription()));
    }
}
