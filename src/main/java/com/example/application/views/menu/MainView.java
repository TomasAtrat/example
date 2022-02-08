package com.example.application.views.menu;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;

@PageTitle("menu")
@CssImport("./main.css")
public class MainView extends VerticalLayout {
    public MainView(){
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        /*Image image = new Image("newuser.png", "Logo");
        add(image);*/
    }
}