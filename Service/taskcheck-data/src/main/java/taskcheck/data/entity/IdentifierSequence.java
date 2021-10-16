/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class IdentifierSequence.
 */
@Entity
@Table(name = "identifier_sequence")
@Getter
@Setter
public class IdentifierSequence extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2216159782399756377L;

	/** The sequence number. */
	@Column(name = "sequence_number")
	protected Long sequenceNumber;

	/** The project. */
	@ManyToOne
	@JoinColumn(name = "project_id", nullable = false)
	private Project project;

}
