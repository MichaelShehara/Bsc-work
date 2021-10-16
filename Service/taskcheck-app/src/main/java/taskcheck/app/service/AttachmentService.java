/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.service;

import java.util.List;

import taskcheck.data.entity.Attachment;

/**
 * The Interface AttachmentService.
 */
public interface AttachmentService {

	/**
	 * Creates the.
	 *
	 * @param attachment the attachment
	 * @return the attachment
	 */
	Attachment create(Attachment attachment);

	/**
	 * Gets the all.
	 *
	 * @param identifier the identifier
	 * @return the all
	 */
	List<Attachment> getAll(String identifier);
	
	/**
	 * Gets the by id.
	 *
	 * @param id the id
	 * @return the by id
	 */
	Attachment getById(Long id);
}
