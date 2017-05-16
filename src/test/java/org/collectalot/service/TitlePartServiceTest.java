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
}
