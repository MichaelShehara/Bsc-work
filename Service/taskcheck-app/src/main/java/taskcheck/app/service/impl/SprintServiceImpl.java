/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import taskcheck.app.config.PageConverter;
import taskcheck.app.service.SprintService;
import taskcheck.data.entity.PageView;
import taskcheck.data.entity.Sprint;
import taskcheck.data.repository.SprintRepository;
import taskcheck.data.repository.SprintRepository.SprintDay;

/**
 * The Class SprintServiceImpl.
 */
@Service
@Transactional
public class SprintServiceImpl implements SprintService {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(SprintServiceImpl.class);

	/** The page converter. */
	@Autowired
	private PageConverter pageConverter;

	/** The sprint repository. */
	@Autowired
	private SprintRepository sprintRepository;

	/**
	 * Creates the.
	 *
	 * @param sprint the sprint
	 * @return the sprint
	 */
	@Override
	public Sprint create(Sprint sprint) {

		return sprintRepository.save(sprint);
	}

	/**
	 * Update.
	 *
	 * @param sprint the sprint
	 * @return the sprint
	 */
	@Override
	public Sprint update(Sprint sprint) {

		LOGGER.debug("Saving sprint {}", sprint);

		// Sprint entity = sprintRepository.getById(sprint.getId());

		// ObjectMapper.mapSprint(sprint, entity);

		return sprintRepository.save(sprint);
	}

	/**
	 * Delete.
	 *
	 * @param id the id
	 */
	@Override
	public void delete(Long id) {
		Sprint obj = sprintRepository.getById(id);
		obj.setVoided(Boolean.TRUE);
		sprintRepository.save(obj);
	}

	/**
	 * Gets the by id.
	 *
	 * @param id the id
	 * @return the by id
	 */
	@Override
	public Sprint getById(Long id) {
		return sprintRepository.getById(id);
	}

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	@Override
	public List<Sprint> getAll() {
		return sprintRepository.getAll();
	}

	/**
	 * Gets the by id with pagination.
	 *
	 * @param id   the id
	 * @param page the page
	 * @return the by id
	 */
	@Override
	public PageView getSprintByProjectId(Long id, Pageable page) {
		return pageConverter.handlePageResponse(sprintRepository.getSprintByProjectId(id, page));
	}

	/**
	 * Gets the active sprints.
	 *
	 * @param projectId the project id
	 * @return the active sprints
	 */
	public List<Sprint> getActiveSprints(Long projectId) {
		return sprintRepository.getActiveSprints(projectId);
	}

	/**
	 * Gets the sprint length.
	 *
	 * @param sprintId the sprint id
	 * @return the sprint length
	 */
	public int getSprintLength(Long sprintId) {
		return sprintRepository.getSprintLength(sprintId);
	}

	/**
	 * Gets the sprint dates.
	 *
	 * @param projectId   the project id
	 * @param sprintStart the sprint start
	 * @param sprintEnd   the sprint end
	 * @return the sprint dates
	 */
	public List<SprintDay> getSprintDates(Long projectId, Date sprintStart, Date sprintEnd) {
		return sprintRepository.getSprintDates(projectId, sprintStart, sprintEnd);
	}
}
