/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import taskcheck.data.entity.PageView;
import taskcheck.data.entity.Task;

/**
 * The Interface TaskService.
 */
@Service
@Transactional
public interface TaskService {

	/**
	 * Creates the.
	 *
	 * @param task the task
	 * @return the task
	 */
	Task create(Task task);

	/**
	 * Update.
	 *
	 * @param task the task
	 * @return the task
	 */
	Task update(Task task);

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
	public Task getById(Long id);

	/**
	 * Gets the by id.
	 *
	 * @param id the id
	 * @return the by id
	 */
	List<Task> getAllTasksByProject(Long id);

	/**
	 * Gets the by id with pagination.
	 *
	 * @param id   the id
	 * @param page the page
	 * @return the by id
	 */
	PageView getTasksByProject(Long id, Pageable page);

	/**
	 * Gets the backlog tasks by project.
	 *
	 * @param projectId the project id
	 * @return the backlog tasks by project
	 */
	List<Task> getBacklogTasksByProject(Long projectId);

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	List<Task> getAll();

}
