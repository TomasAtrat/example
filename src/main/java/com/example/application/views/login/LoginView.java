package com.example.application.views.login;

import com.example.application.views.MainLayout;
import com.example.application.views.configuration.ConfigurationView;
import com.example.application.views.item.ItemView;
import com.example.application.views.menu.MainView;
import com.example.application.views.smartShelf.SmartShelfView;
import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Theme(value = Lumo.class)
@JsModule("./login.js")
@JsModule("https://www.gstatic.com/firebasejs/ui/4.8.1/firebase-ui-auth.js")
@JsModule("https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js")
@PageTitle("Login")
@Route(" ")
public class LoginView extends VerticalLayout {

    Button loginButton = new Button("Login");
    EmailField mail = new EmailField("Mail");
    PasswordField password = new PasswordField("Password");
    public static RouteConfiguration routes;

    public LoginView() {
        routeStarter();
        UI.getCurrent().getPage().executeJs("ns.initApp()");
        mail.focus();
        mail.setRequiredIndicatorVisible(true);
        password.setRequired(true);
        add(new H1("HYC RFID CLOUD SOLUTIONS DEMO©"), mail, password, loginButton);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        loginButton.addClickListener(buttonClickEvent -> UI.getCurrent().getPage()
                .executeJs("lg.login($0, $1, $2)", mail.getValue(), password.getValue(), this));
    }

    @ClientCallable
    public void loginOk(String mail) {
        VaadinService.getCurrentRequest().getWrappedSession()
                .setAttribute("mail", mail);

        routeSetter();
        UI.getCurrent().navigate("menu");
    }

    @ClientCallable
    public void loginError() {
        password.setInvalid(true);
        mail.setInvalid(true);
    }

    public void routeStarter() {
        routes = RouteConfiguration.forSessionScope();
        routes.removeRoute("menu");
        routes.removeRoute("configuration");
        routes.removeRoute("items");
        routes.removeRoute("smartShelf");
    }

    public void routeSetter() {
        routes.setRoute("menu", MainView.class, MainLayout.class);
        routes.setRoute("configuration", ConfigurationView.class, MainLayout.class);
        routes.setRoute("items", ItemView.class, MainLayout.class);
        routes.setRoute("smartShelf", SmartShelfView.class, MainLayout.class);
    }

}