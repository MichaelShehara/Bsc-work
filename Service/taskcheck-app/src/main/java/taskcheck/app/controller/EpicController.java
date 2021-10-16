/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import taskcheck.app.config.Constants;
import taskcheck.app.service.EpicService;
import taskcheck.app.service.StoryService;
import taskcheck.app.service.UserService;
import taskcheck.data.entity.Epic;
import taskcheck.data.entity.Story;
import taskcheck.data.entity.User;
import taskcheck.data.view.Views;

/**
 * The Class EpicController.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/epics")
public class EpicController {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(EpicController.class);

	/** The Epic service. */
	@Autowired
	private EpicService epicService;

	/** The user service. */
	@Autowired
	private UserService userService;

	/** The story service. */
	@Autowired
	private StoryService storyService;

	/**
	 * Change assignee.
	 *
	 * @param epicId the epic id
	 * @param userId the user id
	 * @return the response entity
	 */
	@PutMapping(value = "/{epicId}/assignee/{userId}", headers = Constants.ApiVersion.V1)
	public ResponseEntity<Epic> changeAssignee(@PathVariable("epicId") Long epicId,
			@PathVariable("userId") Long userId) {

		LOGGER.debug("Change Assignee {}", epicId);

		Epic epic = epicService.getById(epicId);

		User user = userService.getById(userId);

		epic.setAssignee(user);

		epic = epicService.update(epic);

		return new ResponseEntity<Epic>(epic, HttpStatus.OK);
	}

	/**
	 * Creates the story.
	 *
	 * @param epicId the epic id
	 * @param story  the story
	 * @return the response entity
	 */
	@PostMapping(value = "/{epicId}/stories", headers = Constants.ApiVersion.V1)
	@JsonView(Views.Story.class)
	public ResponseEntity<Story> createStory(@PathVariable("epicId") Long epicId, @Valid @RequestBody Story story) {

		LOGGER.debug("Create Story {}", story);

		Epic epic = epicService.getById(epicId);
		story.setEpic(epic);

		Story obj = storyService.create(story);

		return new ResponseEntity<Story>(obj, HttpStatus.OK);
	}

	/**
	 * Gets the story.
	 *
	 * @param epicId the epic id
	 * @param id     the id
	 * @return the story
	 */
	@GetMapping(value = "/{epicId}/stories/{id}", headers = Constants.ApiVersion.V1)
	@JsonView(Views.Story.class)
	public ResponseEntity<Story> getStory(@PathVariable("epicId") Long epicId, @PathVariable("id") Long id) {

		LOGGER.debug("Get story {}", id);

		Story obj = storyService.getById(id);

		return new ResponseEntity<Story>(obj, HttpStatus.OK);
	}

	/**
	 * Gets the all.
	 *
	 * @param epicId   the epic id
	 * @param pageNo   the page no
	 * @param pageSize the page size
	 * @return the all
	 */
	@GetMapping(value = "/{epicId}/stories", headers = Constants.ApiVersion.V1)
	public ResponseEntity<Page<Story>> getAll(@PathVariable("epicId") Long epicId, @RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize) {

		LOGGER.debug("Get All stories {}", epicId, pageNo, pageSize);

		Pageable paging = PageRequest.of(pageNo, pageSize);

		Page<Story> obj = epicService.getByIdToStory(epicId, paging);

		return new ResponseEntity<Page<Story>>(obj, HttpStatus.OK);
	}

	/**
	 * Update story.
	 *
	 * @param epicId the epic id
	 * @param story  the story
	 * @return the response entity
	 */
	@PutMapping(value = "/{epicId}/stories", headers = Constants.ApiVersion.V1)
	@JsonView(Views.Story.class)
	public ResponseEntity<Story> updateStory(@PathVariable("epicId") Long epicId, @Valid @RequestBody Story story) {

		LOGGER.debug("Update story {}", story);

		story = storyService.update(story);

		return new ResponseEntity<Story>(story, HttpStatus.OK);

	}

}
