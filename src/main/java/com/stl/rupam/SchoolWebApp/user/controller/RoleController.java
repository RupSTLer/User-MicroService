package com.stl.rupam.SchoolWebApp.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stl.rupam.SchoolWebApp.user.entity.Role;
import com.stl.rupam.SchoolWebApp.user.service.RoleService;

@RestController
@CrossOrigin("http://localhost:4200")
public class RoleController {
//class to make endpoints for creating roles
	
	@Autowired
	private RoleService roleService;
	
	@PostMapping("/createNewRole")
	public Role createNewRole(@RequestBody Role role)
	{
		return roleService.createNewRole(role);
	}
}
