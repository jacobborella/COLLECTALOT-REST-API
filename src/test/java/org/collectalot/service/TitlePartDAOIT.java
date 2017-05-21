package org.collectalot.service;

import org.collectalot.dao.TitlePartDAO;
import org.collectalot.model.TitlePart;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=org.collectalot.dao.TitlePartDAO.class)
public class TitlePartDAOIT {
	@Autowired
	private TitlePartDAO titlePartDAO;
	
	@Value("${ENV}")
	private String env;
	
	private String rootId, child0Id, child1Id;
	
	@Before
	public void setupMongoDB() {
		//setup objects
		if(!"DEV".equals(env.toUpperCase())) {
			throw new IllegalAccessError("These tests are not suitable for production.");
		}
		titlePartDAO.clearDB();
		TitlePart tpParent = new TitlePart();
		tpParent.setName("Comics");
		titlePartDAO.insertTitlePart(tpParent);
		
		TitlePart[] root = titlePartDAO.getChildren(null);
		
		TitlePart child0 = new TitlePart();
		child0.setName("Four Color");
		child0.setParentId(root[0].getId());
		titlePartDAO.insertTitlePart(child0);
		TitlePart child1 = new TitlePart();
		child1.setName("Comics & Stories");
		child1.setParentId(root[0].getId());
		titlePartDAO.insertTitlePart(child1);
		
		rootId = root[0].getId();
	}
	@Test
	public void testGetTitlePart() {
		TitlePart tp = titlePartDAO.getTitlePart(rootId);
		assert "Comics".equals(tp.getName());
		assert 1 == tp.getVersion();
	}
	@Test
	public void testGetTitlePartChildren() {
		TitlePart[] root = titlePartDAO.getChildren(null);
		assert root.length == 1;
		assert "Comics".equals(root[0].getName());
		TitlePart[] children = titlePartDAO.getChildren(root[0].getId());
		assert children.length == 2;
		assert "Four Color".equals(children[0].getName());
		assert "Comics & Stories".equals(children[1].getName());	
	}
	@Test
	public void testInsertTitlePart() {
		try {
			TitlePart tp = new TitlePart();
			tp.setName("Comics & Stories");
			tp.setParentId(rootId);
			titlePartDAO.insertTitlePart(tp);
			assert false: "Insert with identical object didn't fail as expected";
		} catch (Exception e) {
			//do nothing working as expected
		}
	}
}
