/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import taskcheck.app.dto.AuthUser;
import taskcheck.app.service.AttachmentService;
import taskcheck.data.entity.Attachment;
import taskcheck.data.repository.AttachmentRepository;



/**
 * The Class AttachmentServiceImpl.
 */
@Service
public class AttachmentServiceImpl implements AttachmentService {

	/** The attachment repository. */
	@Autowired
	private AttachmentRepository attachmentRepository;

	/**
	 * Creates the.
	 *
	 * @param attachment the attachment
	 * @return the attachment
	 */
	@Override
	public Attachment create(Attachment attachment) {
		
		Date now = new Date();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		AuthUser user = (AuthUser) auth.getPrincipal();
		attachment.setCreatedBy(user.getUser());
		attachment.setCreatedAt(now);
		attachment.setUpdatedBy(user.getUser());
		attachment.setUpdatedAt(now);
		
		return attachmentRepository.save(attachment);
	}

	/**
	 * Gets the all.
	 *
	 * @param identifier the identifier
	 * @return the all
	 */
	@Override
	public List<Attachment> getAll(String identifier) {
		return attachmentRepository.getAll(identifier);
	}
	
	/**
	 * Gets the by id.
	 *
	 * @param id the id
	 * @return the by id
	 */
	public Attachment getById(Long id) {
		return attachmentRepository.getById(id);
	}

}