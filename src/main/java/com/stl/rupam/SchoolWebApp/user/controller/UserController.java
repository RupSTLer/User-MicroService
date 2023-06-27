package com.stl.rupam.SchoolWebApp.user.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@GetMapping("/userDetails")
	public ResponseEntity<UserDetails> getUserDetails(Authentication authentication)
	{
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		return ResponseEntity.ok(userDetails);
	}
	
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
	
	@GetMapping("/getAllDetailsByUserName/{username}")
	public User getAllDetailsByUserName(@PathVariable String username)
	{
		User allDetails = userService.getAllDetailsByUserName(username);
		
		return allDetails;
	}
	
	@GetMapping("/{userID}")
	public User getUserByUserID(@PathVariable String userID)
	{
		User users = userService.getUserByUserID(userID);
		
		return users;
	}
	
//	@GetMapping("/{userID}")
//	public ResponseEntity<User> getUserByUserID(@PathVariable String userID) {
//		return new ResponseEntity<User>(userService.getUserByUserID(userID), HttpStatus.OK);
//	}
	
//	@PreAuthorize("hasAnyRole('Admin','User')") - to set multiple user role to access a single endpoint

}
