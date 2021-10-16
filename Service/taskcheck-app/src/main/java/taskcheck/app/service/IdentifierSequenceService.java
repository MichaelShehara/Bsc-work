/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.service;

import taskcheck.data.entity.IdentifierSequence;

/**
 * The Interface IdentifierSequenceService.
 */
public interface IdentifierSequenceService {

	/**
	 * Creates the.
	 *
	 * @param sequence the sequence
	 * @return the identifier sequence
	 */
	IdentifierSequence create(IdentifierSequence sequence);

	/**
	 * Update.
	 *
	 * @param sequence the sequence
	 * @return the identifier sequence
	 */
	IdentifierSequence update(IdentifierSequence sequence);

	/**
	 * Gets the by project id.
	 *
	 * @param projectId the project id
	 * @return the by project id
	 */
	IdentifierSequence getByProjectId(Long projectId);
}
