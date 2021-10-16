/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.service;

import java.util.Date;
import java.util.List;

import taskcheck.data.entity.ProjectHoliday;

/**
 * The Interface ProjectHolidayService.
 */
public interface ProjectHolidayService {

	/**
	 * Creates the.
	 *
	 * @param project the project
	 * @return the project
	 */
	ProjectHoliday create(ProjectHoliday project);

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
	ProjectHoliday getById(Long id);

	/**
	 * Gets the future holiday by project.
	 *
	 * @param projectId the project id
	 * @return the future holiday by project
	 */
	List<ProjectHoliday> getFutureHolidayByProject(Long projectId);
	
	/**
	 * Gets the holidays by project.
	 *
	 * @param projectId the project id
	 * @param start the start
	 * @param end the end
	 * @return the holidays by project
	 */
	List<ProjectHoliday> getHolidaysByProject(Long projectId, Date start, Date end);

}
