package org.collectalot.config;

import org.collectalot.dao.TitlePartDAO;
import org.collectalot.dao.TitlePartDAOMock;
import org.collectalot.dao.TitlePartInterface;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(scanBasePackages={"org.collectalot.service"})
//@ImportResource("classpath:spring-config.xml")
public class Configurator {
	@Value("${ENV}")
	private String env;
    public static void main(String[] args) {
        SpringApplication.run(Configurator.class, args);
    }
    @Bean
    public TitlePartInterface titlePartDAO() {
    	if("dev".equals(env)) {
    		return new TitlePartDAOMock();
    	} else {
    		return new TitlePartDAO();
    	}
    }
}
