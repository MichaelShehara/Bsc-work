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
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import taskcheck.data.entity.Epic;
import taskcheck.data.entity.Story;

/**
 * The Interface EpicRepository.
 */
@Repository
public interface EpicRepository extends JpaRepository<Epic, Long> {

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	@Query(value = "SELECT e FROM Epic e WHERE e.voided = 0")
	List<Epic> getAll();

	/**
	 * Gets the by id.
	 *
	 * @param id the id
	 * @param pageable the pageable
	 * @return the by id
	 */
	@Query(value = "select s FROM Story s Where s.epic.id=:id AND s.epic.voided=0")
	Page<Story> getStoryByEpicid(@Param("id") Long id, Pageable pageable);
	
	/**
	 * Gets the by id.
	 *
	 * @param id the id
	 * @return the by id
	 */
	@Query(value = "SELECT e FROM Epic e WHERE e.id=?1 AND e.voided = 0")
	Epic getById(Long id);
	
	/**
	 * Gets the by id with Pagination.
	 *
	 * @param id the id
	 * @param page the page
	 * @return the by id
	 */
	@Query(value = "SELECT e FROM Epic e WHERE e.project.id=?1 AND e.voided = 0")
	Page<Epic> getEpicByProjectId(Long id, Pageable page);

}
