package de.jsauer.spring.views;

import com.vaadin.external.org.slf4j.Logger;
import com.vaadin.external.org.slf4j.LoggerFactory;
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
import com.vaadin.flow.server.StreamResource;
import de.jsauer.spring.backend.entities.Hero;
import de.jsauer.spring.backend.repositories.HeroRepository;
import de.jsauer.spring.components.InformationWrapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.util.Optional;

@Route("heroDetail")
public class HeroDetailView extends DetailPage<Hero> {
    private static final Logger log = LoggerFactory.getLogger(HeroDetailView.class);

    private final VerticalLayout masterLayout = new VerticalLayout();
    private final HorizontalLayout headerLayout = new HorizontalLayout();
    private final FlexLayout primaryInformationLayout = new FlexLayout();
    private final VerticalLayout basicInformationLayout = new VerticalLayout();
    private final VerticalLayout skillInformationLayout = new VerticalLayout();


    private final H1 pageHeading = new H1("Hero Name");
    private final H4 descriptionHeading = new H4("Hero Bio");
    private final Image titleImage = new Image();
    private final H2 basicInformationHeading = new H2("Basic Information");
    private final InformationWrapper nameWrapper = new InformationWrapper("Name", null);
    private final InformationWrapper genderWrapper = new InformationWrapper("Gender", null);
    private final H2 skillInformationHeading = new H2("Skill Information");
    private final InformationWrapper skillElementWrapper = new InformationWrapper("Skill Element", null);

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

        basicInformationLayout.setWidth("50%");
        basicInformationLayout.setHeight(null);
        basicInformationLayout.setSpacing(false);
        basicInformationLayout.add(basicInformationHeading);
        basicInformationLayout.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER,
                basicInformationHeading);
        basicInformationLayout.add(nameWrapper);
        basicInformationLayout.add(genderWrapper);
        basicInformationLayout.getStyle().set("min-width", "min-content");

        skillInformationLayout.setWidth("50%");
        skillInformationLayout.setHeight(null);
        skillInformationLayout.setSpacing(false);
        skillInformationLayout.add(skillInformationHeading);
        skillInformationLayout.add(skillElementWrapper);
        skillInformationLayout.getStyle().set("min-width", "min-content");

        primaryInformationLayout.setSizeFull();
        primaryInformationLayout.getStyle().set("flex-wrap", "wrap");
        primaryInformationLayout.add(basicInformationLayout);
        primaryInformationLayout.add(skillInformationLayout);

        masterLayout.setSizeFull();
        masterLayout.add(headerLayout);
        masterLayout.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER,
                headerLayout);
        masterLayout.add(descriptionHeading);
        masterLayout.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER,
                descriptionHeading);
        masterLayout.add(primaryInformationLayout);
        this.add(masterLayout);
    }

    void createBindings(){
        generateAndBindReadOnly(hero -> pageHeading.setText(hero.getName()));
        generateAndBindReadOnly(hero -> nameWrapper.setComponent(new Label(hero.getName())));
        generateAndBindReadOnly(hero -> descriptionHeading.setText(hero.getBio()));
        generateAndBindReadOnly(hero -> genderWrapper.setComponent(new Label(hero.getGender().getName())));
        generateAndBindReadOnly(hero -> skillElementWrapper.setComponent(new Label(hero.getSkillElement().getName())));
    }
}
