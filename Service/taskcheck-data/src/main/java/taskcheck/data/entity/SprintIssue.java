/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.data.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class SprintIssue.
 */
@Entity
@Table(name = "sprint_issue")
@Getter
@Setter
public class SprintIssue extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8788591911859900414L;

	/** The sprint. */
	@ManyToOne
	@JoinColumn(name = "sprint_id", nullable = false)
	private Sprint sprint;

	/** The issue. */
	@ManyToOne
	@JoinColumn(name = "issue_id", nullable = false)
	private Issue issue;

}
