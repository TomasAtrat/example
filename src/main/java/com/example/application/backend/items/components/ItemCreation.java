package com.example.application.backend.items.components;

import com.example.application.backend.common.RfidUseCase;
import com.example.application.views.item.ItemView;
import com.impinj.octane.*;
import com.vaadin.flow.component.UI;

import java.util.Arrays;

public class ItemCreation extends RfidUseCase implements TagReportListener {

    private final UI ui;
    private final ItemView view;

    public ItemCreation(UI ui, ItemView view) {
        this.ui = ui;
        this.view = view;
    }

    private LowDutyCycleSettings ldc;

    @Override
    protected void read() {
        reader.setTagReportListener(this);
    }

    @Override
    protected void configureReader() throws OctaneSdkException {
        Settings settings = getDefaultReportSettings();
        settings.getAntennas().disableById(Arrays.asList(2, 3, 4)); //Only antenna 1 will read
        settings.getReport().setMode(ReportMode.Individual);
        settings.setSearchMode(SearchMode.SingleTarget);
        ldc = settings.getLowDutyCycle();
        ldc.setEmptyFieldTimeoutInMs(2000);
        ldc.setFieldPingIntervalInMs(1000);
        ldc.setIsEnabled(true);
        reader.applySettings(settings);
    }

    @Override
    public void onTagReported(ImpinjReader impinjReader, TagReport tagReport) {
        ldc.setIsEnabled(false);
        ui.access(() -> view.setEpc(tagReport.getTags().get(0).getEpc().toString()));
    }

}
