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

import taskcheck.data.entity.Task;

/**
 * The Interface TaskRepository.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	@Query(value = "SELECT t FROM Task t WHERE t.voided = 0")
	List<Task> getAll();

	/**
	 * Gets the by id.
	 *
	 * @param id the id
	 * @return the by id
	 */
	@Query(value = "SELECT t FROM Task t WHERE t.id=?1 AND t.voided = 0")
	Task getById(Long id);

	/**
	 * Gets the by id with Pagination.
	 *
	 * @param projectId the project id
	 * @return the by id
	 */
	@Query(value = "SELECT t FROM Task t INNER JOIN t.story s INNER JOIN s.epic e INNER JOIN e.project p WHERE p.id=?1 AND t.voided = 0")
	List<Task> getAllTasksByProject(Long projectId);

	/**
	 * Gets the backlog tasks by project.
	 *
	 * @param projectId the project id
	 * @return the backlog tasks by project
	 */
	@Query(value = "SELECT * FROM task t INNER JOIN story s on t.story_id = s.id "
			+ "INNER JOIN epic e ON s.epic_id = e.id INNER JOIN project p ON e.project_id = p.id "
			+ "WHERE t.voided = 0 AND p.id=?1 AND t.id NOT IN (SELECT st.task_id FROM sprint_task st)", nativeQuery = true)
	List<Task> getBacklogTasksByProject(Long projectId);
	
	/**
	 * Gets the by id with Pagination.
	 *
	 * @param projectId the project id
	 * @param page the page
	 * @return the by id
	 */
	@Query(value = "SELECT t FROM Task t INNER JOIN t.story s INNER JOIN s.epic e INNER JOIN e.project p WHERE p.id=?1 AND t.voided = 0")
	Page<Task> getTasksByProject(Long projectId, Pageable page);
	
}
