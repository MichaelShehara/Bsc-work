/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.data.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;
import taskcheck.data.view.Views;

/**
 * The Class Release.
 */
@Entity
@Table(name = "project_release")
@Getter
@Setter
public class Release extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7159493291495306220L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@JsonView(Views.Release.class)
	protected Long id;
	
	/** The name. */
	@NotNull
	@Column(name = "name", unique = true)
	@JsonView(Views.Release.class)
	private String name;

	/** The description. */
	@Column(name = "description")
	@JsonView(Views.Release.class)
	private String description;

	/** The target date. */
	@Column(name = "target_date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonView(Views.Release.class)
	private Date targetDate;

	/** the Project ID *. */
	@ManyToOne
	@JsonView(Views.Project.class)
	@JoinColumn(name = "project_id", nullable = false, updatable = false)
	private Project project;

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Release [id=" + id + ",name=" + name + ", description=" + description + ", targetDate=" + targetDate + ", project="
				+ project + "]";
	}
}
