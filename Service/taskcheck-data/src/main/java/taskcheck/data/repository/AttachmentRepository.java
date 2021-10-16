/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import taskcheck.data.entity.Attachment;

/**
 * The Interface AttachmentRepository.
 */
@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

	/**
	 * Gets the all.
	 *
	 * @param identifier the identifier
	 * @return the all
	 */
	@Query(value = "SELECT i FROM Attachment i WHERE i.identifier=?1 AND i.voided = 0 ORDER BY i.createdAt")
	List<Attachment> getAll(String identifier);
	
	/**
	 * Gets the by id.
	 *
	 * @param id the id
	 * @return the by id
	 */
	@Query(value = "SELECT i FROM Attachment i WHERE i.id=?1 AND i.voided = 0")
	Attachment getById(Long id);
}

