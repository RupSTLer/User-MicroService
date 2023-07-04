package com.stl.rupam.SchoolWebApp.user.controller;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stl.rupam.SchoolWebApp.user.entity.User;
import com.stl.rupam.SchoolWebApp.user.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin("http://localhost:4200")
@Api(tags = "User Service APIs", value = "User MicroService", description = "it will handle the web requests of user service")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostConstruct  //PostConstruct - used on a method that needs to be executed after dependency injection is done to perform any initialization
	public void initRolesAndUsers()
	{
		userService.initRolesAndUser();
	}
	
	@ApiOperation(value = "Register a new user into School Management System")
	@PostMapping("/registerNewUser")
	public String registerNewUser(@RequestBody User user, @RequestParam String role)
	{
		return userService.registerNewUser(user, role);
	}
	
	@ApiOperation(value = "Update user profile details using userID as parameter")
	@PutMapping("/updateUserDetails/{userId}")
	public String updateUserDetails(@Valid @PathVariable String userId, @RequestBody User user)
	{
		return userService.updateUserDetails(userId,user);
	}
		
	@ApiOperation(value = "get details of currently logged in user")
	@GetMapping("/userDetails")
	public ResponseEntity<UserDetails> getUserDetails(Authentication authentication)
	{
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		return ResponseEntity.ok(userDetails);
	}
	
	@ApiOperation(value = "get loggin in user name")
	@GetMapping("/getNameOfUser/{username}")
	public ResponseEntity<String> getNameOfUser(@PathVariable String username)
	{
		try {
			String name = userService.getNameOfUser(username);
			return ResponseEntity.ok(name);
		}
		catch(Exception ex)
		{
			return ResponseEntity.notFound().build();
		}
	}
	
	@ApiOperation(value = "get all details of logged in user by using username as parameter")
	@GetMapping("/getAllDetailsByUserName/{username}")
	public User getAllDetailsByUserName(@PathVariable String username)
	{
		User allDetails = userService.getAllDetailsByUserName(username);
		
		return allDetails;
	}
	
	@ApiOperation(value = "get user by userID as parameter")
	@GetMapping("/{userID}")
	public User getUserByUserID(@PathVariable String userID)
	{
		User users = userService.getUserByUserID(userID);
		
		return users;
	}
	
	@ApiOperation(value = "check authentication for Admin")
	@GetMapping("/forAdmin")
	@PreAuthorize("hasRole('Admin')")
	public String forAdmin()
	{
		return "This URL is only accessible to admin";
	}
	
	@ApiOperation(value = "check authentication for user")
	@GetMapping("/forUser")
	@PreAuthorize("hasAnyRole('Admin','User')") //set multiple user role to access a single endpoint
	public String forUser()
	{
		return "This URL is only accessible to user";
	}
	
}
