/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import taskcheck.data.entity.IdentifierSequence;

/**
 * The Interface IdentifierSequenceRepository.
 */
@Repository
public interface IdentifierSequenceRepository extends JpaRepository<IdentifierSequence, Long> {

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	@Query(value = "SELECT a FROM IdentifierSequence a WHERE a.voided = 0")
	List<IdentifierSequence> getAll();

	/**
	 * Gets the by id.
	 *
	 * @param id the id
	 * @return the by id
	 */
	@Query(value = "SELECT a FROM IdentifierSequence a WHERE a.project.id=?1 AND a.voided = 0")
	IdentifierSequence getByProjectId(Long id);
}
