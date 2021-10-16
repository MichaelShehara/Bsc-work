/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import taskcheck.app.dto.AuthUser;
import taskcheck.app.service.SprintLogService;
import taskcheck.data.entity.SprintLog;
import taskcheck.data.enums.SprintLogAction;
import taskcheck.data.repository.SprintLogRepository;

/**
 * The Class SprintLogServiceImpl.
 */
@Service
public class SprintLogServiceImpl implements SprintLogService {

	/** The sprint log repository. */
	@Autowired
	private SprintLogRepository sprintLogRepository;

	/**
	 * Creates the.
	 *
	 * @param sprintLog the sprint log
	 * @return the sprint log
	 */
	public SprintLog create(SprintLog sprintLog) {
		Date now = new Date();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		AuthUser user = (AuthUser) auth.getPrincipal();
		sprintLog.setCreatedBy(user.getUser());
		sprintLog.setCreatedAt(now);
		sprintLog.setUpdatedBy(user.getUser());
		sprintLog.setUpdatedAt(now);

		return sprintLogRepository.save(sprintLog);

	}

	/**
	 * Gets the by id.
	 *
	 * @param id the id
	 * @return the by id
	 */
	public SprintLog getById(Long id) {
		return sprintLogRepository.getById(id);
	}

	/**
	 * Gets the all.
	 *
	 * @param id the id
	 * @return the all
	 */
	public List<SprintLog> getAllBySprint(Long id) {
		return sprintLogRepository.getAllBySprint(id);
	}

	/**
	 * Gets the last log by sprint id.
	 *
	 * @param id the id
	 * @return the last log by sprint id
	 */
	public SprintLog getLastLogBySprintId(Long id) {
		List<SprintLog> list = sprintLogRepository.getLastLogBySprintId(id, PageRequest.of(0, 1));
		return list.isEmpty() ? null : list.get(0);
	}

	/**
	 * Gets the sprin log by project id and action.
	 *
	 * @param id     the id
	 * @param action the action
	 * @return the sprin log by project id and action
	 */
	public List<SprintLog> getSprinLogByProjectIdAndAction(Long id, SprintLogAction action) {
		return sprintLogRepository.getSprinLogByProjectIdAndAction(id, action, PageRequest.of(0, 10));
	}

}
