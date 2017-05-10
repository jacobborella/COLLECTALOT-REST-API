package org.collectalot.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(scanBasePackages={"org.collectalot.service"})
@Configuration
@ImportResource("classpath:spring-config.xml")
public class Configurator {
    public static void main(String[] args) {
        SpringApplication.run(Configurator.class, args);
    }
}
