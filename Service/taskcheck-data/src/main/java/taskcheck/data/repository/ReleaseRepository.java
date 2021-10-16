/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import taskcheck.data.entity.Release;

/**
 * The Interface ReleaseRepository.
 */
@Repository
public interface ReleaseRepository extends JpaRepository<Release, Long> {

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	@Query(value = "SELECT r FROM Release r WHERE r.voided = 0")
	List<Release> getAll();

	/**
	 * Gets the all.
	 *
	 * @param id the id
	 * @return the all
	 */
	@Query(value = "SELECT r FROM Release r WHERE r.project.id=?1 AND r.voided = 0")
	List<Release> getAllReleaseListInProject(Long id);
	
	/**
	 * Gets the by id.
	 *
	 * @param id the id
	 * @return the by id
	 */
	@Query(value = "SELECT r FROM Release r WHERE r.project.id=:id AND r.voided = 0")
	Release getReleaseByProjectId(@Param("id") Long id);

	/**
	 * Gets the by id.
	 *
	 * @param id the id
	 * @return the by id
	 */
	@Query(value = "SELECT r FROM Release r WHERE r.id=?1 AND r.voided = 0")
	Release getById(Long id);
}
