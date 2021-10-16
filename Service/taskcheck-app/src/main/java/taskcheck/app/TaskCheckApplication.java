/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * The Class JanusApplication.
 */
@SpringBootApplication
@EntityScan(basePackages = { "taskcheck.data.entity" })
@EnableJpaRepositories(basePackages = { "taskcheck.data.repository" })
public class TaskCheckApplication {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(TaskCheckApplication.class, args);
		AppBanner.printBanner();
	}

}