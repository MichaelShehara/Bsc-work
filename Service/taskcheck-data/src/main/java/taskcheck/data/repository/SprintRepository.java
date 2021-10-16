/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.data.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import taskcheck.data.entity.Sprint;

/**
 * The Interface SprintRepository.
 */
@Repository
public interface SprintRepository extends JpaRepository<Sprint, Long> {

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	@Query(value = "SELECT s FROM Sprint s WHERE s.voided = 0")
	List<Sprint> getAll();

	/**
	 * Gets the by id.
	 *
	 * @param id the id
	 * @return the by id
	 */
	@Query(value = "SELECT s FROM Sprint s WHERE s.id=?1 AND s.voided = 0")
	Sprint getById(Long id);

	/**
	 * Gets the active sprints.
	 *
	 * @param projectId the project id
	 * @return the active sprints
	 */
	@Query(value = "SELECT s FROM Sprint s WHERE s.project.id=?1 AND s.status=1 AND s.voided = 0")
	List<Sprint> getActiveSprints(Long projectId);

	/**
	 * Gets the by id with Pagination.
	 *
	 * @param id   the id
	 * @param page the page
	 * @return the by id
	 */
	@Query(value = "SELECT s FROM Sprint s WHERE s.project.id=?1 AND s.voided = 0")
	Page<Sprint> getSprintByProjectId(Long id, Pageable page);

	/**
	 * Gets the sprint length.
	 *
	 * @param sprintId the sprint id
	 * @return the sprint length
	 */
	@Query(value = "SELECT (CASE WHEN pr.work_week = 0 THEN ((DATEDIFF(s.end_at, s.start_at) + 1) - ((WEEK(s.end_at) - WEEK(s.start_at)) * 2) - (CASE WHEN WEEKDAY(s.end_at) = 6 THEN 1 ELSE 0 END) - (CASE WHEN WEEKDAY(s.start_at) = 5 THEN 1 ELSE 0 END) - (SELECT COUNT(*) FROM project_holiday p WHERE p.project_id = s.project_id AND p.holiday BETWEEN s.start_at AND s.end_at)) ELSE ((DATEDIFF(s.end_at, s.start_at) + 1) - (SELECT COUNT(*) FROM project_holiday p WHERE p.project_id = s.project_id AND p.holiday BETWEEN s.start_at AND s.end_at)) END ) FROM sprint s INNER JOIN project pr ON pr.id = s.project_id WHERE s.id =?1", nativeQuery = true)
	int getSprintLength(Long sprintId);

	/**
	 * Gets the sprint dates.
	 *
	 * @param projectId the project id
	 * @param sprintStart the sprint start
	 * @param sprintEnd the sprint end
	 * @return the sprint dates
	 */
	@Query(value = "SELECT (UNIX_TIMESTAMP(reportDate)*1000) AS timeMills, (CASE WHEN WEEKDAY(reportDate) = 5 OR WEEKDAY(reportDate) = 6 THEN 0 ELSE (CASE WHEN (reportDate IN (SELECT p.holiday FROM project_holiday p WHERE p.project_id = ?1 )) THEN 0 ELSE 1 END) END) AS workingDay "
			+ " FROM (SELECT ADDDATE('1970-01-01',t4.i*10000 + t3.i*1000 + t2.i*100 + t1.i*10 + t0.i) reportDate FROM "
			+ " (SELECT 0 i UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) t0,"
			+ " (SELECT 0 i UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) t1,"
			+ " (SELECT 0 i UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) t2,"
			+ " (SELECT 0 i UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) t3,"
			+ " (SELECT 0 i UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) t4) v"
			+ " WHERE CAST(reportDate AS DATE) BETWEEN ?2 AND ?3", nativeQuery = true)
	List<SprintDay> getSprintDates(Long projectId, Date sprintStart, Date sprintEnd);

	/**
	 * The Interface SprintDay.
	 */
	public interface SprintDay {

		/**
		 * Gets the time mills.
		 *
		 * @return the time mills
		 */
		long getTimeMills();

		/**
		 * Gets the working day.
		 *
		 * @return the working day
		 */
		int getWorkingDay();

	}
}
