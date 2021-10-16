/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import taskcheck.app.dto.AuthUser;
import taskcheck.app.service.SprintIssueService;
import taskcheck.data.entity.SprintIssue;
import taskcheck.data.enums.IssueStatus;
import taskcheck.data.repository.SprintIssueRepository;

/**
 * The Class SprintIssueServiceImpl.
 */
@Service
@Transactional
public class SprintIssueServiceImpl implements SprintIssueService {

	/** The sprint issue repository. */
	@Autowired
	private SprintIssueRepository sprintIssueRepository;

	/**
	 * Creates the.
	 *
	 * @param sprintIssue the sprint issue
	 * @return the sprint issue
	 */
	@Override
	public SprintIssue create(SprintIssue sprintIssue) {
		Date now = new Date();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		AuthUser user = (AuthUser) auth.getPrincipal();
		sprintIssue.setCreatedBy(user.getUser());
		sprintIssue.setCreatedAt(now);
		sprintIssue.setUpdatedBy(user.getUser());
		sprintIssue.setUpdatedAt(now);

		return sprintIssueRepository.save(sprintIssue);
	}

	/**
	 * Delete.
	 *
	 * @param sprintId the sprint id
	 * @param issueId  the issue id
	 */
	@Override
	public void delete(Long sprintId, Long issueId) {
		SprintIssue obj = sprintIssueRepository.getSprintIssueBySprintAndIssue(sprintId, issueId);
		obj.setVoided(Boolean.TRUE);
		sprintIssueRepository.save(obj);
	}

	/**
	 * Gets the total story point.
	 *
	 * @param sprintId the sprint id
	 * @return the total story point
	 */
	public Integer getTotalStoryPoint(Long sprintId) {
		return sprintIssueRepository.getTotalStoryPoint(sprintId);
	}

	/**
	 * Gets the total story point by status.
	 *
	 * @param sprintId the sprint id
	 * @param status   the status
	 * @return the total story point by status
	 */
	public Integer getTotalStoryPointByStatus(Long sprintId, IssueStatus status) {
		return sprintIssueRepository.getTotalStoryPointByStatus(sprintId, status);
	}

	/**
	 * Gets the pending by sprint.
	 *
	 * @param sprintId the sprint id
	 * @return the pending by sprint
	 */
	public List<SprintIssue> getPendingBySprint(Long sprintId) {
		return sprintIssueRepository.getPendingBySprint(sprintId);
	}
	
	@Override
	public Integer getSprintIssueByStatus(Long sprintId, IssueStatus status) {
		return sprintIssueRepository.getIssuesByStatus(sprintId, status);
	}

}
