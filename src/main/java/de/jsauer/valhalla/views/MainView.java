package de.jsauer.valhalla.views;

import com.vaadin.external.org.slf4j.Logger;
import com.vaadin.external.org.slf4j.LoggerFactory;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.StreamResource;
import de.jsauer.valhalla.backend.entities.Hero;
import de.jsauer.valhalla.backend.repositories.HeroRepository;
import de.jsauer.valhalla.utility.GarmGrabber;
import de.jsauer.valhalla.utility.ValkypediaGrabber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

@Route(value = MainView.VIEW_NAME, layout = MenuLayout.class)
@PageTitle("Hero Overview")
public class MainView extends AbstractView {
    public static final String VIEW_NAME = "";
    private static final Logger LOGGER = LoggerFactory.getLogger(MainView.class);

    private final Grid<Hero> heroGrid = new Grid<>();
    private final TextField heroNameFilter = new TextField();

    public MainView(@Autowired HeroRepository heroRepository, @Autowired GarmGrabber garmGrabber, @Autowired ValkypediaGrabber valkypediaGrabber) {
        DataProvider<Hero, String> heroProvider = DataProvider.fromFilteringCallbacks(
                query -> {
                    Pair<Integer, Integer> limitOffsetPair = limitAndOffsetToPageSizeAndNumber(query.getOffset(), query.getLimit());
                    List<Sort.Order> orderList = new ArrayList<>();
                    query.getSortOrders().forEach(querySortOrder -> orderList.add(new Sort.Order(Sort.Direction.fromString(querySortOrder.getDirection().name().equals("ASCENDING") ? "asc" : "desc") , querySortOrder.getSorted())));
                    if (query.getFilter().isPresent()){
                        return heroRepository.findAllByNameIgnoreCaseContaining(query.getFilter().get(),
                                PageRequest.of(limitOffsetPair.getSecond(),
                                        limitOffsetPair.getFirst(),
                                        Sort.by(orderList)))
                                .stream()
                                .skip(((query.getOffset() + query.getLimit()) - limitOffsetPair.getFirst() * limitOffsetPair.getSecond()) - query.getLimit())
                                .limit(query.getLimit());
                    } else {
                        return heroRepository.findAll(PageRequest.of(limitOffsetPair.getSecond(),
                                        limitOffsetPair.getFirst(),
                                        Sort.by(orderList)))
                                .stream()
                                .skip(((query.getOffset() + query.getLimit()) - limitOffsetPair.getFirst() * limitOffsetPair.getSecond()) - query.getLimit())
                                .limit(query.getLimit());
                    }

                },
                query -> {
                    if (query.getFilter().isPresent()){
                        return Long.valueOf(heroRepository.countByNameIgnoreCaseContaining(query.getFilter().get())).intValue();
                    } else {
                        return Long.valueOf(heroRepository.count()).intValue();
                    }

                }
        );

        ConfigurableFilterDataProvider<Hero, Void, String> filteredHeroDataProvider = heroProvider.withConfigurableFilter();

        heroNameFilter.setValueChangeMode(ValueChangeMode.EAGER);

        heroNameFilter.addValueChangeListener(valueChangeEvent -> {
           if (valueChangeEvent.getValue() == null) {
               filteredHeroDataProvider.setFilter(null);
           } else {
               filteredHeroDataProvider.setFilter(valueChangeEvent.getHasValue().getValue());
           }
        });

        heroNameFilter.setPlaceholder("Name...");

        //Column for hero image
        heroGrid.addComponentColumn(hero -> {
            if (hero.getImage() != null) {
                Image image = new Image();
                image.setSrc(new StreamResource("", () -> new ByteArrayInputStream(hero.getImage())));
                image.setHeight("75px");
                image.setWidth("75px");
                return image;
            } else {
                return new Label("No Image found");
            }
        }).setSortProperty("id").setHeader("");
        //Column for hero name with link to Detail page
        heroGrid.addComponentColumn(hero -> {
            if (hero.getName() != null) {
                return new RouterLink(hero.getName() , HeroDetailView.class, hero.getId());
            } else {
                return new Label("No name found");
            }
        }).setSortProperty("name").setHeader(heroNameFilter);
        heroGrid.addComponentColumn(hero -> hero.getInitialRarity() == null ? new Label("No rarity found!") : generateRarity(hero)).setSortProperty("initialRarity").setHeader("Initial rarity");
        heroGrid.addColumn(hero -> hero.getDamageType() == null ? "No damage type found!" : hero.getDamageType().getNameShort()).setSortProperty("damageType").setHeader("Attack Type");
        heroGrid.addComponentColumn(hero -> hero.getLimitBurst() == null ? new Label("No limit break found!") : new RouterLink(hero.getLimitBurst().getName(), LimitBurstDetailView.class, hero.getLimitBurst().getId())).setHeader("Limit Break").setSortProperty("limitBurst");
        heroGrid.addColumn(hero -> hero.getGender() == null ? "No gender defined!" : hero.getGender().getName()).setSortProperty("gender").setHeader("Gender");
        heroGrid.setMultiSort(true);

        heroGrid.setWidth("100%");
        heroGrid.setMinHeight("100px");

        heroGrid.setDataProvider(filteredHeroDataProvider);

        Button btImport = new Button("import");
        btImport.addClickListener(event -> {
            valkypediaGrabber.grabAllGear();
            heroProvider.refreshAll();
        });

        setHeight("100%");
        getStyle().set("display", "flex");
        getStyle().set("flex-direction", "column");
        add(heroGrid);
        add(btImport);
    }

    private static Pair<Integer, Integer> limitAndOffsetToPageSizeAndNumber(
            final int offset, final int limit) {
        int minPageSize = limit;
        int lastIndex = offset + limit - 1;
        int maxPageSize = lastIndex + 1;

        for (double pageSize = minPageSize; pageSize <= maxPageSize; pageSize++) {
            int startPage = (int) (offset / pageSize);
            int endPage = (int) (lastIndex / pageSize);
            if (startPage == endPage) {
                // It fits on one page, let's go with that
                return Pair.of((int) pageSize, startPage);

            }
        }

        // Should not really get here
        return Pair.of(maxPageSize, 0);
    }

    /**
     * Generates stars displayed for the {@link Hero#getInitialRarity()}.
     * @param hero a {@link Hero}
     * @return a {@link HorizontalLayout} containing stars and the number.
     */
    private HorizontalLayout generateRarity(final Hero hero) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        if(hero.getInitialRarity() != null && hero.getInitialRarity() > 0) {
            for (int i = 0; i < hero.getInitialRarity(); i++) {
                Icon starIcon = VaadinIcon.STAR.create();
                starIcon.setColor("Yellow");
                horizontalLayout.add(starIcon);
            }
            horizontalLayout.add(new Label("(" + hero.getInitialRarity() + ")"));
        } else {
            horizontalLayout.add(new Label("(0)"));
        }

        return horizontalLayout;
    }
}