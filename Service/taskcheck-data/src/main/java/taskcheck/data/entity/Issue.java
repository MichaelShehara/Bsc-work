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
import taskcheck.data.enums.IssueStatus;
import taskcheck.data.enums.Priority;
import taskcheck.data.enums.Severity;
import taskcheck.data.view.Views;

/**
 * The Class Issue.
 */
@Entity
@Table(name = "issue")
@Getter
@Setter
public class Issue extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 0;

	/** The identifier. */
	@NotNull
	@Column(name = "identifier", unique = true, updatable = false)
	@JsonView(Views.Issue.class)
	private String identifier;

	/** The summary *. */
	@Column(name = "summary")
	@JsonView(Views.Issue.class)
	private String summary;

	/** The description *. */
	@Column(name = "description")
	@JsonView(Views.Issue.class)
	private String description;

	/** The priority *. */
	@NotNull
	@Column(name = "priority")
	@JsonView(Views.Issue.class)
	private Priority priority;

	/** The severity *. */
	@NotNull
	@Column(name = "severity")
	@JsonView(Views.Issue.class)
	private Severity severity;

	/** The status *. */
	@Column(name = "status")
	@JsonView(Views.Issue.class)
	private IssueStatus status;

	/** The impacted area. */
	@Column(name = "impacted_area")
	@JsonView(Views.Issue.class)
	private String impactedArea;

	/** The story point. */
	@Column(name = "story_point")
	@JsonView(Views.Issue.class)
	private int storyPoint = 0;

	/** The environment. */
	@Column(name = "environment")
	@JsonView(Views.Issue.class)
	private String environment;

	/** The Resolve user *. */
	@ManyToOne
	@JsonView(Views.User.class)
	@JoinColumn(name = "resolved_by", nullable = true)
	private User resolvedBy;

	/** The owned user *. */
	@NotNull
	@ManyToOne
	@JsonView(Views.User.class)
	@JoinColumn(name = "owned_by", nullable = false)
	private User assignee;

	/** The Verified User *. */
	@ManyToOne
	@JsonView(Views.User.class)
	@JoinColumn(name = "verified_by", nullable = true)
	private User verifiedBy;

	/** The project ID *. */
	@ManyToOne
	// @JsonView(Views.Project.class)
	@JoinColumn(name = "project_id", nullable = false)
	private Project project;

	/** The affect version. */
	@ManyToOne
	@JsonView(Views.Release.class)
	@JoinColumn(name = "affect_release_id", nullable = true)
	private Release affectVersion;

	/** The fix version. */
	@ManyToOne
	@JsonView(Views.Release.class)
	@JoinColumn(name = "fix_release_id", nullable = true)
	private Release fixVersion;

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Issue [identifier=" + identifier + ", summary=" + summary + ", description=" + description
				+ ", priority=" + priority + ", severity=" + severity + ", status=" + status + ", impactedArea="
				+ impactedArea + ", resolvedBy=" + resolvedBy + ", assignee=" + assignee + ", verifiedBy=" + verifiedBy
				+ ", affectVersion=" + affectVersion + ", fixVersion=" + fixVersion + "]";
	}

}
