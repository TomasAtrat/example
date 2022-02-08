package com.example.application.views;


import com.example.application.views.configuration.ConfigurationView;
import com.example.application.views.helloworld.HelloWorldView;
import com.vaadin.componentfactory.ToggleButton;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Nav;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

/**
 * The main view is a top-level placeholder for other views.
 */
@PageTitle("Main")
public class MainLayout extends AppLayout {

    public MainLayout() {
        DrawerToggle toggle = new DrawerToggle();

        H1 title = new H1("HYC RFID CLOUD SOLUTIONS DEMO");
        title.getStyle()
                .set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        ToggleButton darkMode = new ToggleButton("Modo oscuro");
        darkMode.addValueChangeListener(evt -> {
            ThemeList themeList = UI.getCurrent().getElement().getThemeList();
            if(evt.getValue()) themeList.add(Lumo.DARK);
            else themeList.remove(Lumo.DARK);
        });

        darkMode.getElement().getStyle().set("margin-left", "auto");
        darkMode.getElement().getStyle().set("margin-right", "20px");



        Tab Menu = new Tab("Menu");
        Menu.add(new Icon(VaadinIcon.ELLIPSIS_DOTS_H));
        Tab helloWorld = new Tab("Hello World");
        helloWorld.add(new Icon(VaadinIcon.BELL));
        Tab Configuration = new Tab("Configuration");
        Configuration.add(new Icon(VaadinIcon.SCREWDRIVER));
        Tabs tabs = new Tabs(Menu, helloWorld, Configuration);
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.setAutoselect(false);

        addToDrawer(tabs);
        addToNavbar(toggle, title, darkMode);

        tabs.addSelectedChangeListener(event -> navigator(tabs.getSelectedTab()));
    }

    public void navigator(Tab tabSelected) {
        switch (tabSelected.getLabel()) {
            case "Hello World":
                UI.getCurrent().navigate("hello");
                break;
            case "Configuration":
                UI.getCurrent().navigate("configuration");
                break;
        }
    }
}
