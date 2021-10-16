/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import taskcheck.app.dto.AuthUser;
import taskcheck.app.service.SprintTaskService;
import taskcheck.data.entity.SprintTask;
import taskcheck.data.enums.IssueStatus;
import taskcheck.data.enums.TaskStatus;
import taskcheck.data.repository.SprintTaskRepository;

/**
 * The Class SprintTaskServiceImpl.
 */
@Service
@Transactional
public class SprintTaskServiceImpl implements SprintTaskService {

	/** The sprint task repository. */
	@Autowired
	private SprintTaskRepository sprintTaskRepository;

	/**
	 * Creates the.
	 *
	 * @param sprintTask the sprint task
	 * @return the sprint task
	 */
	@Override
	public SprintTask create(SprintTask sprintTask) {
		Date now = new Date();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		AuthUser user = (AuthUser) auth.getPrincipal();
		sprintTask.setCreatedBy(user.getUser());
		sprintTask.setCreatedAt(now);
		sprintTask.setUpdatedBy(user.getUser());
		sprintTask.setUpdatedAt(now);

		return sprintTaskRepository.save(sprintTask);
	}

	/**
	 * Delete.
	 *
	 * @param sprintId the sprint id
	 * @param taskId the task id
	 */
	@Override
	public void delete(Long sprintId, Long taskId) {
		SprintTask obj = sprintTaskRepository.getSprintTaskBySprintAndTask(sprintId, taskId);
		obj.setVoided(Boolean.TRUE);
		sprintTaskRepository.save(obj);
	}

	/**
	 * Gets the total story point.
	 *
	 * @param sprintId the sprint id
	 * @return the total story point
	 */
	public Integer getTotalStoryPoint(Long sprintId) {
		return sprintTaskRepository.getTotalStoryPoint(sprintId);
	}

	/**
	 * Gets the total story point by status.
	 *
	 * @param sprintId the sprint id
	 * @param status the status
	 * @return the total story point by status
	 */
	public Integer getTotalStoryPointByStatus(Long sprintId, TaskStatus status) {
		return sprintTaskRepository.getTotalStoryPointByStatus(sprintId, status);
	}

	/**
	 * Gets the pending by sprint.
	 *
	 * @param sprintId the sprint id
	 * @return the pending by sprint
	 */
	@Override
	public List<SprintTask> getPendingBySprint(Long sprintId) {
		return sprintTaskRepository.getPendingBySprint(sprintId);
	}
	
	@Override
	public List<SprintTask> getAllBySprint(Long sprintId) {
		return sprintTaskRepository.getAllBySprint(sprintId);
	}

	@Override
	public Integer getSprintCompletedTasks(Long sprintId) {
		return sprintTaskRepository.getSprintCompletedTasksCount(sprintId);
	}

	@Override
	public Integer getSprintInProgressTasks(Long sprintId) {
		return sprintTaskRepository.getSprintInProgressTasks(sprintId);
	}

	@Override
	public Integer getSprintOpenTasks(Long sprintId) {
		return sprintTaskRepository.getSprintOpenTasks(sprintId);
	}

}
