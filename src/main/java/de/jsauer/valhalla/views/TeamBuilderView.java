package de.jsauer.valhalla.views;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import de.jsauer.vaadintoolbox.Card;
import de.jsauer.valhalla.components.TeamLayout;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = TeamBuilderView.VIEW_NAME, layout = MenuLayout.class)
public class TeamBuilderView extends AbstractView implements BeforeEnterObserver {
    public static final String VIEW_NAME = "teamBuilder";

    /**
     * Info-text for the settings card.
     */
    private final Label infoText1 = new Label("Click image to change it.");
    /**
     * Info-text for the settings card.
     */
    private final Label infoText2 = new Label("Click name to go to detail site.");
    /**
     * Checkbox for setting icon only mode.
     */
    private final Checkbox checkIconOnly = new Checkbox("Icon only mode");
    /**
     * Checkbox for setting small (1 team) or big (3 teams) teams.
     */
    private final Checkbox checkBigTeam = new Checkbox("Big Team");

    /**
     * The first team.
     */
    @Autowired
    private TeamLayout team1;
    /**
     * The second team.
     */
    @Autowired
    private TeamLayout team2;
    /**
     * The third team.
     */
    @Autowired
    private TeamLayout team3;
    /**
     * Card for settings
     */
    private final Card settingsCard = new Card("Settings");
    /**
     * Button for creating a share of the created team.
     */
    private final Button btShare = new Button("Share", VaadinIcon.SHARE.create());
    /**
     * Layout containing all teams.
     */
    private final VerticalLayout teamLayout = new VerticalLayout();
    /**
     * Layout containing settings and other additional stuff.
     */
    private final VerticalLayout sideLayout = new VerticalLayout();
    /**
     * The master layout of the page.
     */
    private final HorizontalLayout pageLayout = new HorizontalLayout();

    public TeamBuilderView(){
        checkIconOnly.addValueChangeListener(valueChange -> {
            team1.setIconOnly(!valueChange.getValue());
            team2.setIconOnly(!valueChange.getValue());
            team3.setIconOnly(!valueChange.getValue());
        });

        checkBigTeam.addValueChangeListener(valueChange -> {
           team2.setVisible(valueChange.getValue());
           team3.setVisible(valueChange.getValue());
        });

        btShare.setSizeFull();
        VerticalLayout settingsLayout = new VerticalLayout();
        settingsLayout.setPadding(false);
        settingsLayout.add(infoText1, infoText2, checkIconOnly, checkBigTeam);

        settingsCard.addContent(settingsLayout);
        settingsCard.addAction(btShare);

        sideLayout.add(settingsCard);
        sideLayout.setSizeUndefined();
        pageLayout.add(sideLayout, teamLayout);
        this.add(pageLayout);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {

        team2.setVisible(false);
        team3.setVisible(false);

        teamLayout.add(team1, team2, team3);
    }
}
