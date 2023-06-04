package com.stl.rupam.SchoolWebApp.user.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.stl.rupam.SchoolWebApp.user.entity.Role;
import com.stl.rupam.SchoolWebApp.user.entity.User;
import com.stl.rupam.SchoolWebApp.user.repo.RoleRepo;
import com.stl.rupam.SchoolWebApp.user.repo.UserRepo;

@Service
public class UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private RoleRepo roleRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

//	register new user
	public User registerNewUser(User user) {
		
		Role role = roleRepo.findById("User").get();

		Set<Role> roles = new HashSet<>();
		roles.add(role);
		user.setRole(roles);

//		user.setUserPassword(getEncodedPassword(user.getUserPassword()));
		user.setUserPassword(user.getUserPassword());
		return userRepo.save(user);
	}

	public List<User> getAllUsers() {
		return userRepo.findAll();
	}
	
	public String getNameOfUser(String username)
	{
		Optional<User> userOptional = userRepo.findNameUser(username);
		
		if(userOptional.isPresent())
		{
			User user = userOptional.get();
			return user.getName();
		}
		else
		{
			throw new UsernameNotFoundException("user not found with username: " + username);
		}
		
	}

//	public User getUserById(Long id)
//	{
//		return userRepo.findById(id).orElse(null);
//	}
//	
//	public User deleteUser(Long id)
//	{
//		return userRepo.deleteById(id);
//	}

	public User getAllDetailsByUserName(String userName) {
		return userRepo.findByUserName(userName);
	}

	public void initRolesAndUser() {
		// creating roles -
//
//		//Admin role
		Role adminRole = new Role();
		adminRole.setRoleName("Admin");
		adminRole.setRoleDescription("Admin role");
		roleRepo.save(adminRole);
//
//		//User Role
		Role userRole = new Role();
		userRole.setRoleName("User");
		userRole.setRoleDescription("Default role for newly created record");
		roleRepo.save(userRole);
//
//		//Student role
		Role studentRole = new Role();
		studentRole.setRoleName("Student");
		studentRole.setRoleDescription("role for student");
		roleRepo.save(studentRole);

		// Teacher role
		Role teacherRole = new Role();
		teacherRole.setRoleName("Teacher");
		teacherRole.setRoleDescription("role for teacher");
		roleRepo.save(teacherRole);

//
//		///////////////////////////////////////////////////////////////////////
//
		// hardcoded Admin details -

		User adminUser = new User();
		adminUser.setName("Rupam Chakraborty");
		adminUser.setUserName("admin123");
//		adminUser.setUserPassword(getEncodedPassword("Admin@pass"));
		adminUser.setUserPassword("Admin@pass");

		Set<Role> adminRoles = new HashSet<>();
		adminRoles.add(adminRole);
		adminUser.setRole(adminRoles);
		userRepo.save(adminUser);

		
		User adminUser2 = new User();
		adminUser2.setName("Ranjan Roy");
		adminUser2.setUserName("admin456");
//		adminUser.setUserPassword(getEncodedPassword("Admin2@pass"));
		adminUser2.setUserPassword("Admin2@pass");

		Set<Role> adminRoles2 = new HashSet<>();
		adminRoles2.add(adminRole);
		adminUser2.setRole(adminRoles2);
		userRepo.save(adminUser2);
		
    
		//hardcoded student details -
		User studentUser = new User();
		studentUser.setName("Anu Roy");
		studentUser.setUserName("anu123");
//		studentUser.setUserPassword(getEncodedPassword("Anu@pass"));
		studentUser.setUserPassword("Anu@pass");

		Set<Role> studentRoles = new HashSet<>();
		studentRoles.add(studentRole);
		studentUser.setRole(studentRoles);
		userRepo.save(studentUser);
		
		
		//hardcoded teacher details -
		User teacherUser = new User();
		teacherUser.setName("Sumona Roy");
		teacherUser.setUserName("sumo123");
//		teacherUser.setUserPassword(getEncodedPassword("Sumo@pass"));
		teacherUser.setUserPassword("Sumo@pass");

		Set<Role> teacherRoles = new HashSet<>();
		teacherRoles.add(teacherRole);
		teacherUser.setRole(teacherRoles);
		userRepo.save(teacherUser);
		
		
//
//		// hardcoded User details -
//
//		User user = new User();
//		user.setUserFirstName("Rupam");
//		user.setUserLastName("Roy");
//		user.setUserName("rup123");
//		user.setUserPassword("Rup@pass");
//
//		Set<Role> userRoles = new HashSet<>();
//		userRoles.add(userRole);
//		user.setRole(userRoles);
//		userRepo.save(user);

		// hardcoded student details -

//		Student student = new Student();
//		student.setId(1L);
//		student.setName("Ranjan Roy");
//		student.setUserName("ran123");
//		student.setPassword(getEncodedPassword("ran@pass"));
//		student.setEmail("ran@g.co");
//
//		Set<Role> studentRoles = new HashSet<>();
//		studentRoles.add(studentRole);
//		student.setRole(studentRoles);
//		studentRepo.save(student);

		// hardcoded teacher details -

//		Teacher teacher = new Teacher();
//		teacher.setName("Ritam Roy");
//		teacher.setId(1L);
//		teacher.setUserName("rit123");
//		teacher.setEmail("rit@g.co");
//		teacher.setPassword(getEncodedPassword("rit@pass"));
//
//		Set<Role> teacherRoles = new HashSet<>();
//		teacherRoles.add(teacherRole);
//		teacher.setRole(teacherRoles);
//		teacherRepo.save(teacher);

	}

	public String getEncodedPassword(String password) {
		return passwordEncoder.encode(password);
	}
}
