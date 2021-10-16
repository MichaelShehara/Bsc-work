/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */

package taskcheck.data.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;
import taskcheck.data.enums.ProjectStatus;
import taskcheck.data.enums.ProjectWorkWeek;
import taskcheck.data.view.Views;

/**
 * The Class Project.
 */
@Entity
@Table(name = "project")
@Getter
@Setter
public class Project extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8040010924922593188L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@JsonView(Views.Project.class)
	protected Long id;

	/** The code. */
	@NotNull
	@Size(min = 2, max = 4, message = "Code should contain 4 Letters")
	@NotBlank(message = "A Code for the project is Mandatory")
	@Column(name = "code", unique = true, updatable = false)
	@JsonView(Views.Project.class)
	private String code;

	/** The name. */
	@NotNull
	@NotBlank(message = "A Name for a project is Mandatory")
	@Size(min = 2, max = 30, message = "Name of the project must exceed 30 chractors")
	@Column(name = "name")
	@JsonView(Views.Project.class)
	private String name;

	/** The status. */
	@NotNull
	@Column(name = "status")
	@JsonView(Views.Project.class)
	private ProjectStatus status;

	/** The leader name. */
	@NotBlank(message = "It's Mandatory to assign a leader to the project")
	@Column(name = "leader_name")
	@JsonView(Views.Project.class)
	private String leaderName;

	/** The work week. */
	@Column(name = "work_week")
	@JsonView(Views.Project.class)
	private ProjectWorkWeek workWeek;

	/** The users.(Project User Mapping) */
	@JsonView(Views.ProjectUser.class)
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "project_user_mapping", joinColumns = { @JoinColumn(name = "project_id") }, inverseJoinColumns = {
			@JoinColumn(name = "user_id") })
	private Set<User> users = new HashSet<>();

	/** The sprints. */
	@JsonView(Views.Sprint.class)
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "project")
	private Set<Sprint> sprints = new HashSet<>();

	/** The epics. */
	@JsonView(Views.Epic.class)
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "project")
	private Set<Epic> epics = new HashSet<>();

	/** The issues. */
	@JsonView(Views.Issue.class)
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "project")
	private Set<Issue> issues = new HashSet<>();

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Project [code=" + code + ", name=" + name + ", status=" + status + ", leaderName=" + leaderName
				+ ", users=" + users + "]";
	}

}
