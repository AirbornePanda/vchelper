package de.jsauer.valhalla.views;

import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.ElementFactory;
import com.vaadin.flow.router.Route;
import de.jsauer.valhalla.components.MenuLayout;

/**
 * View for displaying the privacy statement.
 */
@Route(value = PrivacyView.VIEW_NAME, layout = MenuLayout.class)
public class PrivacyView extends AbstractView{
    public static final String VIEW_NAME = "privacy";

    public PrivacyView() {
        VerticalLayout statement = new VerticalLayout();
        statement.setAlignItems(FlexComponent.Alignment.CENTER);
        statement.getElement().appendChild(ElementFactory.createHeading1("Privacy Statement"));
        statement.getElement().appendChild(ElementFactory.createLabel("This site is for made to help people, not to collect data. It will collect minimum to none data. All will be explained below."));
        statement.getElement().appendChild(ElementFactory.createListItem("What data does this site collect?"));
        statement.getElement().appendChild(ElementFactory.createListItem("How does this site collect your data?"));
        statement.getElement().appendChild(ElementFactory.createListItem("How does this site store your data?"));
        statement.getElement().appendChild(ElementFactory.createListItem("Marketing"));
        statement.getElement().appendChild(ElementFactory.createListItem("What are your data protection rights?"));
        statement.getElement().appendChild(ElementFactory.createListItem("What are cookies?"));
        statement.getElement().appendChild(ElementFactory.createListItem("How does this site use cookies?"));
        statement.getElement().appendChild(ElementFactory.createListItem("What types of cookies dos this site use?"));
        statement.getElement().appendChild(ElementFactory.createListItem("How to manage your cookies?"));
        statement.getElement().appendChild(ElementFactory.createListItem("Privacy policies of other websites"));
        statement.getElement().appendChild(ElementFactory.createListItem("Changes to this privacy policy"));
        statement.getElement().appendChild(ElementFactory.createListItem("How to contact some of this site"));
        statement.getElement().appendChild(ElementFactory.createListItem("How to contact the appropriate authorities"));
        statement.getElement().appendChild(ElementFactory.createHeading1("What data does this site collect?"));
        statement.getElement().appendChild(ElementFactory.createLabel("This site collects the following data:"));
        statement.getElement().appendChild(ElementFactory.createListItem("Nothing I'm aware of"));
        statement.getElement().appendChild(ElementFactory.createHeading1("How does this site collect your data?"));
        statement.getElement().appendChild(ElementFactory.createLabel("You directly provide this site with most of the data it collects. It collects data and process data when you:"));
        statement.getElement().appendChild(ElementFactory.createListItem("Use or view this website via your browser’s cookies"));
        statement.getElement().appendChild(ElementFactory.createLabel("This site may also receive your data indirectly from the following sources:"));
        statement.getElement().appendChild(ElementFactory.createListItem("None"));
        statement.getElement().appendChild(ElementFactory.createHeading1("How does this site use your data?"));
        statement.getElement().appendChild(ElementFactory.createLabel("This site collects your data so that it can:"));
        statement.getElement().appendChild(ElementFactory.createListItem("Work properly"));
        statement.getElement().appendChild(ElementFactory.createLabel("If you agree, this will share your data with our partner companies so that they may offer you their products and services.:"));
        statement.getElement().appendChild(ElementFactory.createListItem("None"));
        statement.getElement().appendChild(ElementFactory.createHeading1("How does this site store your data?"));
        statement.getElement().appendChild(ElementFactory.createLabel("This site securely stores your data nowhere. There are no data to store."));
        statement.getElement().appendChild(ElementFactory.createHeading1("Marketing"));
        statement.getElement().appendChild(ElementFactory.createLabel("This site would like to send you information about products and services of ours that we think you might like, as well as those of our partner companies:"));
        statement.getElement().appendChild(ElementFactory.createListItem("None"));
        statement.getElement().appendChild(ElementFactory.createLabel("If you have agreed to receive marketing, you may always opt out at a later date. You have the right at any time to stop this site from contacting you for marketing purposes or giving your data to others."));
        statement.getElement().appendChild(ElementFactory.createHeading1("What are your data protection rights?"));
        statement.getElement().appendChild(ElementFactory.createLabel("This site would like to make sure you are fully aware of all of your data protection rights. Every user is entitled to the following:"));
        statement.getElement().appendChild(ElementFactory.createHeading2("The right to access"));
        statement.getElement().appendChild(ElementFactory.createLabel("You have the right to request this site for copies of your personal data. We may charge you a small fee for this service."));
        statement.getElement().appendChild(ElementFactory.createHeading2("The right to rectification"));
        statement.getElement().appendChild(ElementFactory.createLabel("You have the right to request that this correct any information you believe is inaccurate. You also have the right to request this to complete the information you believe is incomplete."));
        statement.getElement().appendChild(ElementFactory.createHeading2("The right to erasure"));
        statement.getElement().appendChild(ElementFactory.createLabel("You have the right to request that this site erases your personal data, under certain conditions."));
        statement.getElement().appendChild(ElementFactory.createHeading2("The right to restrict processing"));
        statement.getElement().appendChild(ElementFactory.createLabel("You have the right to request that this site restricts the processing of your personal data, under certain conditions."));
        statement.getElement().appendChild(ElementFactory.createHeading2("The right to object to processing"));
        statement.getElement().appendChild(ElementFactory.createLabel("You have the right to object to this site’s processing of your personal data, under certain conditions."));
        statement.getElement().appendChild(ElementFactory.createHeading2("The right to data portability"));
        statement.getElement().appendChild(ElementFactory.createLabel("You have the right to request that Our Company transfer the data that we have collected to another organization, or directly to you, under certain conditions."));
        statement.getElement().appendChild(ElementFactory.createLabel("If you make a request, we have one month to respond to you. If you would like to exercise any of these rights, please contact us at our email: TODO ENTER MAIL"));
        statement.getElement().appendChild(ElementFactory.createHeading1("Cookies"));
        statement.getElement().appendChild(ElementFactory.createLabel("Cookies are text files placed on your computer to collect standard Internet log information and visitor behavior information. When you visit our websites, we may collect information from you automatically through cookies or similar technology For further information, visit allaboutcookies.org"));
        statement.getElement().appendChild(ElementFactory.createHeading1("How does this site use cookies?"));
        statement.getElement().appendChild(ElementFactory.createLabel("This site uses cookies in a range of ways to improve your experience on this website, including:"));
        statement.getElement().appendChild(ElementFactory.createListItem("Keeping you signed in"));
        statement.getElement().appendChild(ElementFactory.createListItem("Understanding how you use this website"));
        statement.getElement().appendChild(ElementFactory.createHeading1("What types of cookies does this site use?"));
        statement.getElement().appendChild(ElementFactory.createLabel("There are a number of different types of cookies, however, this website uses:"));
        statement.getElement().appendChild(ElementFactory.createListItem("Functionality – Our Company uses these cookies so that we recognize you on our website and remember your previously selected preferences. These could include what language you prefer and location you are in. A mix of first-party and third-party cookies are used."));
        statement.getElement().appendChild(ElementFactory.createHeading1("How to manage cookies"));
        statement.getElement().appendChild(ElementFactory.createLabel("You can set your browser not to accept cookies, and the above website tells you how to remove cookies from your browser. However, in a few cases, some of this website features may not function as a result."));
        statement.getElement().appendChild(ElementFactory.createHeading1("Privacy policies of other websites"));
        statement.getElement().appendChild(ElementFactory.createLabel("This website contains links to other websites. This privacy policy applies only to this website, so if you click on a link to another website, you should read their privacy policy."));
        statement.getElement().appendChild(ElementFactory.createHeading1("Changes to our privacy policy"));
        statement.getElement().appendChild(ElementFactory.createLabel("This site keeps its privacy policy under regular review and places any updates on this web page. This privacy policy was last updated on 17 April 2019."));
        statement.getElement().appendChild(ElementFactory.createHeading1("How to contact some of this site"));
        statement.getElement().appendChild(ElementFactory.createLabel("If you have any questions about this site’s privacy policy, the data we hold on you, or you would like to exercise one of your data protection rights, please do not hesitate to contact me: Email at: TODO EMAIL"));
        statement.getElement().appendChild(ElementFactory.createHeading1("How to contact the appropriate authorities"));
        statement.getElement().appendChild(ElementFactory.createLabel("Should you wish to report a complaint or if you feel that this site has not addressed your concern in a satisfactory manner, you may contact the Information Commissioner’s Office."));
        this.add(statement);
    }
}
