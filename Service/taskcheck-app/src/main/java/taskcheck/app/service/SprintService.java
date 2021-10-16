/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;

import taskcheck.data.entity.PageView;
import taskcheck.data.entity.Sprint;
import taskcheck.data.repository.SprintRepository.SprintDay;

/**
 * The Interface SprintService.
 */
public interface SprintService {

	/**
	 * Creates the.
	 *
	 * @param sprint the sprint
	 * @return the sprint
	 */
	Sprint create(Sprint sprint);

	/**
	 * Update.
	 *
	 * @param sprint the sprint
	 * @return the sprint
	 */
	Sprint update(Sprint sprint);

	/**
	 * Delete.
	 *
	 * @param id the id
	 */
	public void delete(Long id);

	/**
	 * Gets the by id.
	 *
	 * @param id the id
	 * @return the by id
	 */
	public Sprint getById(Long id);

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	List<Sprint> getAll();

	/**
	 * Gets the by id with pagination.
	 *
	 * @param id   the id
	 * @param page the page
	 * @return the by id
	 */
	PageView getSprintByProjectId(Long id, Pageable page);

	/**
	 * Gets the active sprints.
	 *
	 * @param projectId the project id
	 * @return the active sprints
	 */
	List<Sprint> getActiveSprints(Long projectId);

	/**
	 * Gets the sprint length.
	 *
	 * @param sprintId the sprint id
	 * @return the sprint length
	 */
	int getSprintLength(Long sprintId);

	/**
	 * Gets the sprint dates.
	 *
	 * @param projectId the project id
	 * @param sprintStart the sprint start
	 * @param sprintEnd the sprint end
	 * @return the sprint dates
	 */
	List<SprintDay> getSprintDates(Long projectId, Date sprintStart, Date sprintEnd);

}
