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
import taskcheck.app.service.StoryService;
import taskcheck.data.entity.IdentifierSequence;
import taskcheck.data.entity.PageView;
import taskcheck.data.entity.Project;
import taskcheck.data.entity.Story;
import taskcheck.data.entity.User;
import taskcheck.data.repository.IdentifierSequenceRepository;
import taskcheck.data.repository.StoryRepository;
import taskcheck.data.repository.UserRepository;

/**
 * The Class StoryServiceImpl.
 */
@Service
@Transactional
public class StoryServiceImpl implements StoryService {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(StoryServiceImpl.class);

	/** The project repository. */
	@Autowired
	private StoryRepository storyRepository;

	/** The user repository. */
	@Autowired
	private UserRepository userRepository;

	/** The sequence repository. */
	@Autowired
	private IdentifierSequenceRepository sequenceRepository;

	/** The page converter. */
	@Autowired
	private PageConverter pageConverter;

	/**
	 * Creates the.
	 *
	 * @param story the story
	 * @return the Story
	 */
	public Story create(Story story) {

		Project project = story.getEpic().getProject();
		IdentifierSequence sequence = sequenceRepository.getByProjectId(project.getId());
		Long number = sequence.getSequenceNumber();

		long next = number + 1;

		String identifier = project.getCode() + "-" + next;
		story.setIdentifier(identifier);

		sequence.setSequenceNumber(next);
		sequenceRepository.save(sequence);

		return storyRepository.save(story);
	}

	/**
	 * Update.
	 *
	 * @param story the story
	 * @return the story
	 */
	public Story update(Story story) {

		LOGGER.debug("Saving story {}", story);

		// Story entity = storyRepository.getById(story.getId());

		if (story.getAssignee() != null) {
			User assignee = userRepository.getById(story.getAssignee().getId());
			story.setAssignee(assignee);
		}

		// ObjectMapper.mapStory(story, entity);

		return storyRepository.save(story);

	}

	/**
	 * Delete.
	 *
	 * @param id the id
	 */
	@Override
	public void delete(Long id) {
		Story obj = storyRepository.getById(id);
		obj.setVoided(Boolean.TRUE);
		storyRepository.save(obj);
	}

	/**
	 * Gets the by id with pagination.
	 *
	 * @param id   the id
	 * @param page the page
	 * @return the by id
	 */
	@Override
	public PageView getStoriesInProject(Long id, Pageable page) {
		return pageConverter.handlePageResponse(storyRepository.getStoriesInProject(id, page));
	}

	/**
	 * Gets the by id.
	 *
	 * @param id the id
	 * @return the by id
	 */
	@Override
	public List<Story> getAllStoriesInProject(Long id) {
		return storyRepository.getAllStoriesInProject(id);
	}

	/**
	 * Gets the by id.
	 *
	 * @param id the id
	 * @return the by id
	 */
	@Override
	public Story getById(Long id) {
		return storyRepository.getById(id);
	}

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	@Override
	public List<Story> getAll() {
		return storyRepository.getAll();
	}

}
