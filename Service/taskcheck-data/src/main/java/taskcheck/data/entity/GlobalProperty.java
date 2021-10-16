/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * The Class GlobalProperty.
 */
@Entity
@Table(name = "global_property")
public class GlobalProperty extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5731932750849629729L;

	/** The name. */
	@NotNull
	@Column(name = "name")
	private String name;

	/** The value. */
	@NotNull
	@Column(name = "value")
	private String value;

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
