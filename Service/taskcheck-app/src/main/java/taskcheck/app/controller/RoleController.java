/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import taskcheck.app.config.Constants;
import taskcheck.app.service.RoleService;
import taskcheck.data.entity.Role;
import taskcheck.data.view.Views;

/**
 * The Class RoleController.
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/roles")
public class RoleController {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

	/** The user service. */
	@Autowired
	private RoleService roleService;

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	@GetMapping(value = "/", headers = Constants.ApiVersion.V1)
	@JsonView(Views.Role.class)
	public ResponseEntity<List<Role>> getAll() {

		LOGGER.debug("get all role");

		List<Role> obj = roleService.getAll();

		return new ResponseEntity<List<Role>>(obj, HttpStatus.OK);
	}

}
