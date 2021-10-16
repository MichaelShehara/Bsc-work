/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import taskcheck.data.entity.Epic;
import taskcheck.data.entity.PageView;
import taskcheck.data.entity.Story;

/**
 * The Interface EpicService.
 */
@Service
@Transactional
public interface EpicService {
	/**
	 * Creates the.
	 *
	 * @param epic the epic
	 * @return the epic
	 */
	Epic create(Epic epic);

	/**
	 * Update.
	 *
	 * @param epic the epic
	 * @return the epic
	 */
	Epic update(Epic epic);

	/**
	 * Delete.
	 *
	 * @param id the id
	 */
	void delete(Long id);

	/**
	 * Gets the by id.
	 *
	 * @param id the id
	 * @return the by id
	 */
	Epic getById(Long id);
	

	/**
	 * Gets the by id to get story.
	 *
	 * @param id the id
	 * @param pageable the pageable
	 * @return the by id
	 */

	Page<Story> getByIdToStory(Long id, Pageable pageable);

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	List<Epic> getAll();
	
	/**
	 * Gets the by id with pagination.
	 *
	 * @param id the id
	 * @param page the page
	 * @return the by id
	 */
	PageView getEpicByProjectId(Long id, Pageable page);

}
