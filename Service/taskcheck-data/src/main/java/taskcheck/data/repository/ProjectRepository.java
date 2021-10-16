/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.data.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import taskcheck.data.entity.Project;

/**
 * The Interface ProjectRepository.
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	@Query(value = "SELECT a FROM Project a WHERE a.voided = 0")
	List<Project> getAll();

	/**
	 * Gets the all.
	 *
	 * @param page the page
	 * @return the all
	 */
	@Query(value = "SELECT a FROM Project a WHERE a.voided = 0")
	Page<Project> getAll(Pageable page);

	/**
	 * Gets the by id.
	 *
	 * @param id the id
	 * @return the by id
	 */
	@Query(value = "SELECT a FROM Project a WHERE a.id=?1 AND a.voided = 0")
	Project getById(Long id);

	/**
	 * Gets the by id.
	 *
	 * @param id   the id
	 * @param page the page
	 * @return the by id
	 */
	@Query(value = "SELECT a FROM Project a WHERE a.id=?1 AND a.voided = 0")
	Page<Project> getById(Long id, Pageable page);

}
