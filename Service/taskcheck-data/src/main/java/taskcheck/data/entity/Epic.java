/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.data.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;
import taskcheck.data.enums.EpicStatus;
import taskcheck.data.enums.Priority;
import taskcheck.data.view.Views;

/**
 * The Class Epic.
 */
@Entity
@Table(name = "epic")
@Getter
@Setter
public class Epic extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8040010924922593188L;

	/** The identifier *. */
	@NotNull
	@Column(name = "identifier", unique = true, updatable = false)
	@JsonView(Views.Epic.class)
	private String identifier;

	/** The name *. */
	@NotNull
	@Column(name = "name")
	@JsonView(Views.Epic.class)
	private String name;

	/** The summary *. */
	@Column(name = "summary")
	@JsonView(Views.Epic.class)
	private String summary;

	/** The description *. */
	@Column(name = "description")
	@JsonView(Views.Epic.class)
	private String description;

	/** The status *. */
	@NotNull
	@Column(name = "status")
	@JsonView(Views.Epic.class)
	private EpicStatus status;

	/** The priority *. */
	@NotNull
	@Column(name = "priority")
	@JsonView(Views.Epic.class)
	private Priority priority;

	/** the owner id *. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonView(Views.User.class)
	@JoinColumn(name = "owned_by", nullable = false)
	private User assignee;

	/** The project. */
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE })
	// @JsonView(Views.Project.class)
	@JoinColumn(name = "project_id", nullable = false, updatable = false)
	private Project project;
	
	/** The stories. */
	@JsonView(Views.StoryDetails.class)
	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.REFRESH, CascadeType.DETACH,
			CascadeType.MERGE }, mappedBy = "epic")
	private Set<Story> stories = new HashSet<>();

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Epic [identifier=" + identifier + ", name=" + name + ", summary=" + summary + ", description="
				+ description + ", status=" + status + ", priority=" + priority + ", assignee=" + assignee + "]";
	}

}