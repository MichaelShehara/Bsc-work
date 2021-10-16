/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.data.repository;

//import java.awt.print.Pageable;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import taskcheck.data.entity.Story;

/**
 * The Interface StoryRepository.
 */
@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	@Query(value = "SELECT s FROM Story s WHERE s.voided = 0")
	List<Story> getAll();

	/**
	 * Gets the by id.
	 *
	 * @param id the id
	 * @return the by id
	 */
	@Query(value = "SELECT s FROM Story s WHERE s.id=?1 AND s.voided = 0")
	Story getById(Long id);

	/**
	 * Gets the by id without Pagination.
	 *
	 * @param id the id
	 * @return the by id
	 */
	@Query(value = "SELECT s FROM Story s INNER JOIN s.epic e INNER JOIN e.project p WHERE p.id=?1 AND s.voided = 0 GROUP BY s.id")
	List<Story> getAllStoriesInProject(Long id);
	
	/**
	 * Gets the by id with Pagination.
	 *
	 * @param id the id
	 * @param page the page
	 * @return the by id
	 */
	@Query(value = "SELECT s FROM Story s INNER JOIN s.epic e INNER JOIN e.project p WHERE p.id=?1 AND s.voided = 0")
	Page<Story> getStoriesInProject(Long id, Pageable page);
}
