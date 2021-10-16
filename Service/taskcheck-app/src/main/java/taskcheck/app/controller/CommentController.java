/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import taskcheck.app.config.Constants;
import taskcheck.app.service.CommentService;
import taskcheck.data.entity.Comment;
import taskcheck.data.view.Views;

/**
 * The Class CommentController.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/comments")
public class CommentController {

	/** The comment service. */
	@Autowired
	private CommentService commentService;

	/**
	 * All issues.
	 *
	 * @param identifier the identifier
	 * @return the response entity
	 */
	@GetMapping(value = "/{identifier}", headers = Constants.ApiVersion.V1)
	@JsonView(Views.Comment.class)
	public ResponseEntity<List<Comment>> getComments(@PathVariable("identifier") String identifier) {

		List<Comment> objs = commentService.getAll(identifier);

		return new ResponseEntity<List<Comment>>(objs, HttpStatus.OK);
	}
}
