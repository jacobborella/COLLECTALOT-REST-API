package org.collectalot.dao;

import java.util.ArrayList;

import org.collectalot.model.TitlePart;

public class TitlePartDAO implements TitlePartInterface {
	/* (non-Javadoc)
	 * @see org.collectalot.dao.TitlePartInterface#getTitlePart(long)
	 */
	@Override
	public TitlePart getTitlePart(long id) {
		if(id!=1) return null;
		TitlePart titlePart = new TitlePart();
		titlePart.setId(id);
		titlePart.setName("Walt Disney's Comics & Stories");
		return titlePart;
	}

	@Override
	public TitlePart[] getChildren(long parentId) {
		ArrayList<TitlePart> children = new ArrayList<TitlePart>();
		TitlePart titlePart;
		switch((int) parentId) {
		case 1:
			titlePart = new TitlePart();
			titlePart.setId(2);
			titlePart.setName("No");
			titlePart.setParentId(1);
			titlePart.setVersion(1);
			children.add(titlePart);
			break;
		case 2:
			titlePart = new TitlePart();
			titlePart.setId(3);
			titlePart.setName("1");
			titlePart.setParentId(2);
			titlePart.setVersion(1);
			children.add(titlePart);
			titlePart = new TitlePart();
			titlePart.setId(4);
			titlePart.setName("2");
			titlePart.setParentId(2);
			titlePart.setVersion(1);
			children.add(titlePart);
			break;
		}
		TitlePart[] tpa = new TitlePart[children.size()];
		children.toArray(tpa);
		return tpa;
	}
	
}
