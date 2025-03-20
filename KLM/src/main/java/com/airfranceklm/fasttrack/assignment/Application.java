package com.airfranceklm.fasttrack.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Spring Boot application.
 * This class is responsible for bootstrapping the application and starting the embedded web server.
 * The `@SpringBootApplication` annotation enables component scanning, autoconfiguration, and configuration support.
 */
@SpringBootApplication
public class Application {

    /**
     * The main method that runs the Spring Boot application.
     * It uses SpringApplication.run to launch the application and start the embedded web server.
     *
     * @param args Command-line arguments passed to the application (if any).
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
