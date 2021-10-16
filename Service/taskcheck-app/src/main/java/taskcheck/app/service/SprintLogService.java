/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import taskcheck.data.entity.SprintLog;
import taskcheck.data.enums.SprintLogAction;

/**
 * The Interface SprintLogService.
 */
@Service
public interface SprintLogService {

	/**
	 * Creates the.
	 *
	 * @param sprintLog the sprint log
	 * @return the sprint log
	 */
	SprintLog create(SprintLog sprintLog);

	/**
	 * Gets the by id.
	 *
	 * @param id the id
	 * @return the by id
	 */
	public SprintLog getById(Long id);

	/**
	 * Gets the all.
	 *
	 * @param id the id
	 * @return the all
	 */
	List<SprintLog> getAllBySprint(Long id);

	/**
	 * Gets the last log by sprint id.
	 *
	 * @param id the id
	 * @return the last log by sprint id
	 */
	SprintLog getLastLogBySprintId(Long id);

	/**
	 * Gets the sprin log by project id and action.
	 *
	 * @param id     the id
	 * @param action the action
	 * @return the sprin log by project id and action
	 */
	List<SprintLog> getSprinLogByProjectIdAndAction(Long id, SprintLogAction action);

}
