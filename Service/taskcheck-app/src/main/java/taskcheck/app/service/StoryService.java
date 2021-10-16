/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import taskcheck.data.entity.PageView;
import taskcheck.data.entity.Story;

/**
 * The Interface StoryService.
 */
public interface StoryService {

	/**
	 * Creates the.
	 *
	 * @param story the story
	 * @return the story
	 */
	Story create(Story story);

	/**
	 * Update.
	 *
	 * @param story the story
	 * @return the story
	 */
	Story update(Story story);

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
	Story getById(Long id);

	/**
	 * Gets the by id.
	 *
	 * @param id the id
	 * @return the by id
	 */
	List<Story> getAllStoriesInProject(Long id);

	/**
	 * Gets the by id with pagination.
	 *
	 * @param id   the id
	 * @param page the page
	 * @return the by id
	 */
	PageView getStoriesInProject(Long id, Pageable page);

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	List<Story> getAll();

}
