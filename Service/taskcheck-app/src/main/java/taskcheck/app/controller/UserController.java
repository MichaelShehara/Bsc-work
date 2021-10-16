/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.controller;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import taskcheck.app.config.Constants;
import taskcheck.app.dto.AuthRequestDto;
import taskcheck.app.dto.AuthResponseDto;
import taskcheck.app.service.AuthTokenService;
import taskcheck.app.service.UserService;
import taskcheck.data.entity.User;
import taskcheck.data.repository.UserRepository;
import taskcheck.data.view.Views;

/**
 * The Class UserController.
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class UserController {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	/** The user service. */
	@Autowired
	private UserService userService;
	
	/** The user service. */
	@Autowired
	private UserRepository userRepo;

	/** The password encoder. */
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/** The auth token service. */
	@Autowired
	private AuthTokenService authTokenService;

	/** The user details service. */
	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * Creates the.
	 *
	 * @param user the user
	 * @return the response entity
	 */
	
	@PostMapping(value = "/", headers = Constants.ApiVersion.V1)
	@JsonView(Views.UserDeatils.class)
	public ResponseEntity<User> create(@Valid @RequestBody User user) {

		LOGGER.debug("Create User {}", user);

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		User obj = userService.create(user);

		return new ResponseEntity<User>(obj, HttpStatus.OK);
	}
	
	@PostMapping(value ="/create/")
	@JsonView(Views.UserDeatils.class)
	public ResponseEntity<User> createUser(@RequestBody User user) {
		
		LOGGER.debug("Create User {}", user);

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		User obj = userService.create(user);

		return new ResponseEntity<User>(obj, HttpStatus.OK);

		//return  userRepo.save(user);
	
	}

	/**
	 * Update.
	 *
	 * @param user the user
	 * @return the response entity
	 */
	@PutMapping(value = "/", headers = Constants.ApiVersion.V1)
	@JsonView(Views.Role.class)
	public ResponseEntity<User> update(@Valid @RequestBody User user) {

		LOGGER.debug("Update user", user);
		try {

			if (user.getPassword() != null) {
				user.setPassword(passwordEncoder.encode(user.getPassword()));
			}

			user = userService.update(user);

			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Delete.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@DeleteMapping(value = "/{id}", headers = Constants.ApiVersion.V1)
	public ResponseEntity<Void> delete(@PathVariable("id") @RequestParam Long id) {

		LOGGER.debug("Delete user");

		userService.delete(id);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	/**
	 * Gets the by user id.
	 *
	 * @param id the id
	 * @return the by user id
	 */
	@GetMapping(value = "/{id}", headers = Constants.ApiVersion.V1)
	@JsonView(Views.UserDeatils.class)
	public ResponseEntity<User> getByUserId(@PathVariable("id") Long id) {

		LOGGER.debug("get User {}", id);

		User obj = userService.getById(id);

		return new ResponseEntity<User>(obj, HttpStatus.OK);

	}

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	@GetMapping(value = "/", headers = Constants.ApiVersion.V1)
	@JsonView(Views.UserDeatils.class)
	public ResponseEntity<List<User>> getAll() {

		LOGGER.debug("get all user");

		List<User> obj = userService.getAll();

		return new ResponseEntity<List<User>>(obj, HttpStatus.OK);
	}
}
