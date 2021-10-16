/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import taskcheck.app.service.StoryService;
import taskcheck.app.service.TaskService;
import taskcheck.data.entity.Story;
import taskcheck.data.entity.Task;
import taskcheck.data.view.Views;

/**
 * The Class StoryController.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/stories")
public class StoryController {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(StoryController.class);

	/** The story service. */
	@Autowired
	private StoryService storyService;

	/** The task service. */
	@Autowired
	private TaskService taskService;

	/**
	 * Creates the task.
	 *
	 * @param storyId the story id
	 * @param task    the task
	 * @return the response entity
	 */
	@PostMapping(value = "/{storyId}/tasks", headers = Constants.ApiVersion.V1)
	@JsonView(Views.Task.class)
	public ResponseEntity<Task> createTask(@PathVariable("storyId") Long storyId, @Valid @RequestBody Task task) {

		LOGGER.debug("Create task {}", task);

		Story story = storyService.getById(storyId);
		task.setStory(story);

		Task obj = taskService.create(task);

		return new ResponseEntity<Task>(obj, HttpStatus.OK);
	}

	/**
	 * Update task.
	 *
	 * @param storyId the story id
	 * @param task    the task
	 * @return the response entity
	 */
	@PutMapping(value = "/{storyId}/tasks", headers = Constants.ApiVersion.V1)
	@JsonView(Views.Task.class)
	public ResponseEntity<Task> updateTask(@PathVariable("storyId") Long storyId, @Valid @RequestBody Task task) {

		LOGGER.debug("Update task {}", task);

		Story story = storyService.getById(storyId);
		task.setStory(story);

		Task obj = taskService.update(task);

		return new ResponseEntity<Task>(obj, HttpStatus.OK);
	}

	/**
	 * Gets the task.
	 *
	 * @param storyId the story id
	 * @param id      the id
	 * @return the task
	 */
	@GetMapping(value = "/{storyId}/tasks/{id}", headers = Constants.ApiVersion.V1)
	@JsonView(Views.StoryTaskDetails.class)
	public ResponseEntity<Task> getTask(@PathVariable("storyId") Long storyId, @PathVariable("id") Long id) {

		LOGGER.debug("Get task {}", id);

		Task obj = taskService.getById(id);

		return new ResponseEntity<Task>(obj, HttpStatus.OK);
	}

	/**
	 * Gets one.
	 *
	 * @param id the id
	 * @return the by id
	 */
	@GetMapping(value = "/{id}", headers = Constants.ApiVersion.V1)
	@JsonView(Views.Story.class)
	public ResponseEntity<Story> getStory(@PathVariable("id") Long id) {

		LOGGER.debug("Get one story {}", id);

		Story obj = storyService.getById(id);

		return new ResponseEntity<Story>(obj, HttpStatus.OK);
	}
	
	/**
	 * Delete task.
	 *
	 * @param storyId the story id
	 * @param id      the id
	 * @return the response entity
	 */
	@DeleteMapping(value = "/{storyId}/tasks/{id}", headers = Constants.ApiVersion.V1)
	public ResponseEntity<Void> deleteTask(@PathVariable("storyId") Long storyId,
			@PathVariable("id") @RequestParam Long id) {

		LOGGER.debug("Delete task {}", id);
		taskService.delete(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
