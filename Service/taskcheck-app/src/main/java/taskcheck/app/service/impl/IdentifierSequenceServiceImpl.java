/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import taskcheck.app.service.IdentifierSequenceService;
import taskcheck.data.entity.IdentifierSequence;
import taskcheck.data.repository.IdentifierSequenceRepository;

/**
 * The Class IdentifierSequenceServiceImpl.
 */
@Service
@Transactional
public class IdentifierSequenceServiceImpl implements IdentifierSequenceService {

	/** The identifier sequence repository. */
	@Autowired
	private IdentifierSequenceRepository identifierSequenceRepository;

	/**
	 * Creates the.
	 *
	 * @param sequence the sequence
	 * @return the identifier sequence
	 */
	@Override
	public IdentifierSequence create(IdentifierSequence sequence) {
		return identifierSequenceRepository.save(sequence);
	}

	/**
	 * Update.
	 *
	 * @param sequence the sequence
	 * @return the identifier sequence
	 */
	@Override
	public IdentifierSequence update(IdentifierSequence sequence) {
		return identifierSequenceRepository.save(sequence);
	}

	/**
	 * Gets the by project id.
	 *
	 * @param projectId the project id
	 * @return the by project id
	 */
	@Override
	public IdentifierSequence getByProjectId(Long projectId) {
		return identifierSequenceRepository.getByProjectId(projectId);
	}

}
