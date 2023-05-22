package com.stl.rupam.SchoolWebApp.user.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stl.rupam.SchoolWebApp.user.entity.User;
import com.stl.rupam.SchoolWebApp.user.service.UserService;


@RestController
@CrossOrigin("http://localhost:4200")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostConstruct  //PostConstruct - used on a method that needs to be executed after dependency injection is done to perform any initialization
	public void initRolesAndUsers()
	{
		userService.initRolesAndUser();
	}
	
	@PostMapping("/registerNewUser")
	public User registerNewUser(@RequestBody User user)
	{
		return userService.registerNewUser(user);
	}
	
	@GetMapping("/forAdmin")
	@PreAuthorize("hasRole('Admin')")
	public String forAdmin()
	{
		return "This URL is only accessible to admin";
	}
	
	@GetMapping("/forUser")
	@PreAuthorize("hasAnyRole('Admin','User')")
	public String forUser()
	{
		return "This URL is only accessible to user";
	}
	
//	@PreAuthorize("hasAnyRole('Admin','User')") - to set multiple user role to access a single endpoint

}
