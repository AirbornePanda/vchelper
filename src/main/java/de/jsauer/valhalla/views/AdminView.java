package de.jsauer.valhalla.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.Route;
import de.jsauer.valhalla.components.Card;
import de.jsauer.valhalla.components.MenuLayout;
import de.jsauer.valhalla.utility.GarmGrabber;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = AdminView.VIEW_NAME, layout = MenuLayout.class)
public class AdminView extends Div {
    public static final String VIEW_NAME = "adminView";

    @Autowired
    private GarmGrabber garmGrabber;

    public AdminView() {
        Card garmCard = new Card("Garm");
        ProgressBar pbPrimaryGarm = new ProgressBar();
        ProgressBar pbSecondaryGarm = new ProgressBar();
        Label statusLabelGarm = new Label();

        Button btImportGarm = new Button("import");
        btImportGarm.addClickListener(event -> garmGrabber.grabBasicHeroInformation(pbPrimaryGarm, pbSecondaryGarm, statusLabelGarm));

        garmCard.addContent(pbPrimaryGarm);
        garmCard.addContent(pbSecondaryGarm);
        garmCard.addContent(statusLabelGarm);
        garmCard.addAction(btImportGarm);

        Card valkCard = new Card("Valkypedia");
        ProgressBar pbPrimaryValk = new ProgressBar();
        ProgressBar pbSecondaryValk = new ProgressBar();
        Label statusLabelValk = new Label();

        Button btImportValk = new Button("import");
        //btImportValk.addClickListener(event -> );

        valkCard.addContent(pbPrimaryValk);
        valkCard.addContent(pbSecondaryValk);
        valkCard.addContent(statusLabelValk);
        valkCard.addAction(btImportValk);

        //Styling
        garmCard.getElement().getClassList().add("col-5");
        garmCard.getElement().getClassList().add("m-3");

        valkCard.getElement().getClassList().add("col-5");
        valkCard.getElement().getClassList().add("m-3");

        getElement().getClassList().add("d-flex");
        getElement().getClassList().add("justify-content-center");

        add(garmCard, valkCard);
    }


}
