/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import taskcheck.app.config.PageConverter;
import taskcheck.app.service.EpicService;
import taskcheck.data.entity.Epic;
import taskcheck.data.entity.IdentifierSequence;
import taskcheck.data.entity.PageView;
import taskcheck.data.entity.Project;
import taskcheck.data.entity.Story;
import taskcheck.data.entity.User;
import taskcheck.data.repository.EpicRepository;
import taskcheck.data.repository.IdentifierSequenceRepository;
import taskcheck.data.repository.UserRepository;

/**
 * The Class EpicServiceImpl.
 */
@Service
@Transactional
public class EpicServiceImpl implements EpicService {

	/** The page converter. */
	@Autowired
	private PageConverter pageConverter;

	/** The epic repository. */
	@Autowired
	private EpicRepository epicRepository;

	/** The user repository. */
	@Autowired
	private UserRepository userRepository;

	/** The sequence repository. */
	@Autowired
	private IdentifierSequenceRepository sequenceRepository;

	/**
	 * Creates the.
	 *
	 * @param epic the epic
	 * @return the project
	 */
	@Override
	public Epic create(Epic epic) {

		Project project = epic.getProject();
		IdentifierSequence sequence = sequenceRepository.getByProjectId(project.getId());
		Long number = sequence.getSequenceNumber();

		long next = number + 1;

		String identifier = project.getCode() + "-" + next;
		epic.setIdentifier(identifier);

		sequence.setSequenceNumber(next);
		sequenceRepository.save(sequence);

		return epicRepository.save(epic);
	}

	/**
	 * Update.
	 *
	 * @param epic the epic
	 * @return the project
	 */
	@Override
	public Epic update(Epic epic) {

		// Epic entity = epicRepository.getById(epic.getId());

		if (epic.getAssignee() != null) {
			User assignee = userRepository.getById(epic.getAssignee().getId());
			epic.setAssignee(assignee);
		}

		// ObjectMapper.mapEpic(epic, entity);

		return epicRepository.save(epic);
	}

	/**
	 * Delete.
	 *
	 * @param id the id
	 */
	@Override
	public void delete(Long id) {
		Epic obj = epicRepository.getById(id);
		obj.setVoided(Boolean.TRUE);
		epicRepository.save(obj);
	}

	/**
	 * Gets the by id.
	 *
	 * @param id the id
	 * @return the by id
	 */
	@Override
	public Epic getById(Long id) {
		return epicRepository.getById(id);
	}

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	@Override
	public List<Epic> getAll() {
		return epicRepository.getAll();
	}

	/**
	 * Gets the by id to story.
	 *
	 * @param id       the id
	 * @param pageable the pageable
	 * @return the by id to story
	 */
	@Override
	public Page<Story> getByIdToStory(Long id, Pageable pageable) {
		return epicRepository.getStoryByEpicid(id, pageable);
	}

	/**
	 * Gets the by id with pagination.
	 *
	 * @param id   the id
	 * @param page the page
	 * @return the by id
	 */
	@Override
	public PageView getEpicByProjectId(Long id, Pageable page) {
		return pageConverter.handlePageResponse(epicRepository.getEpicByProjectId(id, page));
	}
}
