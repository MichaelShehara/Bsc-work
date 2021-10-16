/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import taskcheck.data.entity.Comment;

/**
 * The Interface CommentRepository.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	/**
	 * Gets the all.
	 *
	 * @param identifier the identifier
	 * @return the all
	 */
	@Query(value = "SELECT i FROM Comment i WHERE i.identifier=?1 AND i.voided = 0 ORDER BY i.createdAt")
	List<Comment> getAll(String identifier);

}
