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
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;
import taskcheck.data.view.Views;

/**
 * The Class Role.
 */
@Entity
@Table(name = "role")
@Getter
@Setter
public class Role extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5575686329906990336L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@JsonView(Views.Role.class)
	protected Long id;
	
	/** The name. */
	@NotNull
	@Column(name = "name", unique = true)
	@JsonView(Views.Role.class)
	private String name;

	/** The description. */
	@Column(name = "description")
	@JsonView(Views.Role.class)
	private String description;
	
	/** User Role Mapping. */
	@ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER, cascade = { CascadeType.REFRESH, CascadeType.MERGE })
	private Set<User> users = new HashSet<>();
	
	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", description=" + description + "]";
	}

}
