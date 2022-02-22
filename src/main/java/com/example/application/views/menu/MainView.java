package com.example.application.views.menu;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;

import static com.vaadin.flow.component.Unit.PIXELS;

@PageTitle("Menu")
public class MainView extends VerticalLayout {
    public MainView() {
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        Image image = new Image("ilustration.png", "Logo");
        image.setMaxWidth(700, PIXELS);
        image.setMaxHeight(600, PIXELS);
        add(image);
    }
}
