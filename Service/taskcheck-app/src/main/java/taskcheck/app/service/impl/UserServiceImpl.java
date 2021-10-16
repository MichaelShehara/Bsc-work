/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import taskcheck.app.config.ObjectMapper;
import taskcheck.app.service.UserService;
import taskcheck.data.entity.User;
import taskcheck.data.repository.UserRepository;

/**
 * The Class UserServiceImpl.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	/** The user repository. */
	@Autowired
	private UserRepository userRepository;

	/**
	 * Creates the.
	 *
	 * @param user the user
	 * @return the user
	 */
	@Override
	public User create(User user) {

		LOGGER.debug("Saving User {}", user);

		return userRepository.save(user);
	}

	/**
	 * Update.
	 *
	 * @param user the user
	 * @return the user
	 */
	@Override
	public User update(User user) {

		LOGGER.debug("Saving User {}", user);

		 User entity = userRepository.getById(user.getId());
		 if(user.getPassword() == null) {
			 user.setPassword(entity.getPassword());
		 }

		//ObjectMapper.mapUser(user, entity);

		return userRepository.save(user);
	}

	/**
	 * Delete.
	 *
	 * @param id the id
	 */
	@Override
	public void delete(Long id) {
		User obj = userRepository.getById(id);
		obj.setVoided(Boolean.TRUE);
		userRepository.save(obj);
	}

	/**
	 * Gets the by id.
	 *
	 * @param id the id
	 * @return the by id
	 */
	@Override
	public User getById(Long id) {
		return userRepository.getById(id);
	}

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	@Override
	public List<User> getAll() {
		return userRepository.getAll();
	}

}
