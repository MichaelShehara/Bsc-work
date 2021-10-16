/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import taskcheck.data.entity.User;

/**
 * The Interface UserService.
 */
@Service
@Transactional
public interface UserService {

	/**
	 * Creates the.
	 *
	 * @param user the user
	 * @return the user
	 */
	User create(User user);

	/**
	 * Update.
	 *
	 * @param user the user
	 * @return the user
	 */
	User update(User user);

	/**
	 * Delete.
	 *
	 * @param id the id
	 */
	public void delete(Long id);

	/**
	 * Gets the by id.
	 *
	 * @param id the id
	 * @return the by id
	 */
	public User getById(Long id);

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	List<User> getAll();

}
