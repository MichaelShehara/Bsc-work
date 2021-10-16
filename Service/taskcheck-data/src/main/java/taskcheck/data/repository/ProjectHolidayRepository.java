/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.data.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import taskcheck.data.entity.ProjectHoliday;

/**
 * The Interface ProjectHolidayRepository.
 */
@Repository
public interface ProjectHolidayRepository extends JpaRepository<ProjectHoliday, Long> {

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	@Query(value = "SELECT a FROM ProjectHoliday a WHERE a.voided = 0")
	List<ProjectHoliday> getAll();

	/**
	 * Gets the all by project.
	 *
	 * @param projectId the project id
	 * @return the all by project
	 */
	@Query(value = "SELECT a FROM ProjectHoliday a WHERE a.project.id=?1 AND a.voided = 0 AND a.holiday >= CURRENT_DATE")
	List<ProjectHoliday> getFutureHolidayByProject(Long projectId);
	
	/**
	 * Gets the holidays by project.
	 *
	 * @param projectId the project id
	 * @param start the start
	 * @param end the end
	 * @return the holidays by project
	 */
	@Query(value = "SELECT a FROM ProjectHoliday a WHERE a.project.id=?1 AND a.voided = 0 AND a.holiday >= ?2 AND a.holiday <= ?3")
	List<ProjectHoliday> getHolidaysByProject(Long projectId, Date start, Date end);

	/**
	 * Gets the by id.
	 *
	 * @param id the id
	 * @return the by id
	 */
	@Query(value = "SELECT a FROM ProjectHoliday a WHERE a.id=?1 AND a.voided = 0")
	ProjectHoliday getById(Long id);

}
