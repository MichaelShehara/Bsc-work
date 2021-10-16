/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.service;

import java.util.List;

import taskcheck.data.entity.SprintIssue;
import taskcheck.data.enums.IssueStatus;

/**
 * The Interface SprintIssueService.
 */
public interface SprintIssueService {
	
	/**
	 * Creates the.
	 *
	 * @param sprintIssue the sprint issue
	 * @return the sprint issue
	 */
	SprintIssue create(SprintIssue sprintIssue);

	/**
	 * Delete.
	 *
	 * @param sprintId the sprint id
	 * @param issueId the issue id
	 */
	void delete(Long sprintId, Long issueId);

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
	Integer getTotalStoryPointByStatus(Long sprintId, IssueStatus status);

	/**
	 * Gets the pending by sprint.
	 *
	 * @param sprintId the sprint id
	 * @return the pending by sprint
	 */
	List<SprintIssue> getPendingBySprint(Long sprintId);
	
	
	Integer getSprintIssueByStatus(Long sprintId, IssueStatus status);
	
	

}
