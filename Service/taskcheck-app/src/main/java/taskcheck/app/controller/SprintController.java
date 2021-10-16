/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import taskcheck.app.config.Constants;
import taskcheck.app.dto.AuthUser;
import taskcheck.app.dto.BurndownChartDto;
import taskcheck.app.dto.DeAllocateRequestDto;
import taskcheck.app.dto.SprintProgressDto;
import taskcheck.app.dto.TransitionDto;
import taskcheck.app.service.CommentService;
import taskcheck.app.service.IssueService;
import taskcheck.app.service.SprintIssueService;
import taskcheck.app.service.SprintLogService;
import taskcheck.app.service.SprintService;
import taskcheck.app.service.SprintTaskService;
import taskcheck.app.service.TaskService;
import taskcheck.data.entity.Comment;
import taskcheck.data.entity.Issue;
import taskcheck.data.entity.Sprint;
import taskcheck.data.entity.SprintIssue;
import taskcheck.data.entity.SprintLog;
import taskcheck.data.entity.SprintTask;
import taskcheck.data.entity.Task;
import taskcheck.data.entity.User;
import taskcheck.data.enums.IssueStatus;
import taskcheck.data.enums.SprintLogAction;
import taskcheck.data.enums.SprintStatus;
import taskcheck.data.enums.TaskStatus;
import taskcheck.data.repository.SprintRepository.SprintDay;
import taskcheck.data.view.Views;

/**
 * The Class SprintController.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/sprints")
public class SprintController {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(SprintController.class);

	/** The sprint service. */
	@Autowired
	private SprintService sprintService;

	/** The task service. */
	@Autowired
	private TaskService taskService;

	/** The issue service. */
	@Autowired
	private IssueService issueService;

	/** The sprint log service. */
	@Autowired
	private SprintLogService sprintLogService;

	/** The comment service. */
	@Autowired
	private CommentService commentService;

	/** The sprint task service. */
	@Autowired
	private SprintTaskService sprintTaskService;

	/** The sprint issue service. */
	@Autowired
	private SprintIssueService sprintIssueService;

	/**
	 * Transition task.
	 *
	 * @param sprintId the sprint id
	 * @param taskId the task id
	 * @param transition the transition
	 * @return the response entity
	 */
	@PutMapping(value = "/{sprintId}/tasks/{taskId}/transition", headers = Constants.ApiVersion.V1)
	@JsonView(Views.Task.class)
	public ResponseEntity<Task> transitionTask(@PathVariable("sprintId") Long sprintId,
			@PathVariable("taskId") Long taskId, @RequestBody TransitionDto transition) {

		LOGGER.debug("Transition task {}", taskId);

		Task obj = taskService.getById(taskId);

		if (TaskStatus.IN_PROGRESS.getKey() == transition.getState()) {

			obj.setStatus(TaskStatus.IN_PROGRESS);
			obj = taskService.update(obj);

		} else if (TaskStatus.COMPLETED.getKey() == transition.getState()) {

			obj.setStatus(TaskStatus.COMPLETED);
			obj = taskService.update(obj);

			SprintLog lastLog = sprintLogService.getLastLogBySprintId(sprintId);

			Sprint sprint = sprintService.getById(sprintId);

			SprintLog sprintLog = new SprintLog();
			sprintLog.setAction(SprintLogAction.TASK_COMPLETED);
			sprintLog.setDescription("Task completed");
			sprintLog.setSprint(sprint);
			sprintLog.setStoryPoint(obj.getStoryPoint());
			sprintLog.setBalanceStoryPoint(lastLog.getBalanceStoryPoint() - obj.getStoryPoint());
			sprintLogService.create(sprintLog);
		}

		Comment comment = new Comment();
		comment.setIdentifier(obj.getIdentifier());
		comment.setComment(transition.getComment());
		commentService.create(comment);

		return new ResponseEntity<Task>(obj, HttpStatus.OK);
	}

	/**
	 * All tasks.
	 *
	 * @param sprintId the sprint id
	 * @param state the state
	 * @return the response entity
	 */
	@GetMapping(value = "/{sprintId}/tasks", headers = Constants.ApiVersion.V1)
	@JsonView(Views.TaskWithUser.class)
	public ResponseEntity<Set<Task>> allTasks(@PathVariable("sprintId") Long sprintId,
			@RequestParam(name = "s", required = false) String state) {

		Set<Task> tasks = null;

		if (state != null && "OPEN".equalsIgnoreCase(state) || "IN_PROGRESS".equalsIgnoreCase(state)) {
			List<SprintTask> list = sprintTaskService.getPendingBySprint(sprintId);
			tasks = new HashSet<Task>();
			for (SprintTask s : list) {
				tasks.add(s.getTask());
			}
		} else {
			Sprint sprint = sprintService.getById(sprintId);
			tasks = sprint.getTasks();
		}

		return new ResponseEntity<Set<Task>>(tasks, HttpStatus.OK);
	}

	/**
	 * Allocate task.
	 *
	 * @param sprintId the sprint id
	 * @param taskId the task id
	 * @return the response entity
	 */
	@PutMapping(value = "/{sprintId}/tasks/{taskId}/allocate", headers = Constants.ApiVersion.V1)
	@JsonView(Views.Task.class)
	public ResponseEntity<SprintTask> allocateTask(@PathVariable("sprintId") Long sprintId,
			@PathVariable("taskId") Long taskId) {

		Sprint sprint = sprintService.getById(sprintId);
		SprintStatus status = sprint.getStatus();

		Task task = taskService.getById(taskId);

		if (SprintStatus.STARTED.equals(status)) {

			SprintLog lastLog = sprintLogService.getLastLogBySprintId(sprintId);

			SprintLog sprintLog = new SprintLog();
			sprintLog.setAction(SprintLogAction.SCOPE_INCREASED);
			sprintLog.setDescription("New Task added, " + task.getIdentifier());
			sprintLog.setSprint(sprint);
			sprintLog.setStoryPoint(task.getStoryPoint());
			sprintLog.setBalanceStoryPoint(lastLog.getBalanceStoryPoint() + task.getStoryPoint());
			sprintLogService.create(sprintLog);
		}

		SprintTask sprintTask = new SprintTask();
		sprintTask.setSprint(sprint);
		sprintTask.setTask(task);
		sprintTask = sprintTaskService.create(sprintTask);

		return new ResponseEntity<SprintTask>(sprintTask, HttpStatus.OK);
	}

	/**
	 * Allocate task.
	 *
	 * @param sprintId the sprint id
	 * @param requestDtos the request dtos
	 * @return the response entity
	 */
	@PutMapping(value = "/{sprintId}/bulkdeallocate", headers = Constants.ApiVersion.V1)
	@JsonView(Views.Task.class)
	public ResponseEntity<Void> allocateTask(@PathVariable("sprintId") Long sprintId,
			@RequestBody List<DeAllocateRequestDto> requestDtos) {
		Sprint sprint = sprintService.getById(sprintId);

		for (DeAllocateRequestDto allocateDto : requestDtos) {

			if (allocateDto.getType() == 1) { // TASK

				descopeTask(sprintId, allocateDto.getId(), sprint);

			} else if (allocateDto.getType() == 2) { // ISSUE
				descopeIssue(sprintId, allocateDto.getId(), sprint);
			}

		}

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	/**
	 * Deallocate task.
	 *
	 * @param sprintId the sprint id
	 * @param taskId the task id
	 * @return the response entity
	 */
	@PutMapping(value = "/{sprintId}/tasks/{taskId}/deallocate", headers = Constants.ApiVersion.V1)
	@JsonView(Views.Task.class)
	public ResponseEntity<Void> deallocateTask(@PathVariable("sprintId") Long sprintId,
			@PathVariable("taskId") Long taskId) {

		Sprint sprint = sprintService.getById(sprintId);

		descopeTask(sprintId, taskId, sprint);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	/**
	 * Descope task.
	 *
	 * @param sprintId the sprint id
	 * @param taskId the task id
	 * @param sprint the sprint
	 */
	private void descopeTask(Long sprintId, Long taskId, Sprint sprint) {

		SprintStatus status = sprint.getStatus();

		Task task = taskService.getById(taskId);

		if (SprintStatus.STARTED.equals(status)) {
			SprintLog lastLog = sprintLogService.getLastLogBySprintId(sprintId);

			SprintLog sprintLog = new SprintLog();
			sprintLog.setAction(SprintLogAction.SCOPE_REDUCED);
			sprintLog.setDescription("Task removed, " + task.getIdentifier());
			sprintLog.setSprint(sprint);
			sprintLog.setStoryPoint(task.getStoryPoint());
			sprintLog.setBalanceStoryPoint(lastLog.getBalanceStoryPoint() - task.getStoryPoint());
			sprintLogService.create(sprintLog);
		}

		sprintTaskService.delete(sprintId, taskId);
	}

	/**
	 * Transition issue.
	 *
	 * @param sprintId the sprint id
	 * @param issueId the issue id
	 * @param transition the transition
	 * @return the response entity
	 */
	@PutMapping(value = "/{sprintId}/issues/{issueId}/transition", headers = Constants.ApiVersion.V1)
	@JsonView(Views.Issue.class)
	public ResponseEntity<Issue> transitionIssue(@PathVariable("sprintId") Long sprintId,
			@PathVariable("issueId") Long issueId, @RequestBody TransitionDto transition) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		AuthUser user = (AuthUser) auth.getPrincipal();

		LOGGER.debug("Transition issue {}", issueId);
		Issue obj = issueService.getById(issueId);

		IssueStatus[] statuses = IssueStatus.values();

		for (IssueStatus status : statuses) {
			if (status.getKey() == transition.getState()) {

				obj.setStatus(status);

				User assignee = new User();
				assignee.setId(transition.getAssignee());
				obj.setAssignee(assignee);

				obj = issueService.update(obj);

				if (IssueStatus.AS_DESIGNED.equals(status) || IssueStatus.NOT_A_DEFECT.equals(status)
						|| IssueStatus.RESOLVED.equals(status)) {

					obj.setResolvedBy(user.getUser());

					SprintLog lastLog = sprintLogService.getLastLogBySprintId(sprintId);

					Sprint sprint = sprintService.getById(sprintId);

					SprintLog sprintLog = new SprintLog();
					sprintLog.setAction(SprintLogAction.ISSUE_COMPLETED);
					sprintLog.setDescription("Issue completed as " + status.getValue());
					sprintLog.setSprint(sprint);
					sprintLog.setStoryPoint(obj.getStoryPoint());
					sprintLog.setBalanceStoryPoint(lastLog.getBalanceStoryPoint() - obj.getStoryPoint());
					sprintLogService.create(sprintLog);
				}

				if (IssueStatus.CLOSED.equals(status) || IssueStatus.REOPENED.equals(status)) {
					obj.setVerifiedBy(user.getUser());
				}
				break;
			}
		}

		Comment comment = new Comment();
		comment.setIdentifier(obj.getIdentifier());
		comment.setComment(transition.getComment());
		commentService.create(comment);

		return new ResponseEntity<Issue>(obj, HttpStatus.OK);
	}

	/**
	 * All issues.
	 *
	 * @param sprintId the sprint id
	 * @param state the state
	 * @return the response entity
	 */
	@GetMapping(value = "/{sprintId}/issues", headers = Constants.ApiVersion.V1)
	@JsonView(Views.IssueWithUser.class)
	public ResponseEntity<Set<Issue>> allIssues(@PathVariable("sprintId") Long sprintId,
			@RequestParam(name = "s", required = false) String state) {

		Set<Issue> issues = null;

		if (state != null && "OPEN".equalsIgnoreCase(state) || "IN_PROGRESS".equalsIgnoreCase(state)) {
			List<SprintIssue> list = sprintIssueService.getPendingBySprint(sprintId);
			issues = new HashSet<Issue>();

			for (SprintIssue s : list) {
				issues.add(s.getIssue());
			}
		} else {
			Sprint sprint = sprintService.getById(sprintId);
			issues = sprint.getIssue();
		}

		return new ResponseEntity<Set<Issue>>(issues, HttpStatus.OK);
	}

	/**
	 * Allocate issue.
	 *
	 * @param sprintId the sprint id
	 * @param issueId the issue id
	 * @return the response entity
	 */
	@PutMapping(value = "/{sprintId}/issues/{issueId}/allocate", headers = Constants.ApiVersion.V1)
	@JsonView(Views.Task.class)
	public ResponseEntity<SprintIssue> allocateIssue(@PathVariable("sprintId") Long sprintId,
			@PathVariable("issueId") Long issueId) {

		Sprint sprint = sprintService.getById(sprintId);
		SprintStatus status = sprint.getStatus();

		Issue issue = issueService.getById(issueId);

		if (SprintStatus.STARTED.equals(status)) {
			SprintLog lastLog = sprintLogService.getLastLogBySprintId(sprintId);

			SprintLog sprintLog = new SprintLog();
			sprintLog.setAction(SprintLogAction.SCOPE_INCREASED);
			sprintLog.setDescription("New Issue added, " + issue.getIdentifier());
			sprintLog.setSprint(sprint);
			sprintLog.setStoryPoint(issue.getStoryPoint());
			sprintLog.setBalanceStoryPoint(lastLog.getBalanceStoryPoint() + issue.getStoryPoint());
			sprintLogService.create(sprintLog);
		}

		SprintIssue sprintIssue = new SprintIssue();
		sprintIssue.setSprint(sprint);
		sprintIssue.setIssue(issue);
		sprintIssue = sprintIssueService.create(sprintIssue);

		return new ResponseEntity<SprintIssue>(sprintIssue, HttpStatus.OK);
	}

	/**
	 * Deallocate issue.
	 *
	 * @param sprintId the sprint id
	 * @param issueId the issue id
	 * @return the response entity
	 */
	@PutMapping(value = "/{sprintId}/issues/{issueId}/deallocate", headers = Constants.ApiVersion.V1)
	@JsonView(Views.Task.class)
	public ResponseEntity<Void> deallocateIssue(@PathVariable("sprintId") Long sprintId,
			@PathVariable("issueId") Long issueId) {

		Sprint sprint = sprintService.getById(sprintId);

		descopeIssue(sprintId, issueId, sprint);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	/**
	 * Descope issue.
	 *
	 * @param sprintId the sprint id
	 * @param issueId the issue id
	 * @param sprint the sprint
	 */
	private void descopeIssue(Long sprintId, Long issueId, Sprint sprint) {
		SprintStatus status = sprint.getStatus();

		Issue issue = issueService.getById(issueId);

		if (SprintStatus.STARTED.equals(status)) {
			SprintLog lastLog = sprintLogService.getLastLogBySprintId(sprintId);

			SprintLog sprintLog = new SprintLog();
			sprintLog.setAction(SprintLogAction.SCOPE_REDUCED);
			sprintLog.setDescription("Issue removed, " + issue.getIdentifier());
			sprintLog.setSprint(sprint);
			sprintLog.setStoryPoint(issue.getStoryPoint());
			sprintLog.setBalanceStoryPoint(lastLog.getBalanceStoryPoint() - issue.getStoryPoint());
			sprintLogService.create(sprintLog);
		}

		sprintIssueService.delete(sprintId, issueId);
	}

	/**
	 * Progress.
	 *
	 * @param sprintId the sprint id
	 * @return the response entity
	 */
	@GetMapping(value = "/{sprintId}/progress", headers = Constants.ApiVersion.V1)
	public ResponseEntity<SprintProgressDto> progress(@PathVariable("sprintId") Long sprintId) {

		Integer taskOpen = sprintTaskService.getTotalStoryPointByStatus(sprintId, TaskStatus.OPEN);
		Integer taskInProgress = sprintTaskService.getTotalStoryPointByStatus(sprintId, TaskStatus.IN_PROGRESS);
		Integer taskCompleted = sprintTaskService.getTotalStoryPointByStatus(sprintId, TaskStatus.COMPLETED);

		Integer issueOpen = sprintIssueService.getTotalStoryPointByStatus(sprintId, IssueStatus.OPEN);
		Integer issueReOpen = sprintIssueService.getTotalStoryPointByStatus(sprintId, IssueStatus.REOPENED);
		Integer issueInProgress = sprintIssueService.getTotalStoryPointByStatus(sprintId, IssueStatus.IN_PROGRESS);
		Integer issueAsDesigned = sprintIssueService.getTotalStoryPointByStatus(sprintId, IssueStatus.AS_DESIGNED);
		Integer issueClosed = sprintIssueService.getTotalStoryPointByStatus(sprintId, IssueStatus.CLOSED);
		Integer issueNotADefect = sprintIssueService.getTotalStoryPointByStatus(sprintId, IssueStatus.NOT_A_DEFECT);
		Integer issueResolved = sprintIssueService.getTotalStoryPointByStatus(sprintId, IssueStatus.RESOLVED);

		int totalTodo = (taskOpen != null ? taskOpen.intValue() : 0) + (issueOpen != null ? issueOpen.intValue() : 0)
				+ (issueReOpen != null ? issueReOpen.intValue() : 0);
		int totalInProgress = (taskInProgress != null ? taskInProgress.intValue() : 0)
				+ (issueInProgress != null ? issueInProgress.intValue() : 0);
		int totalCompleted = (taskCompleted != null ? taskCompleted.intValue() : 0)
				+ (issueAsDesigned != null ? issueAsDesigned.intValue() : 0)
				+ (issueClosed != null ? issueClosed.intValue() : 0)
				+ (issueNotADefect != null ? issueNotADefect.intValue() : 0)
				+ (issueResolved != null ? issueResolved.intValue() : 0);

		SprintProgressDto dto = new SprintProgressDto();
		dto.setTodo(totalTodo);
		dto.setInprogress(totalInProgress);
		dto.setCompleted(totalCompleted);
		
		return new ResponseEntity<SprintProgressDto>(dto, HttpStatus.OK);
	}
	

	@GetMapping(value = "/{sprintId}/completedTaskCount", headers = Constants.ApiVersion.V1)
	public ResponseEntity<SprintProgressDto> completedTaskCount(@PathVariable("sprintId") Long sprintId) {
		Integer taskOpen = sprintTaskService.getSprintOpenTasks(sprintId);
		Integer taskInprogress = sprintTaskService.getSprintInProgressTasks(sprintId);
		Integer taskCompleted = sprintTaskService.getSprintCompletedTasks(sprintId);
		
		int totalOpen = (taskOpen != null ? taskOpen.intValue() : 0);
		int totalInprogress = (taskInprogress != null ? taskInprogress.intValue() : 0);
		int totalCompleted = (taskCompleted != null ? taskCompleted.intValue() : 0);
		SprintProgressDto dto = new SprintProgressDto();
		dto.setTodo(totalOpen);
		dto.setInprogress(totalInprogress);
		dto.setCompleted(totalCompleted);

		return new ResponseEntity<SprintProgressDto>(dto, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{sprintId}/completedIssueCount", headers = Constants.ApiVersion.V1)
	public ResponseEntity<SprintProgressDto> completedIssueCount(@PathVariable("sprintId") Long sprintId) {
		
		Integer issueOpen = sprintIssueService.getSprintIssueByStatus(sprintId, IssueStatus.OPEN);
		Integer issueReOpen = sprintIssueService.getSprintIssueByStatus(sprintId, IssueStatus.REOPENED);
		Integer issueInProgress = sprintIssueService.getSprintIssueByStatus(sprintId, IssueStatus.IN_PROGRESS);
		Integer issueAsDesigned = sprintIssueService.getSprintIssueByStatus(sprintId, IssueStatus.AS_DESIGNED);
		Integer issueClosed = sprintIssueService.getSprintIssueByStatus(sprintId, IssueStatus.CLOSED);
		Integer issueNotADefect = sprintIssueService.getSprintIssueByStatus(sprintId, IssueStatus.NOT_A_DEFECT);
		Integer issueResolved = sprintIssueService.getSprintIssueByStatus(sprintId, IssueStatus.RESOLVED);
		
		int totalTodo = (issueReOpen != null ? issueReOpen.intValue() : 0) + (issueOpen != null ? issueOpen.intValue() : 0);
		int totalInProgress = (issueInProgress != null ? issueInProgress.intValue() : 0);
		int totalCompleted = (issueAsDesigned != null ? issueAsDesigned.intValue() : 0)
				+ (issueClosed != null ? issueClosed.intValue() : 0)
				+ (issueNotADefect != null ? issueNotADefect.intValue() : 0)
				+ (issueResolved != null ? issueResolved.intValue() : 0);
		
		
		
		SprintProgressDto dto = new SprintProgressDto();
		dto.setTodo(totalTodo);
		dto.setInprogress(totalInProgress);
		dto.setCompleted(totalCompleted);

		return new ResponseEntity<SprintProgressDto>(dto, HttpStatus.OK);
	}
	
	/*@GetMapping(value = "/{sprintId}/inprogressTaskCount", headers = Constants.ApiVersion.V1)
	public ResponseEntity<SprintProgressDto> inprogressTaskCount(@PathVariable("sprintId") Long sprintId) {
		Integer taskInprogress = sprintTaskService.getSprintInProgressTasks(sprintId);
		int totalInprogress = (taskInprogress != null ? taskInprogress.intValue() : 0);
		SprintProgressDto dto = new SprintProgressDto();
		dto.setInprogress(totalInprogress);

		return new ResponseEntity<SprintProgressDto>(dto, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{sprintId}/openTaskCount", headers = Constants.ApiVersion.V1)
	public ResponseEntity<SprintProgressDto> openTaskCount(@PathVariable("sprintId") Long sprintId) {
		Integer taskOpen = sprintTaskService.getSprintOpenTasks(sprintId);
		int totalOpen = (taskOpen != null ? taskOpen.intValue() : 0);
		SprintProgressDto dto = new SprintProgressDto();
		dto.setTodo(totalOpen);

		return new ResponseEntity<SprintProgressDto>(dto, HttpStatus.OK);
	}*/
 /* Burndown.
 *
 * @param sprintId the sprint id
 * @return the response entity
 */
//=======
	@GetMapping(value = "/{sprintId}/burndownchart", headers = Constants.ApiVersion.V1)
	public ResponseEntity<Map<String, List<BurndownChartDto>>> burndown(@PathVariable("sprintId") Long sprintId) {

		Sprint sprint = sprintService.getById(sprintId);

		float numberOfDays = sprintService.getSprintLength(sprintId);
		Integer totalTask = sprintTaskService.getTotalStoryPoint(sprintId);
		Integer totalIssue = sprintIssueService.getTotalStoryPoint(sprintId);
		float totalSp = (totalTask != null ? totalTask.intValue() : 0)
				+ (totalIssue != null ? totalIssue.intValue() : 0);
		float avgValue = totalSp / numberOfDays;

		List<SprintLog> sprintLogs = sprintLogService.getAllBySprint(sprintId);

//<<<<<<< HEAD
//		Date start = sprint.getStartAt();
//		Date end = sprint.getEndAt();
//
//		Project project = sprint.getProject();
//		String workWeek = project.getWorkWeek();
//
//		List<ProjectHoliday> holidays = projectHolidayService.getHolidaysByProject(project.getId(), start, end);
//
//		Calendar startCalendar = Calendar.getInstance();
//		startCalendar.setTime(start);
//
//		Calendar endCalendar = Calendar.getInstance();
//		endCalendar.setTime(end);
//		int totalSp = totalTask + totalIssue;
//		while (endCalendar.before(startCalendar)) {
//
//			BurndownChartDto dto = new BurndownChartDto();
//			dto.setRecordedAt(startCalendar.getTime());
//			dto.setActualValue(totalSp);
//
//			startCalendar.add(Calendar.DATE, 1);
//		}
//
//		return new ResponseEntity<List<BurndownChartDto>>(objs, HttpStatus.OK);
//=======
		List<SprintDay> sprintDates = sprintService.getSprintDates(sprint.getProject().getId(), sprint.getStartAt(),
				sprint.getEndAt());

		float runningValue = totalSp;
		Map<String, List<BurndownChartDto>> map = new HashMap<String, List<BurndownChartDto>>();
		List<BurndownChartDto> perfectLine = new ArrayList<BurndownChartDto>();

		for (SprintDay sprintDate : sprintDates) {

			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(sprintDate.getTimeMills());
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);

			if (sprintDate.getWorkingDay() == 1) {
				runningValue = runningValue - avgValue;
			}

			BurndownChartDto dto = new BurndownChartDto();
			dto.setRecordedAt(cal.getTime());
			dto.setValue(runningValue);
			perfectLine.add(dto);

		}

		map.put("perfectLine", perfectLine);

		List<BurndownChartDto> actualLine = new ArrayList<BurndownChartDto>();

		for (SprintLog sprintLog : sprintLogs) {

			BurndownChartDto dto = new BurndownChartDto();
			dto.setRecordedAt(sprintLog.getCreatedAt());
			dto.setValue(sprintLog.getBalanceStoryPoint());
			actualLine.add(dto);

		}

		map.put("actualLine", actualLine);

		return new ResponseEntity<Map<String, List<BurndownChartDto>>>(map, HttpStatus.OK);
	}

}
