package org.collectalot.config;

import org.collectalot.dao.TitlePartDAO;
//import org.collectalot.dao.TitlePartDAOMock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
@Component
public class TitlePartDAOConfig {
	@Value("${ENV}")
	private String env;

	@Bean
    public TitlePartDAO titlePartDAO() {
		return new TitlePartDAO();
    }

}
