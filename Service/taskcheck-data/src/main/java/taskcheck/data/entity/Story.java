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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;
import taskcheck.data.enums.Priority;
import taskcheck.data.enums.StoryStatus;
import taskcheck.data.enums.StoryType;
import taskcheck.data.view.Views;

/**
 * The Class Story.
 */
@Entity
@Table(name = "story")
@Getter
@Setter
public class Story extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8040010924922593188L;
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@JsonView(Views.Story.class)
	protected Long id;
	
	/** The identifier. */
	@NotNull
	@Column(name = "identifier", unique = true, updatable = false)
	@JsonView(Views.Story.class)
	private String identifier;

	/** The summary *. */
	@Column(name = "summary")
	@JsonView(Views.Story.class)
	private String summary;

	/** The description *. */
	@Column(name = "description")
	@JsonView(Views.Story.class)
	private String description;

	/** The type *. */
	@Column(name = "type")
	@JsonView(Views.Story.class)
	private StoryType type;

	/** The status *. */
	@NotNull
	@Column(name = "status")
	@JsonView(Views.Story.class)
	private StoryStatus status;

	/** The priority *. */
	@NotNull
	@Column(name = "priority")
	@JsonView(Views.Story.class)
	private Priority priority;

	/** The owned user *. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonView(Views.Story.class)
	@JoinColumn(name = "owned_by", nullable = false)
	private User assignee;

	/** The Epic ID *. */
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonView(Views.Epic.class)
	@JoinColumn(name = "epic_id", nullable = false, updatable = false)
	private Epic epic;

	/** The task. */
//	@JsonView(Views.Task.class)
	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.REFRESH, CascadeType.DETACH,
			CascadeType.MERGE }, mappedBy = "story")
	private Set<Task> task = new HashSet<>();

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Story [identifier=" + identifier + ", summary=" + summary + ", description=" + description + ", status="
				+ status + ", priority=" + priority + ", assignee=" + "" + ", epic=" + epic + "]";
	}

}
