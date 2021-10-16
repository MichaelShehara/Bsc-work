/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import taskcheck.data.entity.PageView;
import taskcheck.data.entity.Project;

/**
 * The Interface ProjectService.
 */
public interface ProjectService {

	/**
	 * Creates the.
	 *
	 * @param project the project
	 * @return the project
	 */
	Project create(Project project);

	/**
	 * Update.
	 *
	 * @param project the project
	 * @return the project
	 */
	Project update(Project project);

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
	Project getById(Long id);

	/**
	 * Gets the by id with pagination.
	 *
	 * @param id   the id
	 * @param page the page
	 * @return the by id
	 */
	PageView getById(Long id, Pageable page);

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	List<Project> getAll();

	/**
	 * Gets the all.
	 *
	 * @param page the page
	 * @return the all
	 */
	PageView getAll(Pageable page);


}
