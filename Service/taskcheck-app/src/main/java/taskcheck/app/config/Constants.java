/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.config;

/**
 * The Class Constants.
 */

public class Constants {

	/**
	 * Instantiates a new constants.
	 */
	private Constants() {
		// Prevent instantiation
	}

	/**
	 * The Constants Interface Database.
	 */
	public interface Database {

		/** The Constant DATABASE_DRIVER_DATASOURCE_CLAZZ. */
		public static final String DATABASE_DRIVER_DATASOURCE_CLAZZ = "db.driver.datasource.clazz";

		/** The Constant DATABASE_DRIVER_CLAZZ. */
		public static final String DATABASE_DRIVER_CLAZZ = "db.driver.clazz";

		/** The Constant DATABASE_URL. */
		public static final String DATABASE_URL = "db.url";

		/** The Constant DATABASE_USERNAME. */
		public static final String DATABASE_USERNAME = "db.username";

		/** The Constant DATABASE_PWD. */
		public static final String DATABASE_PWD = "db.pwd";

		/** The Constant DATABASE_CONNECTION_TEST_QUERY. */
		public static final String DATABASE_CONNECTION_TEST_QUERY = "db.connection.test.query";

		/** The Constant DATABASE_MAX_POOL_SIZE. */
		public static final String DATABASE_MAX_POOL_SIZE = "db.max.pool.size";

	}

	/**
	 * Constants related to Hibernate ORM.
	 */
	public interface Hibernate {

		/** The Constant HIBERNATE_DIALECT. */
		public static final String HIBERNATE_DIALECT = "hibernate.dialect";

		/** The Constant HIBERNATE_DIALECT_TEST. */
		public static final String HIBERNATE_DIALECT_TEST = "hibernate.dialect.test";

		/** The Constant HIBERNATE_SHOW_SQL. */
		public static final String HIBERNATE_SHOW_SQL = "hibernate.show_sql";

		/** The Constant HIBERNATE_HBM2DDL. */
		public static final String HIBERNATE_HBM2DDL = "hibernate.hbm2ddl.auto";

		/** The Constant HIBERNATE_FORMAT_SQL. */
		public static final String HIBERNATE_FORMAT_SQL = "hibernate.format_sql";

		/** The Constant HIBERNATE_SHOULD_VALIDATE. */
		public static final String HIBERNATE_SHOULD_VALIDATE = "hibernate.should.validate";

		/** The Constant HIBERNATE_SHOULD_VALIDATE. */
		public static final String HIBERNATE_NAMING_STRATEGY = "spring.jpa.hibernate.naming-strategy";

		/** Allows Hibernate to generate SQL optimized for a particular DBMS. */
		public static final String HIBERNATE_INNODB_DIALECT = "spring.jpa.properties.hibernate.dialect";
	}

	/**
	 * The Interface ApiVersion.
	 */
	public interface ApiVersion {

		/** The Constant V1. */
		public static final String V1 = "X-API-VERSION=v1";
	}
	
	/**
	 * The Interface Attachment.
	 */
	public interface Attachment {

		/** The Constant ATTACHMENT_PATH. */
		public static final String ATTACHMENT_PATH = "attachment.path";
	}

}
