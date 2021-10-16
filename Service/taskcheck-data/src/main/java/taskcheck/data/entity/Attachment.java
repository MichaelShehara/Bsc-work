/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;
import taskcheck.data.view.Views;

/**
 * The Class Attachment.
 */
@Entity
@Table(name = "attachment")
@Getter
@Setter
public class Attachment extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 0;

	/** The identifier. */
	@NotNull
	@Column(name = "identifier")
	@JsonView(Views.Attachment.class)
	private String identifier;

	/** The name. */
	@NotNull
	@Column(name = "name")
	@JsonView(Views.Attachment.class)
	private String name;

	/** The description. */
	@Column(name = "description")
	@JsonView(Views.Attachment.class)
	private String description;
	
	/** The format. */
	@Column(name = "format")
	@JsonView(Views.Attachment.class)
	private String format;

	/** The path. */
	@NotNull
	@Column(name = "path")
	@JsonView(Views.Attachment.class)
	private String path;
	
}
