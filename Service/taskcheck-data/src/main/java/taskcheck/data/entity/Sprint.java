/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.data.entity;

import java.util.Date;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;
import taskcheck.data.enums.SprintStatus;
import taskcheck.data.view.Views;

/**
 * The Class Sprint.
 */
@Entity
@Table(name = "sprint")
@Getter
@Setter
public class Sprint extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8040010924922593188L;

	/** The name *. */
	@NotNull
	@Column(name = "name")
	@JsonView(Views.Sprint.class)
	private String name;

	/** The status *. */
	@NotNull
	@Column(name = "status")
	@JsonView(Views.Sprint.class)
	private SprintStatus status;

	/** The start date *. */
	@NotNull
	@Column(name = "start_at")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonView(Views.Sprint.class)
	private Date startAt;

	/** The end Date *. */
	@NotNull
	@Column(name = "end_at")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonView(Views.Sprint.class)
	private Date endAt;

	/** The goals *. */
	@Column(name = "goals")
	@JsonView(Views.Sprint.class)
	private String goals;

	/** the Project ID *. */
	@ManyToOne
	@JoinColumn(name = "project_id", nullable = false)
	private Project project;

	/** The tasks. */
	@JsonView(Views.ProjectUser.class)
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "sprint_task", joinColumns = { @JoinColumn(name = "sprint_id") }, inverseJoinColumns = {
			@JoinColumn(name = "task_id") })
	private Set<Task> tasks = new HashSet<>();

	/** The issue. */
	@JsonView(Views.ProjectUser.class)
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "sprint_issue", joinColumns = { @JoinColumn(name = "sprint_id") }, inverseJoinColumns = {
			@JoinColumn(name = "issue_id") })
	private Set<Issue> issue = new HashSet<>();

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Sprint [name=" + name + ", status=" + status + ", startAt=" + startAt + ", endAt=" + endAt + ", goals="
				+ goals + ", project=" + project + "]";
	}

}
