/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.service;

import java.util.List;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import taskcheck.data.entity.Role;

/**
 * The Interface RoleService.
 */

@Service
@Transactional
public interface RoleService {
	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	List<Role> getAll();
}
