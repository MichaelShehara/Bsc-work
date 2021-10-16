/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.data.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;
import taskcheck.data.enums.Priority;
import taskcheck.data.enums.Severity;
import taskcheck.data.enums.TaskStatus;
import taskcheck.data.enums.TaskType;
import taskcheck.data.view.Views;

/**
 * The Class Task.
 */
@Entity
@Table(name = "task")
@Getter
@Setter
public class Task extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 0;

	/** The identifier. */
	@NotNull
	@Column(name = "identifier", unique = true, updatable = false)
	@JsonView(Views.Task.class)
	private String identifier;

	/** The summary *. */
	@Column(name = "summary")
	@JsonView(Views.Task.class)
	private String summary;

	/** The description *. */
	@Column(name = "description")
	@JsonView(Views.Task.class)
	private String description;

	/** The Type *. */
	@NotNull
	@Column(name = "type")
	@JsonView(Views.Task.class)
	private TaskType type;

	/** The priority *. */
	@Column(name = "priority")
	@JsonView(Views.Task.class)
	private Priority priority;

	/** The severity *. */
	@Column(name = "severity")
	@JsonView(Views.Task.class)
	private Severity severity;

	/** The status *. */
	@Column(name = "status")
	@JsonView(Views.Task.class)
	private TaskStatus status;

	/** The story point *. */
	@Column(name = "story_point")
	@JsonView(Views.Task.class)
	private int storyPoint;

	/** The completed time *. */
	@NotNull
	@Column(name = "completed_at")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonView(Views.Task.class)
	private Date completedAt;

	/** The impacted area. */
	@Column(name = "impacted_area")
	@JsonView(Views.Task.class)
	private String impactedArea;

	/** The story id *. */
	@ManyToOne
	@JsonView(Views.Story.class)
	@JoinColumn(name = "story_id", nullable = false)
	private Story story;

	/** the owner id *. */
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonView(Views.User.class)
	@JoinColumn(name = "owned_by")
	private User assignee;

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Task [identifier=" + identifier + ", summary=" + summary + ", description=" + description + ", type="
				+ type + ", priority=" + priority + ", severity=" + severity + ", status=" + status + ", storyPoint="
				+ storyPoint + ", completedAt=" + completedAt + ", impactedArea=" + impactedArea + ", story=" + story
				+ ", assignee=" + assignee + "]";
	}

}
