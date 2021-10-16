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
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;
import taskcheck.data.enums.SprintLogAction;
import taskcheck.data.view.Views;

/**
 * The Class SprintLog.
 */
@Entity
@Table(name = "sprint_log")
@Getter
@Setter
public class SprintLog extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8946230746486612824L;

	/** The action. */
	@NotNull
	@Column(name = "action")
	@JsonView(Views.SprintLog.class)
	private SprintLogAction action;

	/** The description. */
	@NotNull
	@Column(name = "description")
	@JsonView(Views.SprintLog.class)
	private String description;

	/** The story point. */
	@Column(name = "story_point")
	@JsonView(Views.SprintLog.class)
	private int storyPoint;
	
	/** The balance story point. */
	@Column(name = "balance_story_point")
	@JsonView(Views.SprintLog.class)
	private int balanceStoryPoint;

	/** The sprint. */
	@ManyToOne
	@JoinColumn(name = "sprint_id", nullable = false)
	@JsonView(Views.Sprint.class)
	private Sprint sprint;
}
