/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.service;

import java.util.List;

import taskcheck.data.entity.SprintTask;
import taskcheck.data.enums.IssueStatus;
import taskcheck.data.enums.TaskStatus;

/**
 * The Interface SprintTaskService.
 */
public interface SprintTaskService {

	/**
	 * Creates the.
	 *
	 * @param sprintTask the sprint task
	 * @return the sprint task
	 */
	SprintTask create(SprintTask sprintTask);

	/**
	 * Delete.
	 *
	 * @param sprintId the sprint id
	 * @param taskId the task id
	 */
	void delete(Long sprintId, Long taskId);

	/**
	 * Gets the total story point.
	 *
	 * @param sprintId the sprint id
	 * @return the total story point
	 */
	Integer getTotalStoryPoint(Long sprintId);
	
	/**
	 * Gets the total story point by status.
	 *
	 * @param sprintId the sprint id
	 * @param status the status
	 * @return the total story point by status
	 */
	Integer getTotalStoryPointByStatus(Long sprintId, TaskStatus status);
	
	/**
	 * Gets the pending by sprint.
	 *
	 * @param sprintId the sprint id
	 * @return the pending by sprint
	 */
	List<SprintTask> getPendingBySprint(Long sprintId);
	
	Integer getSprintCompletedTasks(Long sprintId);
	
	Integer getSprintInProgressTasks(Long sprintId);
	
	Integer getSprintOpenTasks(Long sprintId);
	
	
	
	List <SprintTask> getAllBySprint(Long sprintId);
	
}
