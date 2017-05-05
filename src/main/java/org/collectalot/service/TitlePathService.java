package org.collectalot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

import org.collectalot.model.TitlePath;

@Controller
@Configuration
@RequestMapping("/title-path")
public class TitlePathService {
	
	@Value("${SW_VERSION}")
	private String swVersion;
	
	@RequestMapping(method=RequestMethod.GET)
    public @ResponseBody TitlePath getTitlePath(@RequestParam(value="id", required=false) Long id, HttpServletResponse  response) {
		TitlePath path = new TitlePath();
		if(id != null) {
			path.setId(id);
		}
		path.setName("Walt Disneys Comics & Stories");
		response.setHeader("api-version", swVersion);
        return path;
    }
}
