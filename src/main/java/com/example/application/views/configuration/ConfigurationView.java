package com.example.application.views.configuration;

import com.example.application.backend.models.ReaderConfiguration;
import com.example.application.backend.readerConfig.components.ReaderConfig;
import com.example.application.backend.readerConfig.services.ReaderConfigService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static com.example.application.utils.EnvironmentVariables.readerConfiguration;
import static com.vaadin.flow.component.notification.Notification.Position.MIDDLE;

@PageTitle("Configuration")
public class ConfigurationView extends VerticalLayout {

    private TextField hostname;
    private Select<Double> powerOptions;
    private Select<Double> rssiOptions;
    private Checkbox isMaxPower;
    private Checkbox isMaxRSSI;
    private Button connect;
    private final ReaderConfigService readerConfigService;

    @Autowired
    public ConfigurationView(ReaderConfigService readerConfigService) {
        this.readerConfigService = readerConfigService;
        add(new H2("Configuración del lector"));
        setJustifyContentMode(JustifyContentMode.START);
        setAlignItems(Alignment.BASELINE);
        displayForm();
        if (readerConfiguration == null) readerConfiguration = readerConfigService.getLastReader();
        loadLastReader();
    }

    private void loadLastReader() {
        hostname.setValue(readerConfiguration.getHostname());
        if (!readerConfiguration.isMaxPower() && readerConfiguration.getPower() != 0)
            powerOptions.setValue(readerConfiguration.getPower());
        if (!readerConfiguration.isMaxRSSI() && readerConfiguration.getRSSI() != 0)
            rssiOptions.setValue(readerConfiguration.getRSSI());
        isMaxPower.setValue(readerConfiguration.isMaxPower());
        isMaxRSSI.setValue(readerConfiguration.isMaxRSSI());
    }

    private void displayForm() {
        addHostnameInput();
        setCheckboxes();
        setPowerSelect();
        setRssiSelect();
        configureReaderVariables();
        configureSaveButton();
    }

    private void configureSaveButton() {
        connect = new Button("Guardar");
        setBehaviour();
        configureButtonLayout();
    }

    private void configureButtonLayout() {
        HorizontalLayout saveButtonLayout = new HorizontalLayout(connect);
        saveButtonLayout.setWidth("50%");
        saveButtonLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        add(saveButtonLayout);
    }

    private void setBehaviour() {
        connect.addClickListener(event -> {
            if (!hostnameIsNullOrEmpty()) {
                ReaderConfiguration readerConfig = new ReaderConfiguration();
                readerConfig.setHostname(hostname.getValue());
                if (!isMaxPower.getValue() && powerOptions.getValue() != null)
                    readerConfig.setPower(powerOptions.getValue());
                readerConfig.setMaxPower(isMaxPower.getValue());
                if (!isMaxRSSI.getValue() && rssiOptions.getValue() != null)
                    readerConfig.setRSSI(rssiOptions.getValue());
                readerConfig.setMaxRSSI(isMaxRSSI.getValue());
                readerConfigService.addReader(readerConfig);
                readerConfiguration = readerConfig;
                Notification.show("Se ha añadido la configuración del lector.", 5000, MIDDLE);

                connectToReader(readerConfig.getHostname());
            } else Notification.show("Completa el campo 'Hostname o dirección IP'", 5000, MIDDLE);
        });
    }

    private boolean hostnameIsNullOrEmpty() {
        return hostname.getValue() == null || hostname.getValue().trim().isEmpty();
    }

    private void connectToReader(String hostname) {
        try {
            ReaderConfig readerConfig = new ReaderConfig();
            readerConfig.connect(hostname);
            readerConfiguration.setConnected(true);
            Notification.show("El lector se ha conectado correctamente.", 5000, MIDDLE);
        } catch (Exception e) {
            readerConfiguration.setConnected(false);
            Notification.show("No se ha podido establecer una conexión con el lector. " +
                    "Compruebe que el lector esté encendido y en la misma red.", 5000, MIDDLE);
        }
    }

    private void configureReaderVariables() {
        HorizontalLayout readerVariables = new HorizontalLayout(getPowerLayout(), getRssiLayout());
        readerVariables.setSizeFull();
        readerVariables.setJustifyContentMode(JustifyContentMode.EVENLY);
        add(readerVariables);
    }

    private VerticalLayout getRssiLayout() {
        VerticalLayout rssiConfig = new VerticalLayout();
        rssiConfig.setWidth("50%");
        rssiConfig.setJustifyContentMode(JustifyContentMode.BETWEEN);
        HorizontalLayout horizontalRssiOpts = new HorizontalLayout(isMaxRSSI, rssiOptions);
        horizontalRssiOpts.setSizeFull();
        horizontalRssiOpts.setAlignItems(Alignment.BASELINE);
        rssiConfig.add(new H4("RSSI"), horizontalRssiOpts);
        return rssiConfig;
    }

    private VerticalLayout getPowerLayout() {
        VerticalLayout powerConfig = new VerticalLayout();
        powerConfig.setWidth("50%");
        powerConfig.setJustifyContentMode(JustifyContentMode.BETWEEN);
        HorizontalLayout horizontalPowerOpts = new HorizontalLayout(isMaxPower, powerOptions);
        horizontalPowerOpts.setSizeFull();
        horizontalPowerOpts.setAlignItems(Alignment.BASELINE);
        powerConfig.add(new H4("POTENCIA"), horizontalPowerOpts);
        return powerConfig;
    }

    private void addHostnameInput() {
        hostname = new TextField("Hostname o dirección IP");
        hostname.setRequired(true);
        hostname.setRequiredIndicatorVisible(true);
        hostname.addValueChangeListener(event -> connect.setEnabled(event.getValue() != null && !event.getValue().trim().isEmpty()));
        VerticalLayout hostnameLayout = new VerticalLayout(hostname);
        hostnameLayout.setWidth("50%");
        hostnameLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        add(hostnameLayout);
    }

    private void setCheckboxes() {
        isMaxPower = new Checkbox();
        isMaxPower.setLabel("Valor máximo");
        isMaxPower.addValueChangeListener(event -> powerOptions.setEnabled(!event.getValue()));
        isMaxRSSI = new Checkbox();
        isMaxRSSI.setLabel("Valor máximo");
        isMaxRSSI.addValueChangeListener(event -> rssiOptions.setEnabled(!event.getValue()));
    }

    private void setPowerSelect() {
        powerOptions = new Select<>();
        powerOptions.setLabel("Personalizado (dbm)");
        List<Double> items = new ArrayList<>();
        for (double i = 12; i <= 30; i += 3) {
            items.add(-i);
        }
        powerOptions.setItems(items);
        powerOptions.setEmptySelectionAllowed(true);
        powerOptions.setEmptySelectionCaption("Valor por defecto");
    }

    private void setRssiSelect() {
        rssiOptions = new Select<>();
        rssiOptions.setLabel("Personalizado (dbm)");
        List<Double> items = new ArrayList<>();
        for (double i = 40; i <= 80; i += 5) {
            items.add(-i);
        }
        rssiOptions.setItems(items);
        rssiOptions.setEmptySelectionAllowed(true);
        rssiOptions.setEmptySelectionCaption("Valor por defecto");
    }
}
