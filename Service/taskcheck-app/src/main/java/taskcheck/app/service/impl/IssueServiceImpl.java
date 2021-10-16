/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import taskcheck.app.config.PageConverter;
import taskcheck.app.service.IssueService;
import taskcheck.data.entity.IdentifierSequence;
import taskcheck.data.entity.Issue;
import taskcheck.data.entity.PageView;
import taskcheck.data.entity.Project;
import taskcheck.data.entity.Release;
import taskcheck.data.entity.User;
import taskcheck.data.repository.IdentifierSequenceRepository;
import taskcheck.data.repository.IssueRepository;
import taskcheck.data.repository.ReleaseRepository;
import taskcheck.data.repository.UserRepository;
import taskcheck.data.repository.IssueRepository.ReleaseIssueCount;

/**
 * The Class IssueServiceImpl.
 */
@Service
@Transactional
public class IssueServiceImpl implements IssueService {

	/** The page converter. */
	@Autowired
	private PageConverter pageConverter;

	/** The issue repository. */
	@Autowired
	private IssueRepository issueRepository;

	/** The user repository. */
	@Autowired
	private UserRepository userRepository;

	/** The release repository. */
	@Autowired
	private ReleaseRepository releaseRepository;

	/** The sequence repository. */
	@Autowired
	private IdentifierSequenceRepository sequenceRepository;

	/**
	 * Creates the.
	 *
	 * @param issue the issue
	 * @return the issue
	 */
	@Override
	public Issue create(Issue issue) {

		Project project = issue.getProject();
		IdentifierSequence sequence = sequenceRepository.getByProjectId(project.getId());
		Long number = sequence.getSequenceNumber();

		long next = number + 1;

		String identifier = project.getCode() + "-" + next;
		issue.setIdentifier(identifier);

		sequence.setSequenceNumber(next);
		sequenceRepository.save(sequence);

		return issueRepository.save(issue);
	}

	/**
	 * Update.
	 *
	 * @param issue the issue
	 * @return the issue
	 */
	@Override
	public Issue update(Issue issue) {
		// Issue entity = issueRepository.getById(issue.getId());

		if (issue.getAssignee() != null) {
			User assignee = userRepository.getById(issue.getAssignee().getId());
			issue.setAssignee(assignee);
		}

		if (issue.getResolvedBy() != null) {
			User user = userRepository.getById(issue.getResolvedBy().getId());
			issue.setResolvedBy(user);
		}

		if (issue.getVerifiedBy() != null) {
			User user = userRepository.getById(issue.getVerifiedBy().getId());
			issue.setVerifiedBy(user);
		}

		if (issue.getAffectVersion() != null) {
			Release release = releaseRepository.getById(issue.getAffectVersion().getId());
			issue.setAffectVersion(release);
		}

		if (issue.getFixVersion() != null) {
			Release release = releaseRepository.getById(issue.getFixVersion().getId());
			issue.setFixVersion(release);
		}

		// ObjectMapper.mapIssue(issue, entity);

		return issueRepository.save(issue);
	}

	/**
	 * Delete.
	 *
	 * @param id the id
	 */
	@Override
	public void delete(Long id) {
		Issue obj = issueRepository.getById(id);
		obj.setVoided(Boolean.TRUE);
		issueRepository.save(obj);
	}

	/**
	 * Gets the by id.
	 *
	 * @param id the id
	 * @return the by id
	 */
	@Override
	public Issue getById(Long id) {
		return issueRepository.getById(id);
	}

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	@Override
	public List<Issue> getAll() {
		return issueRepository.getAll();
	}

	/**
	 * Gets the by id with pagination.
	 *
	 * @param id   the id
	 * @param page the page
	 * @return the by id
	 */
	@Override
	public PageView getIssueByProjectId(Long id, Pageable page) {
		return pageConverter.handlePageResponse(issueRepository.getIssueByProjectId(id, page));
	}

	/**
	 * Gets the backlog issues by project.
	 *
	 * @param projectId the project id
	 * @return the backlog issues by project
	 */
	public List<Issue> getBacklogIssuesByProject(Long projectId) {
		return issueRepository.getBacklogIssuesByProject(projectId);
	}

	/**
	 * Gets the release issue count by project.
	 *
	 * @param projectId the project id
	 * @return the release issue count by project
	 */
	public List<ReleaseIssueCount> getReleaseIssueCountByProject(Long projectId) {
		return issueRepository.getReleaseIssueCountByProject(projectId);
	}
}
