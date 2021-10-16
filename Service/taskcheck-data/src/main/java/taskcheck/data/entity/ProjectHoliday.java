/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.data.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;
import taskcheck.data.view.Views;

/**
 * The Class ProjectHoliday.
 */
@Entity
@Table(name = "project_holiday")
@Getter
@Setter
public class ProjectHoliday extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6053722695673534285L;

	/** The holiday. */
	@Column(name = "holiday")
	@JsonView(Views.Project.class)
	private Date holiday;

	/** The project. */
	@ManyToOne
	@JsonView(Views.Project.class)
	@JoinColumn(name = "project_id", nullable = false)
	private Project project;
}
