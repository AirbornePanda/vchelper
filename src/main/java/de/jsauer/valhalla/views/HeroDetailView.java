package de.jsauer.valhalla.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.StreamResource;
import de.jsauer.valhalla.backend.entities.Hero;
import de.jsauer.valhalla.backend.enums.EElement;
import de.jsauer.valhalla.backend.repositories.HeroRepository;
import de.jsauer.valhalla.components.InformationLayout;
import de.jsauer.valhalla.components.InformationWrapper;
import de.jsauer.valhalla.components.MenuLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.Optional;

@Route(value = HeroDetailView.VIEW_NAME, layout = MenuLayout.class)
public class HeroDetailView extends DetailPage<Hero> {
    public static final String VIEW_NAME = "heroDetail";
    private static final Logger LOGGER = LoggerFactory.getLogger(HeroDetailView.class);

    private final VerticalLayout masterLayout = new VerticalLayout();
    private final HorizontalLayout headerLayout = new HorizontalLayout();
    private final FlexLayout informationContainerLayout = new FlexLayout();
    private final FlexLayout basicInformationLayout = new InformationLayout();
    private final FlexLayout skillInformationLayout = new InformationLayout();
    private final FlexLayout limitBurstLayout = new InformationLayout();

    private final H1 pageHeading = new H1("Hero Name");
    private final H4 descriptionHeading = new H4("Hero Bio");
    private final Image titleImage = new Image();
    private final H2 basicInformationHeading = new H2("Basic Information");
    private final InformationWrapper nameWrapper = new InformationWrapper("Name", null);
    private final InformationWrapper genderWrapper = new InformationWrapper("Gender", null);
    private final H2 skillInformationHeading = new H2("Skill Information");
    private final InformationWrapper skillNameWrapper = new InformationWrapper("Skill Name", null);
    private final InformationWrapper skillDescriptionWrapper = new InformationWrapper("Skill Description", null);
    private final InformationWrapper skillElementWrapper = new InformationWrapper("Skill Element", null);
    private final H2 limitBurstInformationHeading = new H2("Limit Break");
    private final InformationWrapper limitBurstNameWrapper = new InformationWrapper("Name", null);
    private final InformationWrapper limitBurstDescriptionWrapper = new InformationWrapper("Description", null);


    @Autowired
    private HeroRepository heroRepository;

    @Override
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public void setParameter(final BeforeEvent event, final Long heroId) {
        createBindings();
        if(heroId == null) {
            event.rerouteTo(ErrorView.class);
        }
        else {
            Optional<Hero> heroOptional = heroRepository.findById(heroId);
            heroOptional.ifPresentOrElse(hero -> getBinder().setBean(hero), () ->  event.rerouteTo(ErrorView.class));
        }
    }

    @Override
    public String getPageTitle() {
        return getBinder().getBean().getName() + " Details";
    }

    public HeroDetailView() {
        pageHeading.getStyle().set("margin-top", "40px");
        pageHeading.getStyle().set("margin-bottom", "40px");
        titleImage.setWidth("75px");
        titleImage.setHeight("75px");
        titleImage.setSrc(new StreamResource("",
                () -> new ByteArrayInputStream(getBinder().getBean().getImage())));

        headerLayout.setDefaultVerticalComponentAlignment(
                FlexComponent.Alignment.CENTER);
        headerLayout.add(titleImage);
        headerLayout.add(pageHeading);

        descriptionHeading.setWidth("100%");

        basicInformationLayout.setHeight(null);
        basicInformationLayout.add(basicInformationHeading);
        basicInformationLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        basicInformationLayout.add(nameWrapper);
        basicInformationLayout.add(genderWrapper);

        skillInformationLayout.setHeight(null);
        skillInformationLayout.add(skillInformationHeading);
        skillInformationLayout.add(skillNameWrapper);
        skillInformationLayout.add(skillDescriptionWrapper);
        skillInformationLayout.add(skillElementWrapper);

        limitBurstLayout.setHeight(null);
        limitBurstLayout.add(limitBurstInformationHeading);
        limitBurstLayout.add(limitBurstNameWrapper);
        limitBurstLayout.add(limitBurstDescriptionWrapper);

        informationContainerLayout.setSizeFull();
        informationContainerLayout.getStyle().set("flex-wrap", "wrap");
        informationContainerLayout.add(basicInformationLayout);
        informationContainerLayout.add(skillInformationLayout);
        informationContainerLayout.add(limitBurstLayout);

        masterLayout.setSizeFull();
        masterLayout.add(headerLayout);
        masterLayout.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER,
                headerLayout);
        masterLayout.add(descriptionHeading);
        masterLayout.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER,
                descriptionHeading);
        masterLayout.add(informationContainerLayout);
        this.add(masterLayout);
    }

    void createBindings(){
        generateAndBindReadOnly(hero -> pageHeading.setText(hero.getName()));
        generateAndBindReadOnly(hero -> nameWrapper.setComponent(new Label(hero.getName())));
        generateAndBindReadOnly(hero -> descriptionHeading.setText(hero.getBio()));
        generateAndBindReadOnly(hero -> genderWrapper.setComponent(new Label(hero.getGender().getName())));
        generateAndBindReadOnly(hero -> skillNameWrapper.setComponent(new Label(hero.getSkillName())));
        generateAndBindReadOnly(hero -> skillDescriptionWrapper.setComponent(new Label(hero.getSkillDescription())));
        generateAndBindReadOnly(hero -> skillElementWrapper.setComponent(new RouterLink(hero.getSkillElement().getName(), SkillElementDetailView.class, Long.valueOf(Arrays.asList(EElement.values()).indexOf(hero.getSkillElement())))));
        generateAndBindReadOnly(hero -> limitBurstNameWrapper.setComponent(new RouterLink(hero.getLimitBurst().getName(), LimitBurstDetailView.class, hero.getLimitBurst().getId())));
        generateAndBindReadOnly(hero -> limitBurstDescriptionWrapper.setComponent(new Label(hero.getLimitBurst().getDescription())));
    }
}
