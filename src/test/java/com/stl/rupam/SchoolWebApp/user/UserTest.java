package com.stl.rupam.SchoolWebApp.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

import static org.mockito.Mockito.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assertions.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.stl.rupam.SchoolWebApp.user.entity.User;
import com.stl.rupam.SchoolWebApp.user.repo.UserRepo;
import com.stl.rupam.SchoolWebApp.user.service.UserService;

@SpringBootTest
public class UserTest {
	
	@MockBean
	private UserRepo userRepo;
	
//	@Auto
//	private PasswordEncoder passEncoder;
	
	@Autowired
	private UserService userService;
	
	@Test
	final void testGetUser()
	{
		User user = new User();
		user.setUserFirstName("Rupam");
		user.setUserLastName("Chakraborty");
		user.setUserName("rups1234");
		user.setUserPassword("rups@pass");
		
//		User u1 = userService.registerNewUser(user);
		
		User u1 = userRepo.save(user);
		
		System.out.println(u1);
		
		assertEquals(user.getUserPassword(), "rups@pass");
		
		assertEquals(user.getUserName(), "rups1234");
		
		
		when(userRepo.save(user)).thenReturn(user);
		assertEquals(user, userService.registerNewUser(user));
//		when(passEncoder.encode(user.getUserPassword())).thenReturn("rup@12pass");
//		when(userRepo.findByUserName(user.getUserName())).thenReturn("rups1234");
//		assertEquals(passEncoder.encode(user.getUserPassword()), passEncoder.encode("rups@pass"));
		
		
	}
	
	//count no of users
//	@Test
//	public void testUserCount()
//	{
////		assertEquals(4, userService.getAllUsers().size());
//		assertEquals(4, userRepo.findAll().size());
//	}
	
	@Test
	public void getUserTest()
	{
		when(userRepo.findAll()).thenReturn(Stream.of(new User("rup123", "rups", "Del", "rup@pass"),new User("rit123", "rits", "Del", "rit@pass")).collect(Collectors.toList()));
		
		assertEquals(2, userService.getAllUsers().size());
	}
	
	@Test
	public void getUserbyUserName()
	{
		String userName = "rup123";
		when(userRepo.findByUserName(userName)).thenReturn(Stream.of(new User("rup123", "rups", "Del", "rup@pass")).collect(Collectors.toList()));
		
		assertEquals(1, userService.getUserByUserName(userName).size());
	}

}
