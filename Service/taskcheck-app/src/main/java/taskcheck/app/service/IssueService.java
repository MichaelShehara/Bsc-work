/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import taskcheck.data.entity.Issue;
import taskcheck.data.entity.PageView;
import taskcheck.data.repository.IssueRepository.ReleaseIssueCount;

/**
 * The Interface IssueService.
 */
public interface IssueService {

	/**
	 * Creates the.
	 *
	 * @param issue the issue
	 * @return the issue
	 */
	Issue create(Issue issue);

	/**
	 * Update.
	 *
	 * @param issue the issue
	 * @return the issue
	 */
	Issue update(Issue issue);

	/**
	 * Delete.
	 *
	 * @param id the id
	 */
	void delete(Long id);

	/**
	 * Gets the by id.
	 *
	 * @param id the id
	 * @return the by id
	 */
	Issue getById(Long id);

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	List<Issue> getAll();

	/**
	 * Gets the by id with pagination.
	 *
	 * @param id the id
	 * @param page the page
	 * @return the by id
	 */
	PageView getIssueByProjectId(Long id, Pageable page);
	
	/**
	 * Gets the backlog issues by project.
	 *
	 * @param projectId the project id
	 * @return the backlog issues by project
	 */
	List<Issue> getBacklogIssuesByProject(Long projectId);
	
	/**
	 * Gets the release issue count by project.
	 *
	 * @param projectId the project id
	 * @return the release issue count by project
	 */
	List<ReleaseIssueCount> getReleaseIssueCountByProject(Long projectId);
}
