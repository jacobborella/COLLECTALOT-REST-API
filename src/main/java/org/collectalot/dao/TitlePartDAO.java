package org.collectalot.dao;

import org.collectalot.model.TitlePart;

public class TitlePartDAO implements TitlePartInterface {
	/* (non-Javadoc)
	 * @see org.collectalot.dao.TitlePartInterface#getTitlePart(long)
	 */
	@Override
	public TitlePart getTitlePart(long id) {
		TitlePart titlePart = new TitlePart();
		titlePart.setId(id);
		titlePart.setName("Walt Disney's Comics & Stories");
		return titlePart;
	}
}
