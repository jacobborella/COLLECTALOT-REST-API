package org.collectalot.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"org.collectalot.service", "org.collectalot.config"})
public class Configurator {
    public static void main(String[] args) {
        SpringApplication.run(Configurator.class, args);
    }
}
