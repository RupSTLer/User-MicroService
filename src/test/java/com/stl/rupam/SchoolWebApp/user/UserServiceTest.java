package com.stl.rupam.SchoolWebApp.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.stl.rupam.SchoolWebApp.user.entity.Role;
import com.stl.rupam.SchoolWebApp.user.entity.User;
import com.stl.rupam.SchoolWebApp.user.repo.RoleRepo;
import com.stl.rupam.SchoolWebApp.user.repo.UserRepo;
import com.stl.rupam.SchoolWebApp.user.service.UserService;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class UserServiceTest {
	
	@Mock
	UserRepo userRepo;
	
	@Mock
	RoleRepo roleRepo;
	
	@InjectMocks
	private UserService userService;
	
	@Test
	@Order(1)
	@Rollback(value = false)
	final void registerUserTest()
	{
		User user = new User();
		user.setName("Rupam Chakraborty");
		user.setUserName("rups1234");
		user.setUserPassword("rups@pass");
		user.setBirthDate(LocalDate.of(2015, 5, 25));
		user.setAge(10);
		
		Role role = new Role();
		role.setRoleName("Student");
		
		String rolen = "Student";
		
		when(roleRepo.findById(rolen)).thenReturn(Optional.of(role));
		
		when(userRepo.save(user)).thenReturn(user);
		
		String result = userService.registerNewUser(user, rolen);
		
		assertEquals("User registered successfully", result);
		assertEquals(1, user.getRole().size());
		assertTrue(user.getRole().contains(role));
		verify(roleRepo, times(1)).findById("Student");
		verify(userRepo, times(1)).save(user);
				
	}
	
	@Test
	@Order(2)
	@Rollback(value = false)
	public void getAllUsersTest()
	{
		List<User> userList = new ArrayList<User>();
		
		userList.add(new User("rup123", "rups del", "Rup@pass",  "SMS003", "rup@g.co"));
		userList.add(new User("rit123", "rits Del", "Rit@pass",  "SMS002", "rit@g.co"));
		
		when(userRepo.findAll()).thenReturn(userList);
		
		List<User> result = userService.getAllUsers();
		
		assertEquals(userList, result);
		verify(userRepo, times(1)).findAll();
	}
	
	//count no of users
	@Test
	@Order(3)
	@Rollback(value = false)
	public void getAllDetailsByUserNameTest()
	{
		String userName = "rup123";
		
		User user = new User("rup123", "rups del", "Rup@pass",  "SMS003", "rup@g.co");
		
		when(userRepo.findByUserName(userName)).thenReturn(user);
		
		User result = userService.getAllDetailsByUserName(userName);
		
		assertEquals(user, result);
		verify(userRepo, times(1)).findByUserName(userName);
		
	}
	
	
	@Test
	@Order(4)
	@Rollback(value = false)
	public void getUserCountTest()
	{
		List<User> user = new ArrayList<User>();
		
		user.add(new User("rup123", "rups del", "Rup@pass",  "SMS003", "rup@g.co"));
		user.add(new User("rit123", "rits Del", "Rit@pass",  "SMS002", "rit@g.co"));
		
		when(userRepo.findAll()).thenReturn(Stream.of(new User("rup123", "rups del", "rup@pass",  "SMS003", "rup@g.co"),new User("rit123", "rits Del", "rit@pass",  "SMS002", "rit@g.co")).collect(Collectors.toList()));
		
		assertEquals(2, userService.getAllUsers().size());
	}

}
