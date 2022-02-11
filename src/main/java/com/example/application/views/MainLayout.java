package com.example.application.views;


import com.vaadin.componentfactory.ToggleButton;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.PageTitle;
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
        Tab Configuration = new Tab("Configuración");
        Configuration.add(new Icon(VaadinIcon.COG));
        Tab newItem = new Tab("Nuevo elemento");
        newItem.add(new Icon(VaadinIcon.PACKAGE));
        Tabs tabs = new Tabs(Menu, Configuration, newItem);
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.setAutoselect(false);

        addToDrawer(tabs);
        addToNavbar(toggle, title, darkMode);

        tabs.addSelectedChangeListener(event -> navigator(tabs.getSelectedTab()));
    }

    public void navigator(Tab tabSelected) {
        switch (tabSelected.getLabel()) {
            case "Menu":
                UI.getCurrent().navigate("menu");
                break;
            case "Configuración":
                UI.getCurrent().navigate("configuration");
                break;
            case "Nuevo elemento":
                UI.getCurrent().navigate("items");
                break;
        }
    }
}
