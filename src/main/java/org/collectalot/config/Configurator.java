package org.collectalot.config;

import org.collectalot.dao.TitlePartDAO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(scanBasePackages={"org.collectalot.service"})
//@ImportResource("classpath:spring-config.xml")
public class Configurator {
    public static void main(String[] args) {
        SpringApplication.run(Configurator.class, args);
    }
    @Bean
    public TitlePartDAO titlePartDAO() {
    	return new TitlePartDAO();
    }
}
