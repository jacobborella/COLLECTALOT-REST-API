package org.collectalot.dao;

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
	public TitlePart getChildren(long parentId) {
		TitlePart titlePart = new TitlePart();
		switch((int) parentId) {
		case 1:
			titlePart.setId(2);
			titlePart.setName("No");
			titlePart.setParentId(1);
			titlePart.setVersion(1);
		}
		return titlePart;
	}
	
}
