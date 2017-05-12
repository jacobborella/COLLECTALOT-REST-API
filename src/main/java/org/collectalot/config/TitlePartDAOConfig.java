package org.collectalot.config;

import org.collectalot.dao.TitlePartDAO;
//import org.collectalot.dao.TitlePartDAOMock;
import org.collectalot.dao.TitlePartInterface;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
@Component
public class TitlePartDAOConfig {
	@Value("${ENV}")
	private String env;

	@Bean
    public TitlePartInterface titlePartDAO() {
//    	if("dev".equals(env)) {
//    		return new TitlePartDAOMock();
//    	} else {
    		return new TitlePartDAO();
//    	}
    }

}
