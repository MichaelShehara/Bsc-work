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
import taskcheck.app.service.CommentService;
import taskcheck.data.entity.Comment;
import taskcheck.data.repository.CommentRepository;

/**
 * The Class CommentServiceImpl.
 */
@Service
public class CommentServiceImpl implements CommentService {

	/** The task comment repository. */
	@Autowired
	private CommentRepository commentRepository;

	
	/**
	 * Creates the.
	 *
	 * @param comment the comment
	 * @return the comment
	 */
	@Override
	public Comment create(Comment comment) {
		
		Date now = new Date();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		AuthUser user = (AuthUser) auth.getPrincipal();
		comment.setCreatedBy(user.getUser());
		comment.setCreatedAt(now);
		comment.setUpdatedBy(user.getUser());
		comment.setUpdatedAt(now);
		
		return commentRepository.save(comment);
	}


	/**
	 * Gets the all.
	 *
	 * @param identifier the identifier
	 * @return the all
	 */
	@Override
	public List<Comment> getAll(String identifier) {
		return commentRepository.getAll(identifier);
	}

}
