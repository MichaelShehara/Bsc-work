/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import taskcheck.app.service.ReleaseService;
import taskcheck.data.entity.Release;
import taskcheck.data.repository.ReleaseRepository;

/**
 * The Class ReleaseServiceImpl.
 */
@Service
@Transactional
public class ReleaseServiceImpl implements ReleaseService {

	/** The release repository. */
	@Autowired
	private ReleaseRepository releaseRepository;

	/**
	 * Creates the.
	 *
	 * @param release the release
	 * @return the release
	 */
	@Override
	public Release create(Release release) {
		Release obj = releaseRepository.save(release);
		return obj;
	}

	/**
	 * Update.
	 *
	 * @param release the release
	 * @return the release
	 */
	@Override
	public Release update(Release release) {

		return releaseRepository.save(release);
	}

	/**
	 * Delete.
	 *
	 * @param id the id
	 */
	@Override
	public void delete(Long id) {
		Release obj = releaseRepository.getById(id);
		obj.setVoided(Boolean.TRUE);
		releaseRepository.save(obj);

	}

	/**
	 * Gets the by id.
	 *
	 * @param id the id
	 * @return the by id
	 */
	@Override
	public Release getById(Long id) {
		return releaseRepository.getById(id);
	}
	
	/**
	 * Gets the by projectId.
	 *
	 * @param id the id
	 * @return the by projectId
	 */
	@Override
	public Release getReleaseByProjectId(Long id) {
		return releaseRepository.getReleaseByProjectId(id);
	}

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	@Override
	public List<Release> getAll() {

		return releaseRepository.getAll();
	}

	/**
	 * Gets the all in project.
	 *
	 * @param projectId the project id
	 * @return the all
	 */
	@Override
	public List<Release> getAllReleaseListInProject(Long projectId) {

		return releaseRepository.getAllReleaseListInProject(projectId);
	}
}
