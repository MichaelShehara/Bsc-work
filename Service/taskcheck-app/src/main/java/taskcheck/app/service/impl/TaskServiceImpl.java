/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import taskcheck.app.config.PageConverter;
import taskcheck.app.service.TaskService;
import taskcheck.data.entity.IdentifierSequence;
import taskcheck.data.entity.PageView;
import taskcheck.data.entity.Project;
import taskcheck.data.entity.Task;
import taskcheck.data.entity.User;
import taskcheck.data.repository.IdentifierSequenceRepository;
import taskcheck.data.repository.TaskRepository;
import taskcheck.data.repository.UserRepository;

/**
 * The Class TaskServiceImpl.
 */
@Service
@Transactional
public class TaskServiceImpl implements TaskService {

	/** The page converter. */
	@Autowired
	private PageConverter pageConverter;

	/** The task repository. */
	@Autowired
	private TaskRepository taskRepository;

	/** The user repository. */
	@Autowired
	private UserRepository userRepository;

	/** The sequence repository. */
	@Autowired
	private IdentifierSequenceRepository sequenceRepository;

	/**
	 * Creates the.
	 *
	 * @param task the task
	 * @return the task
	 */
	@Override
	public Task create(Task task) {

		Project project = task.getStory().getEpic().getProject();
		IdentifierSequence sequence = sequenceRepository.getByProjectId(project.getId());
		Long number = sequence.getSequenceNumber();

		long next = number + 1;

		String identifier = project.getCode() + "-" + next;
		task.setIdentifier(identifier);

		sequence.setSequenceNumber(next);
		sequenceRepository.save(sequence);

		return taskRepository.save(task);
	}

	/**
	 * Update.
	 *
	 * @param task the task
	 * @return the task
	 */
	@Override
	public Task update(Task task) {

		// Task entity = taskRepository.getById(task.getId());

		if (task.getAssignee() != null) {
			User assignee = userRepository.getById(task.getAssignee().getId());
			task.setAssignee(assignee);
		}

		// ObjectMapper.mapTask(task, entity);

		return taskRepository.save(task);
	}

	/**
	 * Delete.
	 *
	 * @param id the id
	 */
	@Override
	public void delete(Long id) {
		Task obj = taskRepository.getById(id);
		obj.setVoided(Boolean.TRUE);
		taskRepository.save(obj);
	}

	/**
	 * Gets the by id.
	 *
	 * @param id the id
	 * @return the by id
	 */
	@Override
	public Task getById(Long id) {
		return taskRepository.getById(id);
	}

	/**
	 * Gets the by id with pagination.
	 *
	 * @param id   the id
	 * @param page the page
	 * @return the by id
	 */
	@Override
	public PageView getTasksByProject(Long id, Pageable page) {
		return pageConverter.handlePageResponse(taskRepository.getTasksByProject(id, page));
	}

	/**
	 * Gets the by id.
	 *
	 * @param id the id
	 * @return the by id
	 */
	@Override
	public List<Task> getAllTasksByProject(Long id) {
		return taskRepository.getAllTasksByProject(id);
	}

	/**
	 * Gets the backlog tasks by project.
	 *
	 * @param projectId the project id
	 * @return the backlog tasks by project
	 */
	public List<Task> getBacklogTasksByProject(Long projectId) {
		return taskRepository.getBacklogTasksByProject(projectId);
	}

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	@Override
	public List<Task> getAll() {
		return taskRepository.getAll();
	}
}
