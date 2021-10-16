/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.data.entity;

import java.io.Serializable;


import java.util.Date;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;
import taskcheck.data.view.Views;

/**
 * The Class User.
 */
@Entity
@Table(name = "user")
@Getter
@Setter
public class User implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4702108842959161268L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@JsonView(Views.User.class)
	protected Long id;

	/** The username. */
	@NotNull(message = "Username cannot be null")
	@NotEmpty(message = "Username cannot be empty")
	@Email(message = "Email should be valid")
	@Column(name = "username", unique = true, updatable = false)
	@JsonView(Views.User.class)
	private String username;

	/** The full name. */
	@NotNull(message = "Full name cannot be null")
	@NotEmpty(message = "Full name cannot be empty")
	@Column(name = "full_name")
	@JsonView(Views.User.class)
	private String fullName;

	/** The password. */
	@NotNull(message = "Password cannot be null")
	@NotEmpty(message = "Password cannot be empty")
	@Column(name = "password")
	private String password;

	/** The roles. */
	@NotNull(message = "User has to be asigned a role")
	@JsonView(Views.Role.class)
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.REFRESH, CascadeType.MERGE })
	@JoinTable(name = "user_role_mapping", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "role_id") })
	private Set<Role> roles = new HashSet<>();

	/** The Users' Projects. */
	@JsonView(Views.UserProject.class)
	@ManyToMany(mappedBy = "users", fetch = FetchType.EAGER, cascade = { CascadeType.REFRESH, CascadeType.MERGE })
	private Set<Project> projects = new HashSet<>();

	/** The created by. */
	@JsonView(Views.User.class)
	@Column(name = "created_by", updatable = false)
	protected String createdBy;

	/** The created at. */
	@NotNull
	@Column(name = "created_at", updatable = false)
	@JsonView(Views.User.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a z")
	protected Date createdAt = new Date();

	/** The updated by. */
	@JsonView(Views.User.class)
	@Column(name = "updated_by")
	protected String updatedBy;

	/** The updated at. */
	@NotNull
	@Column(name = "updated_at")
	@JsonView(Views.User.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a z")
	protected Date updatedAt = new Date();

	/** The enabled. */
	@NotNull
	@Column(name = "enabled")
	@JsonView(Views.User.class)
	protected boolean enabled = true;

	/** The voided. */
	@NotNull
	@Column(name = "voided")
	@JsonView(Views.User.class)
	protected boolean voided;

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", fullName=" + fullName + ", password=" + password
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", enabled=" + enabled + ", voided="
				+ voided + "]";
	}

}
