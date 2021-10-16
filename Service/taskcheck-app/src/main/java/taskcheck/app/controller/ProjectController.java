/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.controller;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
import taskcheck.app.dto.TransitionDto;
import taskcheck.app.exception.BusinessException;
import taskcheck.app.service.EpicService;
import taskcheck.app.service.IssueService;
import taskcheck.app.service.ProjectHolidayService;
import taskcheck.app.service.ProjectService;
import taskcheck.app.service.ReleaseService;
import taskcheck.app.service.SprintIssueService;
import taskcheck.app.service.SprintLogService;
import taskcheck.app.service.SprintService;
import taskcheck.app.service.SprintTaskService;
import taskcheck.app.service.StoryService;
import taskcheck.app.service.TaskService;
import taskcheck.data.entity.Epic;
import taskcheck.data.entity.Issue;
import taskcheck.data.entity.PageView;
import taskcheck.data.entity.Project;
import taskcheck.data.entity.ProjectHoliday;
import taskcheck.data.entity.Release;
import taskcheck.data.entity.Sprint;
import taskcheck.data.entity.SprintLog;
import taskcheck.data.entity.Story;
import taskcheck.data.entity.Task;
import taskcheck.data.enums.SprintLogAction;
import taskcheck.data.enums.SprintStatus;
import taskcheck.data.repository.IssueRepository.ReleaseIssueCount;
import taskcheck.data.view.Views;
import taskcheck.data.view.Views.SprintPageble;

/**
 * The Class ProjectController.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/projects")
public class ProjectController {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);

	/** The project service. */
	@Autowired
	private ProjectService projectService;

	/** The epic service. */
	@Autowired
	private EpicService epicService;

	/** The sprint service. */
	@Autowired
	private SprintService sprintService;

	/** The sprint log service. */
	@Autowired
	private SprintLogService sprintLogService;

	/** The issue service. */
	@Autowired
	private StoryService storyService;

	/** The task service. */
	@Autowired
	private TaskService taskService;

	/** The issue service. */
	@Autowired
	private IssueService issueService;

	/** The project holiday service. */
	@Autowired
	private ProjectHolidayService projectHolidayService;

	/** The project service. */
	@Autowired
	private ReleaseService releaseService;

	/** The sprint task service. */
	@Autowired
	private SprintTaskService sprintTaskService;

	/** The sprint issue service. */
	@Autowired
	private SprintIssueService sprintIssueService;

	/**
	 * Create New Project.
	 *
	 * @param project the project
	 * @return the response entity
	 */
	@PostMapping(value = "/", headers = Constants.ApiVersion.V1)
	@JsonView(Views.User.class)
	public ResponseEntity<Project> createProject(@Valid @RequestBody Project project) {

		LOGGER.debug("Create project {}", project);

		Project obj = projectService.create(project);

		return new ResponseEntity<Project>(obj, HttpStatus.OK);
	}

	/**
	 * Updates the.
	 *
	 * @param project the project
	 * @return the response entity
	 */
	@PutMapping(value = "/", headers = Constants.ApiVersion.V1)
	@JsonView(Views.Project.class)
	public ResponseEntity<Project> updateProject(@Valid @RequestBody Project project) {

		LOGGER.debug("Update project", project);

		project = projectService.update(project);

		return new ResponseEntity<Project>(project, HttpStatus.OK);

	}

	/**
	 * Deletes the.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@DeleteMapping(value = "/{id}", headers = Constants.ApiVersion.V1)
	public ResponseEntity<Void> deleteProject(@PathVariable("id") Long id) {

		LOGGER.debug("Delete project");

		projectService.delete(id);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	/**
	 * Gets one.
	 *
	 * @param id the id
	 * @return the by id
	 */
	@GetMapping(value = "/{id}", headers = Constants.ApiVersion.V1)
	@JsonView(Views.ProjectList.class)
	public ResponseEntity<Project> getProjectById(@PathVariable("id") Long id) {

		LOGGER.debug("Get project {}", id);

		Project obj = projectService.getById(id);

		return new ResponseEntity<Project>(obj, HttpStatus.OK);

	}

	/**
	 * Gets all .
	 *
	 * @return the all
	 */
	@GetMapping(value = "/", headers = Constants.ApiVersion.V1)
	@JsonView(Views.Project.class)
	public ResponseEntity<List<Project>> getAllProjects() {

		LOGGER.debug("get all project");

		List<Project> obj = projectService.getAll();

		return new ResponseEntity<List<Project>>(obj, HttpStatus.OK);
	}

	/**
	 * Gets all with Pagination.
	 *
	 * @param pageNo   the page no
	 * @param pageSize the page size
	 * @return the all
	 */
	@GetMapping(value = "/list", headers = Constants.ApiVersion.V1)
	@JsonView(Views.ProjectPageable.class)
	public ResponseEntity<PageView> getAllProjects(@RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize) {

		LOGGER.debug("get all project");

		PageView obj = projectService.getAll(PageRequest.of(pageNo, pageSize));
		return new ResponseEntity<PageView>(obj, HttpStatus.OK);
	}

	/**
	 * Creates the epic.
	 *
	 * @param projectId the project id
	 * @param epic      the epic
	 * @return the response entity
	 */
	@PostMapping(value = "/{projectId}/epics", headers = Constants.ApiVersion.V1)
	@JsonView(Views.Epic.class)
	public ResponseEntity<Epic> createEpic(@PathVariable("projectId") Long projectId, @Valid @RequestBody Epic epic) {

		LOGGER.debug("Create epic {}", epic);

		Project project = projectService.getById(projectId);
		epic.setProject(project);

		Epic obj = epicService.create(epic);

		return new ResponseEntity<Epic>(obj, HttpStatus.OK);
	}

	/**
	 * Update epic.
	 *
	 * @param projectId the project id
	 * @param epic      the epic
	 * @return the response entity
	 */
	@PutMapping(value = "/{projectId}/epics", headers = Constants.ApiVersion.V1)
	@JsonView(Views.Epic.class)
	public ResponseEntity<Epic> updateEpic(@PathVariable("projectId") Long projectId, @Valid @RequestBody Epic epic) {

		LOGGER.debug("Update epic {}", epic);

		Project project = projectService.getById(projectId);
		epic.setProject(project);

		Epic obj = epicService.update(epic);

		return new ResponseEntity<Epic>(obj, HttpStatus.OK);
	}

	/**
	 * Delete epic.
	 *
	 * @param projectId the project id
	 * @param id        the id
	 * @return the response entity
	 */
	@DeleteMapping(value = "/{projectId}/epics/{id}", headers = Constants.ApiVersion.V1)
	public ResponseEntity<Void> deleteEpic(@PathVariable("projectId") Long projectId,
			@PathVariable("id") @RequestParam Long id) {

		LOGGER.debug("Delete epic {}", id);

		epicService.delete(id);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	/**
	 * Gets the epic.
	 *
	 * @param projectId the project id
	 * @param id        the id
	 * @return the epic
	 */
	@GetMapping(value = "/{projectId}/epics/{id}", headers = Constants.ApiVersion.V1)
	@JsonView(Views.ProjectDeatils.class)
	public ResponseEntity<Epic> getEpic(@PathVariable("projectId") Long projectId, @PathVariable("id") Long id) {

		LOGGER.debug("Get epic {}", id);

		Epic obj = epicService.getById(id);

		return new ResponseEntity<Epic>(obj, HttpStatus.OK);
	}

	/**
	 * Gets all Epics in Project(Pagination).
	 *
	 * @param projectId the project id
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @return the all
	 */
	@GetMapping(value = "/{projectId}/epics", headers = Constants.ApiVersion.V1)
	@JsonView(Views.EpicPageable.class)
	public ResponseEntity<PageView> getAllEpics(@PathVariable("projectId") Long projectId,
			@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) {

		LOGGER.debug("Get All Epics {}", projectId);

		PageView obj = epicService.getEpicByProjectId(projectId, PageRequest.of(pageNo, pageSize));

		return new ResponseEntity<PageView>(obj, HttpStatus.OK);
	}

	/**
	 * Gets all Epics in Project(Without Pagination).
	 *
	 * @param projectId the project id
	 * @return the all
	 */
	@GetMapping(value = "/{projectId}/allepics", headers = Constants.ApiVersion.V1)
	@JsonView(Views.Epic.class)
	public ResponseEntity<Set<Epic>> getAllEpicsInProjects(@PathVariable("projectId") Long projectId) {

		LOGGER.debug("Get All Epics without Pagination {}", projectId);

		Project obj = projectService.getById(projectId);

		return new ResponseEntity<Set<Epic>>(obj.getEpics(), HttpStatus.OK);
	}

	/**
	 * Creates the sprint.
	 *
	 * @param projectId the project id
	 * @param sprint    the sprint
	 * @return the response entity
	 */
	@PostMapping(value = "/{projectId}/sprints", headers = Constants.ApiVersion.V1)
	@JsonView(Views.Project.class)
	public ResponseEntity<Sprint> createSprint(@PathVariable("projectId") Long projectId,
			@Valid @RequestBody Sprint sprint) {

		LOGGER.debug("Create sprint {}", sprint);

		Project project = projectService.getById(projectId);
		sprint.setProject(project);
		Sprint obj = sprintService.create(sprint);

		return new ResponseEntity<Sprint>(obj, HttpStatus.OK);
	}

	/**
	 * Update sprint.
	 *
	 * @param projectId the project id
	 * @param sprint    the sprint
	 * @return the response entity
	 */
	@PutMapping(value = "/{projectId}/sprints", headers = Constants.ApiVersion.V1)
	@JsonView(Views.Sprint.class)
	public ResponseEntity<Sprint> updateSprint(@PathVariable("projectId") Long projectId,
			@Valid @RequestBody Sprint sprint) {

		LOGGER.debug("Update sprint {}", sprint);

		Project project = projectService.getById(projectId);
		sprint.setProject(project);
		Sprint obj = sprintService.update(sprint);

		return new ResponseEntity<Sprint>(obj, HttpStatus.OK);
	}

	/**
	 * Transition sprint.
	 *
	 * @param projectId the project id
	 * @param sprintId the sprint id
	 * @param transition the transition
	 * @return the response entity
	 */
	@PutMapping(value = "/{projectId}/sprints/{sprintId}/transition", headers = Constants.ApiVersion.V1)
	@JsonView(Views.Sprint.class)
	public ResponseEntity<Sprint> transitionSprint(@PathVariable("projectId") Long projectId,
			@PathVariable("sprintId") Long sprintId, @RequestBody TransitionDto transition) {

		LOGGER.debug("Transition sprint {}", sprintId);

		Sprint obj = sprintService.getById(sprintId);

		if (SprintStatus.STARTED.getKey() == transition.getState()) {

			List<Sprint> activeSprints = sprintService.getActiveSprints(projectId);

			if (activeSprints == null || activeSprints.isEmpty()) {

				Integer spCountTask = sprintTaskService.getTotalStoryPoint(sprintId);
				Integer spCountIssue = sprintIssueService.getTotalStoryPoint(sprintId);

				int task = 0;
				int issue = 0;

				if (spCountTask != null) {
					task = spCountTask.intValue();
				}

				if (spCountIssue != null) {
					issue = spCountIssue.intValue();
				}

				obj = sprintService.getById(sprintId);
				obj.setStatus(SprintStatus.STARTED);
				obj = sprintService.update(obj);

				SprintLog sprintLog = new SprintLog();
				sprintLog.setAction(SprintLogAction.SPRINT_STARTED);
				sprintLog.setDescription("Sprint started");
				sprintLog.setSprint(obj);
				sprintLog.setStoryPoint(task + issue);
				sprintLog.setBalanceStoryPoint(task + issue);
				sprintLogService.create(sprintLog);

			} else {
				throw new BusinessException(HttpStatus.BAD_REQUEST,
						"Cannot start this sprint as another sprint is already running");
			}

		} else if (SprintStatus.COMPLETED.getKey() == transition.getState()) {

			if (SprintStatus.STARTED == obj.getStatus()) {
				obj.setStatus(SprintStatus.COMPLETED);
				obj = sprintService.update(obj);

				SprintLog lastLog = sprintLogService.getLastLogBySprintId(sprintId);

				SprintLog sprintLog = new SprintLog();
				sprintLog.setAction(SprintLogAction.SPRINT_COMPLETED);
				sprintLog.setDescription("Sprint completed");
				sprintLog.setSprint(obj);
				sprintLog.setBalanceStoryPoint(lastLog.getBalanceStoryPoint());
				sprintLogService.create(sprintLog);

			} else {
				throw new BusinessException(HttpStatus.BAD_REQUEST, "Sprint is not started yet");
			}

		}

		return new ResponseEntity<Sprint>(obj, HttpStatus.OK);
	}

	/**
	 * Delete sprint.
	 *
	 * @param projectId the project id
	 * @param id        the id
	 * @return the response entity
	 */
	@DeleteMapping(value = "/{projectId}/sprints/{id}", headers = Constants.ApiVersion.V1)
	@JsonView(Views.Project.class)
	public ResponseEntity<Void> deleteSprint(@PathVariable("projectId") Long projectId, @PathVariable("id") Long id) {

		LOGGER.debug("Delete sprint {}", id);

		sprintService.delete(id);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	/**
	 * Gets the sprint.
	 *
	 * @param projectId the project id
	 * @param id        the id
	 * @return the sprint
	 */
	@GetMapping(value = "/{projectId}/sprints/{id}", headers = Constants.ApiVersion.V1)
	@JsonView(Views.Sprint.class)
	public ResponseEntity<Sprint> getSprint(@PathVariable("projectId") Long projectId, @PathVariable("id") Long id) {

		LOGGER.debug("Get sprint {}", id);

		Sprint obj = sprintService.getById(id);

		return new ResponseEntity<Sprint>(obj, HttpStatus.OK);
	}

	/**
	 * Gets all Sprints in Project(Pagination).
	 *
	 * @param projectId the project id
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @return the all
	 */
	@GetMapping(value = "/{projectId}/sprints", headers = Constants.ApiVersion.V1)
	@JsonView(SprintPageble.class)
	public ResponseEntity<PageView> getAllSprints(@PathVariable("projectId") Long projectId,
			@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) {

		LOGGER.debug("Get All Sprints {}", projectId);

		PageView obj = sprintService.getSprintByProjectId(projectId, PageRequest.of(pageNo, pageSize));

		return new ResponseEntity<PageView>(obj, HttpStatus.OK);
	}

	/**
	 * Gets all Sprints in Project(Without Pagination).
	 *
	 * @param projectId the project id
	 * @return the all
	 */
	@GetMapping(value = "/{projectId}/allsprints", headers = Constants.ApiVersion.V1)
	@JsonView(Views.Sprint.class)
	public ResponseEntity<Set<Sprint>> getAllSprintsInProjects(@PathVariable("projectId") Long projectId) {

		LOGGER.debug("Get All Sprints without Pagination {}", projectId);

		Project obj = projectService.getById(projectId);

		return new ResponseEntity<Set<Sprint>>(obj.getSprints(), HttpStatus.OK);
	}

	/**
	 * Gets all Sprints in Project(Without Pagination).
	 *
	 * @param projectId the project id
	 * @return the all
	 */
	@GetMapping(value = "/{projectId}/allActivesprints", headers = Constants.ApiVersion.V1)
	@JsonView(Views.Sprint.class)
	public ResponseEntity<List<Sprint>> getActiveSprints(@PathVariable("projectId") Long projectId) {

		LOGGER.debug("Get Active Sprints {}", projectId);

		List<Sprint> obj = sprintService.getActiveSprints(projectId);

		return new ResponseEntity<List<Sprint>>(obj, HttpStatus.OK);
	}

	/**
	 * Creates the issue.
	 *
	 * @param projectId the project id
	 * @param issue     the issue
	 * @return the response entity
	 */
	@PostMapping(value = "/{projectId}/issues", headers = Constants.ApiVersion.V1)
	@JsonView(Views.Issue.class)
	public ResponseEntity<Issue> createIssue(@PathVariable("projectId") Long projectId,
			@Valid @RequestBody Issue issue) {

		LOGGER.debug("Create issue {}", issue);

		Project project = projectService.getById(projectId);
		issue.setProject(project);
		Issue obj = issueService.create(issue);

		return new ResponseEntity<Issue>(obj, HttpStatus.OK);
	}

	/**
	 * Update issue.
	 *
	 * @param projectId the project id
	 * @param issue     the issue
	 * @return the response entity
	 */
	@PutMapping(value = "/{projectId}/issues", headers = Constants.ApiVersion.V1)
	@JsonView(Views.Issue.class)
	public ResponseEntity<Issue> updateIssue(@PathVariable("projectId") Long projectId,
			@Valid @RequestBody Issue issue) {

		LOGGER.debug("Update issue {}", issue);

		Project project = projectService.getById(projectId);
		issue.setProject(project);
		Issue obj = issueService.update(issue);

		return new ResponseEntity<Issue>(obj, HttpStatus.OK);
	}

	/**
	 * Delete issue.
	 *
	 * @param projectId the project id
	 * @param id        the id
	 * @return the response entity
	 */
	@DeleteMapping(value = "/{projectId}/issues/{id}", headers = Constants.ApiVersion.V1)
	public ResponseEntity<Void> deleteIssue(@PathVariable("projectId") Long projectId,
			@PathVariable("id") @RequestParam Long id) {

		LOGGER.debug("Delete issue {}", id);

		issueService.delete(id);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	/**
	 * Gets the issue.
	 *
	 * @param projectId the project id
	 * @param id        the id
	 * @return the issue
	 */
	@GetMapping(value = "/{projectId}/issues/{id}", headers = Constants.ApiVersion.V1)
	@JsonView(Views.ProjectDeatils.class)
	public ResponseEntity<Issue> getIssue(@PathVariable("projectId") Long projectId, @PathVariable("id") Long id) {

		LOGGER.debug("Get issue {}", id);

		Issue obj = issueService.getById(id);

		return new ResponseEntity<Issue>(obj, HttpStatus.OK);
	}

	/**
	 * Gets all Epics in Issues(Pagination).
	 *
	 * @param projectId the project id
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @return the all
	 */
	@GetMapping(value = "/{projectId}/issues", headers = Constants.ApiVersion.V1)
	@JsonView(Views.IssuePageable.class)
	public ResponseEntity<PageView> getAllIssues(@PathVariable("projectId") Long projectId,
			@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) {
		LOGGER.debug("Get All Issues {}", projectId);

		PageView obj = issueService.getIssueByProjectId(projectId, PageRequest.of(pageNo, pageSize));

		return new ResponseEntity<PageView>(obj, HttpStatus.OK);
	}

	/**
	 * Gets all Issues in Project(Without Pagination).
	 *
	 * @param projectId the project id
	 * @return the all
	 */
	@GetMapping(value = "/{projectId}/allissues", headers = Constants.ApiVersion.V1)
	@JsonView(Views.Issue.class)
	public ResponseEntity<Set<Issue>> getAllIssuesInProjects(@PathVariable("projectId") Long projectId) {

		LOGGER.debug("Get All Issues without Pagination {}", projectId);

		Project obj = projectService.getById(projectId);

		return new ResponseEntity<Set<Issue>>(obj.getIssues(), HttpStatus.OK);
	}

	/**
	 * Gets the backlog issue.
	 *
	 * @param projectId the project id
	 * @return the backlog issue
	 */
	@GetMapping(value = "/{projectId}/issues/backlog", headers = Constants.ApiVersion.V1)
	@JsonView(Views.IssueWithUser.class)
	public ResponseEntity<List<Issue>> getBacklogIssue(@PathVariable("projectId") Long projectId) {

		List<Issue> objs = issueService.getBacklogIssuesByProject(projectId);

		return new ResponseEntity<List<Issue>>(objs, HttpStatus.OK);
	}

	/**
	 * Gets all Stories in Project(Pagination).
	 *
	 * @param projectId the project id
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @return the all
	 */
	@GetMapping(value = "/{projectId}/stories", headers = Constants.ApiVersion.V1)
	@JsonView(Views.StoryPageable.class)
	public ResponseEntity<PageView> getAllStories(@PathVariable("projectId") Long projectId,
			@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) {
		LOGGER.debug("Get All Stories in Project {}", projectId);

		PageView obj = storyService.getStoriesInProject(projectId, PageRequest.of(pageNo, pageSize));

		return new ResponseEntity<PageView>(obj, HttpStatus.OK);
	}

	/**
	 * Gets all Stories in Project(Without Pagination).
	 *
	 * @param projectId the project id
	 * @return the all
	 */
	@GetMapping(value = "/{projectId}/allstories", headers = Constants.ApiVersion.V1)
	@JsonView(Views.Story.class)
	public ResponseEntity<List<Story>> getAllStoriesInProjects(@PathVariable("projectId") Long projectId) {

		LOGGER.debug("Get All Stories without Pagination {}", projectId);

		List<Story> obj = storyService.getAllStoriesInProject(projectId);
		return new ResponseEntity<List<Story>>(obj, HttpStatus.OK);
	}

	/**
	 * Gets all Tasks in Project(Pagination).
	 *
	 * @param projectId the project id
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @return the all
	 */
	@GetMapping(value = "/{projectId}/tasks", headers = Constants.ApiVersion.V1)
	@JsonView(Views.TaskPageable.class)
	public ResponseEntity<PageView> getAllTasks(@PathVariable("projectId") Long projectId,
			@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) {
		LOGGER.debug("Get All Tasks in Project {}", projectId);

		PageView obj = taskService.getTasksByProject(projectId, PageRequest.of(pageNo, pageSize));

		return new ResponseEntity<PageView>(obj, HttpStatus.OK);
	}

	/**
	 * Gets all Tasks in Project(Without Pagination).
	 *
	 * @param projectId the project id
	 * @return the all
	 */
	@GetMapping(value = "/{projectId}/alltasks", headers = Constants.ApiVersion.V1)
	@JsonView(Views.StoryTaskDetails.class)
	public ResponseEntity<List<Task>> getAllTasksInProjects(@PathVariable("projectId") Long projectId) {
		LOGGER.debug("Get All Tasks in Project without pagination {}", projectId);

		List<Task> objs = taskService.getAllTasksByProject(projectId);

		return new ResponseEntity<List<Task>>(objs, HttpStatus.OK);
	}

	/**
	 * Gets the backlog task.
	 *
	 * @param projectId the project id
	 * @return the backlog task
	 */
	@GetMapping(value = "/{projectId}/tasks/backlog", headers = Constants.ApiVersion.V1)
	@JsonView(Views.TaskWithUser.class)
	public ResponseEntity<List<Task>> getBacklogTask(@PathVariable("projectId") Long projectId) {

		List<Task> objs = taskService.getBacklogTasksByProject(projectId);

		return new ResponseEntity<List<Task>>(objs, HttpStatus.OK);
	}

	/**
	 * Creates the holiday.
	 *
	 * @param projectId the project id
	 * @param holiday the holiday
	 * @return the response entity
	 */
	@PostMapping(value = "/{projectId}/holidays", headers = Constants.ApiVersion.V1)
	@JsonView(Views.Issue.class)
	public ResponseEntity<ProjectHoliday> createHoliday(@PathVariable("projectId") Long projectId,
			@Valid @RequestBody ProjectHoliday holiday) {

		LOGGER.debug("Create holiday {}", holiday);

		Project project = projectService.getById(projectId);
		holiday.setProject(project);
		ProjectHoliday obj = projectHolidayService.create(holiday);

		return new ResponseEntity<ProjectHoliday>(obj, HttpStatus.OK);
	}

	/**
	 * Delete holiday.
	 *
	 * @param projectId the project id
	 * @param id the id
	 * @return the response entity
	 */
	@DeleteMapping(value = "/{projectId}/holidays/{id}", headers = Constants.ApiVersion.V1)
	public ResponseEntity<Void> deleteHoliday(@PathVariable("projectId") Long projectId,
			@PathVariable("id") @RequestParam Long id) {

		LOGGER.debug("Delete holiday {}", id);

		projectHolidayService.delete(id);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	/**
	 * Gets the issue.
	 *
	 * @param projectId the project id
	 * @return the issue
	 */
	@GetMapping(value = "/{projectId}/holidays", headers = Constants.ApiVersion.V1)
	@JsonView(Views.ProjectDeatils.class)
	public ResponseEntity<List<ProjectHoliday>> getHoliday(@PathVariable("projectId") Long projectId) {

		LOGGER.debug("Get holidays {}", projectId);

		List<ProjectHoliday> objs = projectHolidayService.getFutureHolidayByProject(projectId);

		return new ResponseEntity<List<ProjectHoliday>>(objs, HttpStatus.OK);
	}

	/**
	 * Gets one.
	 *
	 * @param projectId the projectId
	 * @return the by projectId
	 */
	@GetMapping(value = "/releases/{projectId}", headers = Constants.ApiVersion.V1)
	@JsonView(Views.ReleaseDetails.class)
	public ResponseEntity<Release> getReleaseDetails(@PathVariable("projectId") Long projectId) {

		LOGGER.debug("Get Release Details {}", projectId);

		Release obj = releaseService.getReleaseByProjectId(projectId);

		return new ResponseEntity<Release>(obj, HttpStatus.OK);
	}

	/**
	 * Gets all.
	 *
	 * @param projectId the projectId
	 * @return the by projectId
	 */
	@GetMapping(value = "/{projectId}/releases", headers = Constants.ApiVersion.V1)
	@JsonView(Views.ReleaseDetails.class)
	public ResponseEntity<List<Release>> getAllReleaseListInProject(@PathVariable("projectId") Long projectId) {

		LOGGER.debug("Get All Release Details {}", projectId);

		List<Release> objs = releaseService.getAllReleaseListInProject(projectId);

		return new ResponseEntity<List<Release>>(objs, HttpStatus.OK);
	}

	/**
	 * Creates the release.
	 *
	 * @param projectId the project id
	 * @param release the release
	 * @return the response entity
	 */
	@PostMapping(value = "/{projectId}/releases", headers = Constants.ApiVersion.V1)
	@JsonView(Views.Release.class)
	public ResponseEntity<Release> createRelease(@PathVariable("projectId") Long projectId,
			@Valid @RequestBody Release release) {

		LOGGER.debug("Create release {}", release);

		Project project = projectService.getById(projectId);
		release.setProject(project);

		Release obj = releaseService.create(release);

		return new ResponseEntity<Release>(obj, HttpStatus.OK);
	}

	/**
	 * Update release.
	 *
	 * @param projectId the project id
	 * @param release   the release
	 * @return the response entity
	 */
	@PutMapping(value = "/{projectId}/releases", headers = Constants.ApiVersion.V1)
	@JsonView(Views.Release.class)
	public ResponseEntity<Release> updateRelease(@PathVariable("projectId") Long projectId,
			@Valid @RequestBody Release release) {

		LOGGER.debug("Update release {}", release);

		Project project = projectService.getById(projectId);
		release.setProject(project);
		Release obj = releaseService.update(release);

		return new ResponseEntity<Release>(obj, HttpStatus.OK);
	}

	/**
	 * Delete release.
	 *
	 * @param projectId the project id
	 * @param id        the id
	 * @return the response entity
	 */
	@DeleteMapping(value = "/{projectId}/releases/{id}", headers = Constants.ApiVersion.V1)
	@JsonView(Views.Release.class)
	public ResponseEntity<Void> deleteRelease(@PathVariable("projectId") Long projectId, @PathVariable("id") Long id) {

		LOGGER.debug("Delete release {}", id);

		releaseService.delete(id);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	/**
	 * Gets the sprint chart.
	 *
	 * @param projectId the project id
	 * @return the sprint chart
	 */
	@GetMapping(value = "/{projectId}/sprintchart", headers = Constants.ApiVersion.V1)
	@JsonView(Views.SprintChart.class)
	public ResponseEntity<List<SprintLog>> getSprintChart(@PathVariable("projectId") Long projectId) {

		LOGGER.debug("Get sprint chart {}", projectId);

		List<SprintLog> objs = sprintLogService.getSprinLogByProjectIdAndAction(projectId,
				SprintLogAction.SPRINT_COMPLETED);

		return new ResponseEntity<List<SprintLog>>(objs, HttpStatus.OK);
	}

	/**
	 * Gets the issue chart.
	 *
	 * @param projectId the project id
	 * @return the issue chart
	 */
	@GetMapping(value = "/{projectId}/issuechart", headers = Constants.ApiVersion.V1)
	public ResponseEntity<List<ReleaseIssueCount>> getIssueChart(@PathVariable("projectId") Long projectId) {

		LOGGER.debug("Get issue chart {}", projectId);

		List<ReleaseIssueCount> objs = issueService.getReleaseIssueCountByProject(projectId);

		return new ResponseEntity<List<ReleaseIssueCount>>(objs, HttpStatus.OK);
	}
}
