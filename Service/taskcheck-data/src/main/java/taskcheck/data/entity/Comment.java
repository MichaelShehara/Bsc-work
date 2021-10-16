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
 * The Class TaskComment.
 */
@Entity
@Table(name = "comment")
@Getter
@Setter
public class Comment extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6028955344702911464L;

	/** The identifier. */
	@NotNull
	@Column(name = "identifier")
	@JsonView(Views.Comment.class)
	private String identifier;

	/** The comment. */
	@Column(name = "comment")
	@JsonView(Views.Comment.class)
	private String comment;

}
