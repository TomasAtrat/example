package com.example.application.backend.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReaderConfiguration {
    private String hostname;
    private double power;
    private double RSSI;
    private boolean maxPower;
    private boolean maxRSSI;
    private boolean connected;
}
