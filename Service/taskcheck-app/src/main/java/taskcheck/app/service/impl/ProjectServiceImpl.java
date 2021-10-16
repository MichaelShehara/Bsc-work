/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import taskcheck.app.config.PageConverter;
import taskcheck.app.service.ProjectService;
import taskcheck.data.entity.IdentifierSequence;
import taskcheck.data.entity.PageView;
import taskcheck.data.entity.Project;
import taskcheck.data.repository.IdentifierSequenceRepository;
import taskcheck.data.repository.ProjectRepository;

/**
 * The Class ProjectServiceImpl.
 */
@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

	/** The page converter. */
	@Autowired
	private PageConverter pageConverter;

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ProjectServiceImpl.class);

	/** The project repository. */
	@Autowired
	private ProjectRepository projectRepository;

	/** The identifier sequence repository. */
	@Autowired
	private IdentifierSequenceRepository identifierSequenceRepository;

	/**
	 * Creates the.
	 *
	 * @param project the project
	 * @return the project
	 */
	@Override
	public Project create(Project project) {

		Project obj = projectRepository.save(project);

		IdentifierSequence sequence = new IdentifierSequence();
		sequence.setProject(obj);
		sequence.setSequenceNumber(0L);
		identifierSequenceRepository.save(sequence);

		return obj;
	}

	/**
	 * Update.
	 *
	 * @param project the project
	 * @return the project
	 */
	@Override
	public Project update(Project project) {

		LOGGER.debug("Saving project {}", project);

		// Project entity = projectRepository.getById(project.getId());

		// ObjectMapper.mapProject(project, entity);

		return projectRepository.save(project);
	}

	/**
	 * Delete.
	 *
	 * @param id the id
	 */
	@Override
	public void delete(Long id) {
		Project obj = projectRepository.getById(id);
		obj.setVoided(Boolean.TRUE);
		projectRepository.save(obj);
	}

	/**
	 * Gets the by id.
	 *
	 * @param id the id
	 * @return the by id
	 */
	@Override
	public Project getById(Long id) {
		return projectRepository.getById(id);
	}

	/**
	 * Gets the by id with pagination.
	 *
	 * @param id   the id
	 * @param page the page
	 * @return the by id
	 */
	@Override
	public PageView getById(Long id, Pageable page) {
		return pageConverter.handlePageResponse(projectRepository.getById(id, page));
	}

	/**
	 * Gets the all.
	 *
	 * @param page the page
	 * @return the all
	 */
	@Override
	public PageView getAll(Pageable page) {
		return pageConverter.handlePageResponse(projectRepository.getAll(page));
	}

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	@Override
	public List<Project> getAll() {
		return projectRepository.getAll();
	}

}
