package com.example.application.backend.readerConfig.components;

import com.impinj.octane.*;
import org.springframework.stereotype.Component;

@Component
public class ReaderConfig {

    public ReaderConfig(){
        reader = new ImpinjReader();
    }

    protected final ImpinjReader reader;

    public void connect(String hostname) throws OctaneSdkException {
        reader.connect(hostname);
    }

    public void disconnect(){
        reader.disconnect();
    }

    public Settings getDefaultReportSettings() throws OctaneSdkException {
        Settings settings = reader.queryDefaultSettings();
        settings.getReport().setIncludeAntennaPortNumber(true);
        settings.getReport().setIncludeChannel(true);
        settings.getReport().setIncludePeakRssi(true);
        settings.getReport().setIncludeSeenCount(true);
        settings.getReport().setMode(ReportMode.Individual);
        return settings;
    }

    public void configureAntennaPower(double power, boolean isMaxPower) throws OctaneSdkException {
        Settings settings = reader.queryDefaultSettings();
        AntennaConfig antenna = settings.getAntennas().getAntenna(1);
        if (isMaxPower) antenna.setIsMaxTxPower(true);
        else antenna.setTxPowerinDbm(power);

        reader.applySettings(settings);
    }

    public void configureAntennaSensitivity(double rssi, boolean isMaxRSSI) throws OctaneSdkException {
        Settings settings = reader.queryDefaultSettings();
        AntennaConfig antenna = settings.getAntennas().getAntenna(1);
        if (isMaxRSSI) antenna.setIsMaxRxSensitivity(true);
        else antenna.setRxSensitivityinDbm(rssi);

        reader.applySettings(settings);
    }
}
