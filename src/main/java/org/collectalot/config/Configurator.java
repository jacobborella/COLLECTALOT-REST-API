package org.collectalot.config;

import org.collectalot.dao.TitlePartDAO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages={"org.collectalot.service"})

public class Configurator {
    public static void main(String[] args) {
        SpringApplication.run(Configurator.class, args);
    }
    @Bean
    public TitlePartDAO titlePartDAO() {
    	return new TitlePartDAO();
    }
}
