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

import taskcheck.data.entity.SprintLog;
import taskcheck.data.enums.SprintLogAction;

/**
 * The Interface SprintLogRepository.
 */
@Repository
public interface SprintLogRepository extends JpaRepository<SprintLog, Long> {

	/**
	 * Gets the all.
	 *
	 * @param id the id
	 * @return the all
	 */
	@Query(value = "SELECT s FROM SprintLog s WHERE s.sprint.id=?1 AND s.voided = 0")
	List<SprintLog> getAllBySprint(Long id);

	/**
	 * Gets the by id.
	 *
	 * @param id the id
	 * @return the by id
	 */
	@Query(value = "SELECT s FROM SprintLog s WHERE s.id=?1 AND s.voided = 0")
	SprintLog getById(Long id);

	/**
	 * Gets the by id with Pagination.
	 *
	 * @param id   the id
	 * @param page the page
	 * @return the by id
	 */
	@Query(value = "SELECT s FROM SprintLog s WHERE s.sprint.id=?1 AND s.voided = 0")
	Page<SprintLog> getSprinLogBySprintId(Long id, Pageable page);

	/**
	 * Gets the last log by sprint id.
	 *
	 * @param id the id
	 * @param page the page
	 * @return the last log by sprint id
	 */
	@Query(value = "SELECT s FROM SprintLog s WHERE s.sprint.id=?1 AND s.voided = 0 ORDER BY s.createdBy DESC")
	List<SprintLog> getLastLogBySprintId(Long id, Pageable page);
	
	/**
	 * Gets the sprin log by project id and action.
	 *
	 * @param id the id
	 * @param action the action
	 * @param page the page
	 * @return the sprin log by project id and action
	 */
	@Query(value = "SELECT s FROM SprintLog s INNER JOIN s.sprint sp INNER JOIN sp.project p WHERE p.id=?1 AND s.action=?2 AND s.voided = 0 ORDER BY s.createdBy DESC")
	List<SprintLog> getSprinLogByProjectIdAndAction(Long id, SprintLogAction action, Pageable page);

}