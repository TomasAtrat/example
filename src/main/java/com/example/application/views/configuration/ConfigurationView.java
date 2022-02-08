package com.example.application.views.configuration;

import com.example.application.views.MainLayout;
import com.impinj.octane.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Configuration")
@Route(value = "configuration", layout = MainLayout.class)
public class ConfigurationView extends HorizontalLayout implements TagReportListener {

    private TextField name;
    private Button connect;
    private Button readTags;
    private Button stopReading;
    static ImpinjReader reader = new ImpinjReader();

    public ConfigurationView() {
        name = new TextField("Dirección IP del lector");
        connect = new Button("Conectarse");
        connect.addClickListener(e -> {
            try {
                reader.connect(name.getValue());
                readTags.setEnabled(true);
            } catch (OctaneSdkException octaneSdkException) {
                octaneSdkException.printStackTrace();
                Notification.show("No ha sido posible establecer una conexión con el lector", 5000, Notification.Position.MIDDLE);
            }
        });

        readTags = new Button("Leer tags", e -> {
            try {
                Settings settings = reader.queryDefaultSettings();
                settings.getReport().setIncludeAntennaPortNumber(true);
                settings.getReport().setMode(ReportMode.Individual);
                settings.setRfMode(1000);
                settings.setSearchMode(SearchMode.DualTarget);
                settings.setSession(2);
                reader.applySettings(settings);
                reader.setTagReportListener(this);
                reader.start();
                stopReading.setEnabled(true);
            } catch (OctaneSdkException octaneSdkException) {
                octaneSdkException.printStackTrace();
            }
        });

        stopReading = new Button("Detener lectura", e -> {
            try {
                reader.stop();
                reader.disconnect();
            } catch (OctaneSdkException octaneSdkException) {
                octaneSdkException.printStackTrace();
            }
        });

        stopReading.setEnabled(false);
        readTags.setEnabled(false);
        setMargin(true);
        setVerticalComponentAlignment(Alignment.END, name, connect);

        add(name, connect, readTags, stopReading);
    }

    @Override
    public void onTagReported(ImpinjReader impinjReader, TagReport tagReport) {
        tagReport.getTags().forEach(tag-> {
            System.out.println("tag.getEpc() = " + tag.getEpc());
            System.out.println("tag.getAntennaPortNumber() = " + tag.getAntennaPortNumber());
        });
    }
}
