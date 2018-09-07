package de.jsauer.spring.views;

import com.vaadin.external.org.slf4j.Logger;
import com.vaadin.external.org.slf4j.LoggerFactory;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.StreamResource;
import de.jsauer.spring.Application;
import de.jsauer.spring.backend.entities.Hero;
import de.jsauer.spring.backend.repositories.HeroRepository;
import de.jsauer.spring.utility.GarmGrabber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

@Route
@PageTitle("Hero Overview")
public class MainView extends VerticalLayout {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    private final Grid<Hero> heroGrid = new Grid<>();
    private final TextField heroNameFilter = new TextField();

    public MainView(@Autowired HeroRepository heroRepository, @Autowired GarmGrabber garmGrabber) {
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
        heroGrid.addColumn(hero -> hero.getInitialRarity() == null ? "No rarity found!" : hero.getInitialRarity()).setSortProperty("initialRarity").setHeader("Initial rarity");
        heroGrid.addColumn(hero -> hero.getDamageType() == null ? "No damage type found!" : hero.getDamageType().getNameShort()).setSortProperty("damageType").setHeader("Attack Type");
        heroGrid.addColumn(hero -> hero.getGender() == null ? "No gender defined!" : hero.getGender().getName()).setSortProperty("gender").setHeader("Gender");
        heroGrid.setMultiSort(true);

        heroGrid.setSizeFull();

        heroGrid.setDataProvider(filteredHeroDataProvider);

        this.setSizeFull();
        add(heroGrid);

        Button btImport = new Button("import");
        btImport.addClickListener(event -> {
            garmGrabber.grabBasicHeroInformation();
            heroProvider.refreshAll();
        });

        add(btImport);
    }

    public static Pair<Integer, Integer> limitAndOffsetToPageSizeAndNumber(
            int offset, int limit) {
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
}