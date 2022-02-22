package com.example.application.backend.common;

import com.example.application.backend.readerConfig.components.ReaderConfig;
import com.impinj.octane.OctaneSdkException;

import static com.example.application.utils.EnvironmentVariables.readerConfiguration;

public abstract class RfidUseCase extends ReaderConfig {

    public void executeUseCase() throws OctaneSdkException { // TEMPLATE
        connect(readerConfiguration.getHostname());
        configureReader();
        read();
        disconnect();
    }

    protected abstract void read();

    protected abstract void configureReader() throws OctaneSdkException;
}