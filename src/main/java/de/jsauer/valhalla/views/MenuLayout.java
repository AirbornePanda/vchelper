package de.jsauer.valhalla.views;

import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.tabs.Tab;
import de.jsauer.valhalla.components.LoginDialog;
import org.springframework.beans.factory.annotation.Autowired;

public class MenuLayout extends AppLayout implements HasComponents{
    @Autowired
    private LoginDialog loginDialog;

    public MenuLayout() {
        this.addToNavbar(new Tab("test"));
        this.addToNavbar(new Tab("test"));
        this.addToNavbar(new Tab("test"));
        this.addToNavbar(new Tab("test"));
        this.addToNavbar(new Tab("test"));
        this.addToNavbar(new Tab("test"));
        this.addToNavbar(new Tab("test"));
        this.addToNavbar(new Tab("test"));
        this.addToNavbar(new Tab("test"));
        this.addToDrawer(new Tab("test"));
    }

    private void changeTheme() {
        String style = this.getElement().getAttribute("theme");

        if (style == null || style.equals("")) {
            this.getElement().setAttribute("theme", "dark");
        } else {
            this.getElement().setAttribute("theme", "");
        }
    }
}
