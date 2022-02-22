package com.example.application;

import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.server.PWA;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication
@NpmPackage(value = "lumo-css-framework", version = "^4.0.10")
@NpmPackage(value = "line-awesome", version = "1.3.0")
@PWA(name = "HYC RFID Cloud Solutions DEMO",
        shortName = "HYC RFID Cloud",
        offlinePath="offline.html",
        offlineResources = "./images/icon.png",
        description = "This is a Cloud RFID Demo App",
        enableInstallPrompt = true
)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
