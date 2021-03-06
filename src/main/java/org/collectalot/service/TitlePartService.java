package org.collectalot.service;

import javax.servlet.http.HttpServletResponse;

import org.collectalot.dao.TitlePartDAO;
import org.collectalot.model.TitlePart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/title-part")
public class TitlePartService {
	
	@Value("${SW_VERSION}")
	private String swVersion;
	
	@Autowired
//	@Qualifier("titlePartDAO")
	private TitlePartDAO mTitlePartDAO;
	
	/*
	 * Operations regarding children
	 */
	@RequestMapping(path="children", method=RequestMethod.GET)
	public @ResponseBody TitlePart[] getTitlePartChildren() {
		return mTitlePartDAO.getChildren(null);
	}
	@RequestMapping(path="children/{id}", method=RequestMethod.GET)
	public @ResponseBody TitlePart[] getTitlePartChildren(@PathVariable(value="id") String id) {
		return mTitlePartDAO.getChildren(id);
	}
	@RequestMapping(path="children/{id}", method=RequestMethod.POST)
	public @ResponseBody void insertTitlePartChildren(@PathVariable(value="id") String id, @RequestBody TitlePart tp) {
		tp.setParentId(id);
		mTitlePartDAO.insertTitlePart(tp);
	}

	/*
	 * Operations regarding title-part
	 */
	@RequestMapping(path="{id}", method=RequestMethod.GET)
    public @ResponseBody TitlePart getTitlePart(@PathVariable(value="id") String id, HttpServletResponse  response) {
		if(response != null) response.setHeader("api-version", swVersion);
        return mTitlePartDAO.getTitlePart(id);
    }
	@RequestMapping(path="{id}", method=RequestMethod.DELETE)
    public @ResponseBody void deleteTitlePart(@PathVariable(value="id") String id, @RequestParam(value="version") int version) {
        mTitlePartDAO.deleteTitlePart(id, version);
    }
	@RequestMapping(method=RequestMethod.POST)
	public @ResponseBody void insertTitlePart(@RequestBody TitlePart tp) {
		//TODO must be the titlepart with no parent
		mTitlePartDAO.insertTitlePart(tp);
	}
}
