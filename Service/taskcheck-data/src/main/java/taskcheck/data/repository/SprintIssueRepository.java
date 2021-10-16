/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import taskcheck.data.entity.SprintIssue;
import taskcheck.data.enums.IssueStatus;

/**
 * The Interface SprintIssueRepository.
 */
@Repository
public interface SprintIssueRepository extends JpaRepository<SprintIssue, Long> {

	/**
	 * Gets the all.
	 *
	 * @param sprintId the sprint id
	 * @return the all
	 */
	@Query(value = "SELECT r FROM SprintIssue r WHERE r.sprint.id=?1 AND r.voided = 0")
	List<SprintIssue> getAllBySprint(Long sprintId);

	/**
	 * Gets the sprint issue by sprint and issue.
	 *
	 * @param sprintId the sprint id
	 * @param issueId the issue id
	 * @return the sprint issue by sprint and issue
	 */
	@Query(value = "SELECT r FROM SprintIssue r WHERE r.sprint.id=?1 AND r.issue.id=?2 AND r.voided = 0")
	SprintIssue getSprintIssueBySprintAndIssue(Long sprintId, Long issueId);
	
	/**
	 * Gets the total story point.
	 *
	 * @param sprintId the sprint id
	 * @return the total story point
	 */
	@Query(value = "SELECT SUM(r.issue.storyPoint) FROM SprintIssue r WHERE r.sprint.id=?1 AND r.voided = 0")
	Integer getTotalStoryPoint(Long sprintId);
	
	/**
	 * Gets the total story point by status.
	 *
	 * @param sprintId the sprint id
	 * @param status the status
	 * @return the total story point by status
	 */
	@Query(value = "SELECT SUM(r.issue.storyPoint) FROM SprintIssue r WHERE r.sprint.id=?1 AND  r.issue.status=?2 AND r.voided = 0")
	Integer getTotalStoryPointByStatus(Long sprintId, IssueStatus status);
	
	/**
	 * Gets the pending by sprint.
	 *
	 * @param sprintId the sprint id
	 * @return the pending by sprint
	 */
	@Query(value = "SELECT r FROM SprintIssue r INNER JOIN r.issue i WHERE r.sprint.id=?1 AND (i.status = 0 OR i.status = 1 OR i.status = 2) AND r.voided = 0")
	List<SprintIssue> getPendingBySprint(Long sprintId);
	
	@Query(value = "SELECT COUNT(r.issue.id) FROM SprintIssue r WHERE r.sprint.id=?1 AND r.issue.status=?2 AND r.voided = 0")
	Integer getIssuesByStatus(Long sprintId, IssueStatus status);
}