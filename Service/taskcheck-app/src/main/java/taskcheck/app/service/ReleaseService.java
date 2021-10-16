/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.service;

import java.util.List;

import taskcheck.data.entity.Release;

/**
 * The Interface ReleaseService.
 */
public interface ReleaseService {

	/**
	 * Creates the.
	 *
	 * @param release the release
	 * @return the release
	 */
	Release create(Release release);

	/**
	 * Update.
	 *
	 * @param release the release
	 * @return the release
	 */
	Release update(Release release);

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
	Release getById(Long id);

	/**
	 * Gets the by projectId.
	 *
	 * @param id the id
	 * @return the by id
	 */
	Release getReleaseByProjectId(Long id);

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	List<Release> getAll();

	/**
	 * Gets the all in project.
	 *
	 * @param projectId the project id
	 * @return the all
	 */
	List<Release> getAllReleaseListInProject(Long projectId);
}
