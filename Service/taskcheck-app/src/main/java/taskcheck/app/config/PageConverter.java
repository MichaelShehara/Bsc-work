/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.config;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import taskcheck.data.entity.PageView;


/**
 * The Class PageConverter.
 */
@Component
public class PageConverter {

	/**
	 * Handle page response.
	 *
	 * @param page the page
	 * @return the page view
	 */
	public PageView handlePageResponse(Page<?> page) {
		PageView pageView = new PageView();
		pageView.setContent(page.getContent());
		pageView.setFirst(page.isFirst());
		pageView.setLast(page.isLast());
		pageView.setNumber(page.getNumber());
		pageView.setNumOfElements(page.getNumberOfElements());
		pageView.setSize(page.getSize());
		pageView.setSort(page.getSort());
		pageView.setPageable(page.getPageable());
		pageView.setTotalPages(page.getTotalPages());
		
		return pageView;
	}
	
}
