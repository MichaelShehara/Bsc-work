/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.data.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import taskcheck.data.entity.Issue;

/**
 * The Interface IssueRepository.
 */
@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	@Query(value = "SELECT i FROM Issue i WHERE i.voided = 0")
	List<Issue> getAll();

	/**
	 * Gets the by id.
	 *
	 * @param id the id
	 * @return the by id
	 */
	@Query(value = "SELECT i FROM Issue i WHERE i.id=?1 AND i.voided = 0")
	Issue getById(Long id);
	
	
	/**
	 * Gets the by id with Pagination.
	 *
	 * @param id the id
	 * @param page the page
	 * @return the by id
	 */
	@Query(value = "SELECT i FROM Issue i WHERE i.project.id=?1 AND i.voided = 0")
	Page<Issue> getIssueByProjectId(Long id, Pageable page);
	
	/**
	 * Gets the backlog issues by project.
	 *
	 * @param projectId the project id
	 * @return the backlog issues by project
	 */
	@Query(value = "SELECT * FROM issue i INNER JOIN project p ON i.project_id = p.id "
			+ "WHERE i.voided = 0 AND p.id=?1 AND i.id NOT IN (SELECT si.issue_id FROM sprint_issue si)", nativeQuery = true)
	List<Issue> getBacklogIssuesByProject(Long projectId);
	
	/**
	 * Gets the release issue count by project.
	 *
	 * @param projectId the project id
	 * @return the release issue count by project
	 */
	@Query(value = "SELECT COUNT(*) AS issueCount, pr.name AS releaseName FROM issue i "
			+ "INNER JOIN project_release pr ON i.affect_release_id = pr.id WHERE pr.project_id = ?1 "
			+ "GROUP BY i.affect_release_id ORDER BY pr.updated_at DESC LIMIT 10", nativeQuery = true)
	List<ReleaseIssueCount> getReleaseIssueCountByProject(Long projectId);
	
	/**
	 * The Interface ReleaseIssueCount.
	 */
	public interface ReleaseIssueCount {

		/**
		 * Gets the issue count.
		 *
		 * @return the issue count
		 */
		int getIssueCount();

		/**
		 * Gets the release name.
		 *
		 * @return the release name
		 */
		String getReleaseName();

	}
}
