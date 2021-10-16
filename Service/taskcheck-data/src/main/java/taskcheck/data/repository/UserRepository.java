/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.data.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import taskcheck.data.entity.User;


/**
 * The Interface UserRepository.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	@Query(value = "SELECT u FROM User u WHERE u.voided = 0")
	List<User> getAll();

	/**
	 * Gets the by id.
	 *
	 * @param id the id
	 * @return the by id
	 */
	@Query(value = "SELECT u FROM User u WHERE u.id=?1 AND u.voided = 0")
	User getById(Long id);

	/**
	 * Get by username.
	 *
	 * @param username the username
	 * @return the user
	 */
//	@Query(value = "SELECT a FROM User a WHERE a.username=?1")
	User getByUsername(String username);
}
