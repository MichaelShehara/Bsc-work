/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import taskcheck.app.service.ProjectHolidayService;
import taskcheck.data.entity.ProjectHoliday;
import taskcheck.data.repository.ProjectHolidayRepository;

/**
 * The Class ProjectHolidayServiceImpl.
 */
@Service
@Transactional
public class ProjectHolidayServiceImpl implements ProjectHolidayService {

	/** The project holiday repository. */
	@Autowired
	private ProjectHolidayRepository projectHolidayRepository;

	/**
	 * Creates the.
	 *
	 * @param project the project
	 * @return the project holiday
	 */
	@Override
	public ProjectHoliday create(ProjectHoliday project) {

		return projectHolidayRepository.save(project);

	}

	/**
	 * Delete.
	 *
	 * @param id the id
	 */
	@Override
	public void delete(Long id) {
		ProjectHoliday obj = projectHolidayRepository.getById(id);
		obj.setVoided(Boolean.TRUE);
		projectHolidayRepository.save(obj);

	}

	/**
	 * Gets the by id.
	 *
	 * @param id the id
	 * @return the by id
	 */
	@Override
	public ProjectHoliday getById(Long id) {
		return projectHolidayRepository.getById(id);
	}

	/**
	 * Gets the future holiday by project.
	 *
	 * @param projectId the project id
	 * @return the future holiday by project
	 */
	public List<ProjectHoliday> getFutureHolidayByProject(Long projectId) {
		return projectHolidayRepository.getFutureHolidayByProject(projectId);
	}

	/**
	 * Gets the holidays by project.
	 *
	 * @param projectId the project id
	 * @param start     the start
	 * @param end       the end
	 * @return the holidays by project
	 */
	public List<ProjectHoliday> getHolidaysByProject(Long projectId, Date start, Date end) {
		return projectHolidayRepository.getHolidaysByProject(projectId, start, end);
	}

}
