/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.service.impl;

import java.util.List;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import taskcheck.app.service.RoleService;
import taskcheck.data.entity.Role;
import taskcheck.data.repository.RoleRepository;

/**
 * The Class UserServiceImpl.
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	/** The role repository. */
	@Autowired
	private RoleRepository roleRepository;

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	@Override
	public List<Role> getAll() {
		return roleRepository.getAll();
	}
}
