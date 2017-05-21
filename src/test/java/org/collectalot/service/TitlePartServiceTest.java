package org.collectalot.service;

import org.collectalot.dao.TitlePartDAO;
import org.collectalot.model.TitlePart;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TitlePartServiceTest {
	@InjectMocks
    private TitlePartService service;

	@Mock
	private TitlePartDAO mTitlePartDAO = new TitlePartDAO();

    @Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void testGetTitlePartChildren() {
		TitlePart tpNull = new TitlePart();
		tpNull.setName("AAA");
		TitlePart tpId = new TitlePart();
		tpId.setName("BBB");
		
		Mockito.when(mTitlePartDAO.getChildren(null)).thenReturn(new TitlePart[]{tpNull});
		Mockito.when(mTitlePartDAO.getChildren("3")).thenReturn(new TitlePart[]{tpId});
		Mockito.when(mTitlePartDAO.getChildren("5")).thenReturn(new TitlePart[]{});

		assert "AAA".equals(service.getTitlePartChildren(null)[0].getName());
		assert "BBB".equals(service.getTitlePartChildren("3")[0].getName());
		assert 0 == service.getTitlePartChildren("5").length;
	}

	@Test
	public void testInsertTitlePartChildren() {
		class TitlePartTest extends TitlePart {
			@Override
			public boolean equals(Object obj) {
				if(!(obj instanceof TitlePart)) {
					return false;
				}
				TitlePart other = (TitlePart) obj;
				return other.getParentId().equals(getParentId()) &&
					   other.getName().equals(getName()) &&
					   other.getVersion() == getVersion() &&
					   other.getId().equals(getId());
			}
		}
		TitlePart tpi = new TitlePartTest();
		tpi.setId("1");
		tpi.setName("foo");
		TitlePart tpo = new TitlePartTest();
		tpo.setId("1");
		tpo.setName("foo");
		tpo.setParentId("123");
		service.insertTitlePartChildren("123", tpi);
		Mockito.verify(mTitlePartDAO).insertTitlePart(tpo);
	}

	@Test
	public void testGetTitlePart() {
		TitlePart tp = new TitlePart();
		tp.setId("1");
		tp.setName("foo");
		Mockito.when(mTitlePartDAO.getTitlePart("1")).thenReturn(tp);
		TitlePart tpRes = service.getTitlePart("1", null);
		assert "1".equals(tpRes.getId());
		assert "foo".equals(tpRes.getName());
	}

	@Test
	public void testDeleteTitlePart() {
		service.deleteTitlePart("foo", 1);
		Mockito.verify(mTitlePartDAO).deleteTitlePart("foo", 1);
	}
	
	@Test
	public void testInsertTitlePart() {
		TitlePart tp = new TitlePart();
		service.insertTitlePart(tp);
		Mockito.verify(mTitlePartDAO).insertTitlePart(tp);
	}
}
