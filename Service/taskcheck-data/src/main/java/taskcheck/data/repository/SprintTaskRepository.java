/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import taskcheck.data.entity.SprintTask;
import taskcheck.data.enums.IssueStatus;
import taskcheck.data.enums.TaskStatus;

/**
 * The Interface SprintTaskRepository.
 */
@Repository
public interface SprintTaskRepository extends JpaRepository<SprintTask, Long> {

	/**
	 * Gets the all.
	 *
	 * @param sprintId the sprint id
	 * @return the all
	 */
	@Query(value = "SELECT r FROM SprintTask r WHERE r.sprint.id=?1 AND r.voided = 0")
	List<SprintTask> getAllBySprint(Long sprintId);

	/**
	 * Gets the sprint task by sprint and task.
	 *
	 * @param sprintId the sprint id
	 * @param taskId the task id
	 * @return the sprint task by sprint and task
	 */
	@Query(value = "SELECT r FROM SprintTask r WHERE r.sprint.id=?1 AND r.task.id=?2 AND r.voided = 0")
	SprintTask getSprintTaskBySprintAndTask(Long sprintId, Long taskId);
	
	/**
	 * Gets the total story point.
	 *
	 * @param sprintId the sprint id
	 * @return the total story point
	 */
	@Query(value = "SELECT SUM(r.task.storyPoint) FROM SprintTask r WHERE r.sprint.id=?1 AND r.voided = 0")
	Integer getTotalStoryPoint(Long sprintId);
	
	/**
	 * Gets the total story point by status.
	 *
	 * @param sprintId the sprint id
	 * @param status the status
	 * @return the total story point by status
	 */
	@Query(value = "SELECT SUM(r.task.storyPoint) FROM SprintTask r WHERE r.sprint.id=?1 AND r.task.status=?2 AND r.voided = 0")
	Integer getTotalStoryPointByStatus(Long sprintId, TaskStatus status);
	
	/**
	 * Gets the pending by sprint.
	 *
	 * @param sprintId the sprint id
	 * @return the pending by sprint
	 */
	@Query(value = "SELECT r FROM SprintTask r INNER JOIN r.task t WHERE r.sprint.id=?1 AND (t.status = 0 OR t.status = 1) AND r.voided = 0")
	List<SprintTask> getPendingBySprint(Long sprintId);
	
	@Query(value = "SELECT COUNT(r.task.id) FROM SprintTask r WHERE r.sprint.id=?1 AND r.task.status=2 AND r.voided = 0")
	Integer getSprintCompletedTasksCount(Long sprintId);
	
	@Query(value = "SELECT COUNT(r.task.id) FROM SprintTask r WHERE r.sprint.id=?1 AND r.task.status=1 AND r.voided = 0")
	Integer getSprintInProgressTasks(Long sprintId);
	
	@Query(value = "SELECT COUNT(r.task.id) FROM SprintTask r WHERE r.sprint.id=?1 AND r.task.status=0 AND r.voided = 0")
	Integer getSprintOpenTasks(Long sprintId);
	
	
	
}