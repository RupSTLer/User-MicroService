package com.stl.rupam.SchoolWebApp.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stl.rupam.SchoolWebApp.user.entity.Role;
import com.stl.rupam.SchoolWebApp.user.repo.RoleRepo;

@Service
public class RoleService {
	
	@Autowired
	private RoleRepo roleRepo;

	public Role createNewRole(Role role)
	{
		return roleRepo.save(role);
	}
}