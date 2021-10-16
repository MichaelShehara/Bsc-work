/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import taskcheck.data.entity.GlobalProperty;

/**
 * The Interface GlobalPropertyRepository.
 */
@Repository
public interface GlobalPropertyRepository extends JpaRepository<GlobalProperty, Long> {

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	@Query(value = "SELECT a FROM GlobalProperty a WHERE a.voided = 0")
	List<GlobalProperty> getAll();

	/**
	 * Gets the by id.
	 *
	 * @param id the id
	 * @return the by id
	 */
	@Query(value = "SELECT a FROM GlobalProperty a WHERE a.id=?1 AND a.voided = 0")
	GlobalProperty getById(Long id);
}
