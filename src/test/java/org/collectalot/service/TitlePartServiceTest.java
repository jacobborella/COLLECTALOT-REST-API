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
	public void doit() {
		TitlePart tp = new TitlePart();
		tp.setId(1);
		tp.setName("foo");
		Mockito.when(mTitlePartDAO.getTitlePart(1)).thenReturn(tp);
		TitlePart tpRes = service.getTitlePart(1L, null);
		assert tpRes.getId() == 1;
		assert "foo".equals(tpRes.getName());
	}
}
