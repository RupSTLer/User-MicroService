package com.stl.rupam.SchoolWebApp.user.service;

import java.time.LocalDate;
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
	
//	public User registerNewUser(User user, String role) {
//		
//		Role selectedRole = roleRepo.findById(role).orElseThrow(() -> new RuntimeException("Role not found"));
//		
//		Set<Role> roles = new HashSet<>();
//		roles.add(selectedRole);
//		user.setRole(roles);
//
//		if(role.equals("Teacher"))
//		{
//			user.setUserID("SMT00" + (userRepo.getTeacherCount()+1));
//			userRepo.insertTeacher(user.getUserID(), user.getUserName(), user.getUserPassword(), user.getName(), user.getAge(), user.getBirthDate(), user.getGender(), user.getAddress(), user.getPhoneNo(), user.getEmail(), user.getDepartment());			
//		}
//		else if(role.equals("Student"))
//		{
//			user.setUserID("SMS00" + (userRepo.getStudentCount()+1));
//			userRepo.insertStudent(user.getUserID(), user.getUserName(), user.getUserPassword(), user.getName(), user.getAge(), user.getBirthDate(), user.getGender(), user.getAddress(), user.getPhoneNo(), user.getEmail(), user.getClasse(), user.getSection());			
//		}
//		else if(role.equals("Admin"))
//		{
//			user.setUserID("SMA00" + (userRepo.getAdminCount()+1));
//		}
//		
//		return userRepo.save(user);
//	}
	
	public String registerNewUser(User user, String role) {
		
		Role selectedRole = roleRepo.findById(role).orElseThrow(() -> new RuntimeException("Role not found"));
		
		Set<Role> roles = new HashSet<>();
		roles.add(selectedRole);
		user.setRole(roles);
		
		String u = validateUser(user);
		
		if(u == null)
		{
			if(role.equals("Teacher"))
			{
				String u1 = validateDOBAgeTeacher(user);
				if(u1 == null)
				{
					user.setUserID("SMT00" + (userRepo.getTeacherCount()+1));
					userRepo.insertTeacher(user.getUserID(), user.getUserName(), user.getUserPassword(), user.getName(), user.getAge(), user.getBirthDate(), user.getGender(), user.getAddress(), user.getPhoneNo(), user.getEmail(), user.getDepartment());
					userRepo.save(user);
				}
				else
				{
					return u1;
				}
			}
			else if(role.equals("Student"))
			{
				String u2 = validateDOBAgeStudent(user);
				if(u2 == null)
				{
					user.setUserID("SMS00" + (userRepo.getStudentCount()+1));
					userRepo.insertStudent(user.getUserID(), user.getUserName(), user.getUserPassword(), user.getName(), user.getAge(), user.getBirthDate(), user.getGender(), user.getAddress(), user.getPhoneNo(), user.getEmail(), user.getClasse(), user.getSection());
					userRepo.save(user);
				}
				else
				{
					return u2;
				}
			}
			else if(role.equals("Admin"))
			{
				String u3 = validateDOBAgeTeacher(user);
				if(u3 == null)
				{
					user.setUserID("SMA00" + (userRepo.getAdminCount()+1));
					userRepo.save(user);
				}
				else
				{
					return u3;
				}
			}
			return "User registered successfully";
		}
		else
		{
			return u;
		}

	}
	
	public String validateUser(User user)
	{
		try {

			List<User> existingEmail = userRepo.findByEmail(user.getEmail());

			if (!existingEmail.isEmpty()) {
				throw new IllegalArgumentException(user.getEmail()+ " :Email already exists");
			}

			Optional<User> existingUserName = userRepo.findNameUser(user.getUserName());

			if (!existingUserName.isEmpty()) {
				throw new IllegalArgumentException(user.getUserName()+ " :Username already exists");
			}

			List<User> existingPhoneNo = userRepo.findByPhoneNo(user.getPhoneNo());

			if (!existingPhoneNo.isEmpty()) {
				throw new IllegalArgumentException(user.getPhoneNo()+ " :PhoneNo already exists");
			}

		} catch (Exception ex) {
			return ex.getMessage();
		}

		return null;
	}
	
	public String validateDOBAgeTeacher(User user)
	{
		LocalDate minDate = LocalDate.of(2000, 1, 1);
		LocalDate maxDate = LocalDate.of(2020, 12, 31);
		LocalDate birthDate = user.getBirthDate();

		try {

			if (birthDate.isBefore(minDate) || birthDate.isAfter(maxDate)) {
				throw new IllegalArgumentException("Invalid Bithdate. Birthdate must be in between 2000 to 2020");
			}
			if(user.getAge() < 30 || user.getAge() > 50)
			{
				throw new IllegalArgumentException("Age must be in between 30 to 50");
			}
			
		} catch (Exception ex) {
			return ex.getMessage();
		}

		return null;
	}
	
	public String validateDOBAgeStudent(User user)
	{
		LocalDate minDate = LocalDate.of(2010, 1, 1);
		LocalDate maxDate = LocalDate.of(2020, 12, 31);
		LocalDate birthDate = user.getBirthDate();

		try {

			if (birthDate.isBefore(minDate) || birthDate.isAfter(maxDate)) {
				throw new IllegalArgumentException("Invalid Bithdate. Birthdate must be in between 2010 to 2020");
			}
			if(user.getAge() < 5 || user.getAge() > 15)
			{
				throw new IllegalArgumentException("Age must be in between 5 to 15");
			}
			
		} catch (Exception ex) {
			return ex.getMessage();
		}

		return null;
	}
	
	public String updateUserDetails(String userId, User user)
	{
		
		if(userId.startsWith("SMS"))
		{
//			String u1 = validateDOBAgeStudent(user);
//			if(u1 == null)
//			{
				userRepo.updateStudentProfile(user.getUserID(), user.getUserName(), user.getUserPassword(), user.getName(), user.getAge(), user.getBirthDate(), user.getGender(), user.getAddress(), user.getPhoneNo(), user.getEmail(), user.getClasse(), user.getSection());							
//			}
//			else
//			{
//				return u1;
//			}
		}
		else if(userId.startsWith("SMT"))
		{
//			String u2 = validateDOBAgeTeacher(user);
//			if(u2 == null)
//			{
				userRepo.updateTeacherProfile(user.getUserID(), user.getUserName(), user.getUserPassword(), user.getName(), user.getAge(), user.getBirthDate(), user.getGender(), user.getAddress(), user.getPhoneNo(), user.getEmail(), user.getDepartment());										
//			}
//			else
//			{
//				return u2;
//			}
		}

		userRepo.saveAndFlush(user);

		return "Profile details updated";
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
	
	public User getUserByUserID(String userID) {
		return userRepo.getUserByUserID(userID);
//				.orElseThrow(() -> new ResourceNotFoundException("User not exist with userID: " + userID));
	}

	public User getAllDetailsByUserName(String userName) {
		return userRepo.findByUserName(userName);
	}

	public void initRolesAndUser() {
		// creating roles -
		
		//Admin role
		Role adminRole = new Role();
		adminRole.setRoleName("Admin");
		adminRole.setRoleDescription("Admin role");
		roleRepo.save(adminRole);

		//User Role
		Role userRole = new Role();
		userRole.setRoleName("User");
		userRole.setRoleDescription("Default role for newly created record");
		roleRepo.save(userRole);

		//Student role
		Role studentRole = new Role();
		studentRole.setRoleName("Student");
		studentRole.setRoleDescription("role for student");
		roleRepo.save(studentRole);

		// Teacher role
		Role teacherRole = new Role();
		teacherRole.setRoleName("Teacher");
		teacherRole.setRoleDescription("role for teacher");
		roleRepo.save(teacherRole);


		///////////////////////////////////////////////////////////////////////

		// hardcoded Admin details -

		User adminUser = new User();
		adminUser.setUserName("admin123");
		adminUser.setUserPassword("Admin@pass");
		adminUser.setUserID("SMA001");
		adminUser.setName("Rupam Chakraborty");
		adminUser.setAge(30);
		adminUser.setBirthDate(LocalDate.of(1992, 6, 13));
		adminUser.setGender("Male");
		adminUser.setAddress("Howrah");
		adminUser.setPhoneNo("6289045253");
		adminUser.setEmail("admin123@g.co");
		adminUser.setDepartment("Administration");

		Set<Role> adminRoles = new HashSet<>();
		adminRoles.add(adminRole);
		adminUser.setRole(adminRoles);
		userRepo.save(adminUser);

		
		User adminUser2 = new User();
		adminUser2.setUserName("admin456");
		adminUser2.setUserPassword("Admin2@pass");
		adminUser2.setUserID("SMA002");
		adminUser2.setName("Ritam Chakraborty");
		adminUser2.setAge(30);
		adminUser2.setBirthDate(LocalDate.of(1995, 3, 24));
		adminUser2.setGender("Male");
		adminUser2.setAddress("Howrah");
		adminUser2.setPhoneNo("6289045256");
		adminUser2.setEmail("admin456@g.co");
		adminUser2.setDepartment("Administration");

		Set<Role> adminRoles2 = new HashSet<>();
		adminRoles2.add(adminRole);
		adminUser2.setRole(adminRoles2);
		userRepo.save(adminUser2);

	}
	
	public void deleteUser(String userName)
	{
		userRepo.deleteById(userName);
	}

	public String getEncodedPassword(String password) {
		return passwordEncoder.encode(password);
	}
}


