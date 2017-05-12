package org.collectalot.dao;

import org.collectalot.model.TitlePart;

public class TitlePartDAOMock implements TitlePartInterface {
	/* (non-Javadoc)
	 * @see org.collectalot.dao.TitlePartInterface#getTitlePart(long)
	 */
	@Override
	public TitlePart getTitlePart(long id) {
		TitlePart titlePart = new TitlePart();
		titlePart.setId(id);
		titlePart.setName("Walt Disney's Comics & Stories (mock)");
		return titlePart;
	}

	@Override
	public TitlePart getChildren(long parentId) {
		TitlePart titlePart = new TitlePart();
		switch((int) parentId) {
		case 1:
			titlePart.setId(2);
			titlePart.setName("No (Mock)");
			titlePart.setParentId(1);
			titlePart.setVersion(1);
			break;
		case 2:
			titlePart.setId(3);
			titlePart.setName("1 (Mock)");
			titlePart.setParentId(2);
			titlePart.setVersion(1);
			break;
		}
		return titlePart;
	}
}
