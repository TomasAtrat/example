package com.example.application.views.smartShelf;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.PageTitle;

@PageTitle("Smart Shelf")
public class SmartShelfView extends VerticalLayout {
    public SmartShelfView() {
        add(new H2("Smart shelf"));
        displayTabs();
    }

    private void displayTabs() {
        Tab panel = new Tab(
                VaadinIcon.PANEL.create(),
                new Span("Panel")
        );

        Tab itemsInShelf = new Tab(
                VaadinIcon.CLIPBOARD_CHECK.create(),
                new Span("Items presentes")
        );

        Tab itemsOutOfShelf = new Tab(
                VaadinIcon.CLIPBOARD_CROSS.create(),
                new Span("Items ausentes")
        );

        Tab reports = new Tab(
                VaadinIcon.CHART.create(),
                new Span("Reportes")
        );

        setIconOnTop(panel, itemsInShelf, itemsOutOfShelf, reports);
    }

    private void setIconOnTop(Tab... tabs) {
        Tabs _tabs = new Tabs();
        for (Tab tab : tabs) {
            tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
            _tabs.add(tab);
        }
        _tabs.setSizeFull();
        _tabs.addThemeVariants(TabsVariant.LUMO_EQUAL_WIDTH_TABS);
        add(_tabs);
    }

}
