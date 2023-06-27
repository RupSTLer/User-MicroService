package com.stl.rupam.SchoolWebApp.user.entity;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.JoinColumn;

import lombok.Data;

@Entity
@Data
public class User {

	@Id
	@NotEmpty(message = "username is mandetory")
	@Pattern(regexp = "[a-zA-Z0-9]{4,}")
	private String userName;
	
	@NotEmpty(message = "password is mandetory")
	@Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z]).{5,}")
	private String userPassword;
	
	@NotEmpty(message = "userID is mandetory")
	private String userID;
	
	@NotEmpty(message = "name is mandetory")
	@Pattern(regexp = "[a-zA-Z]{2}[a-zA-Z ]+", message = "please add valid name")
	private String name;
	
	@NotNull(message = "please add valid age")
	@Positive(message = "age should be positive")
//	@Min(value = 6, message = "age must be atleast 6")
//	@Max(value = 18, message = "age must be less than 18")
	private int age;

//	@NotNull(message = "DOB is mandetory")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthDate;

	@NotEmpty(message = "gender is mandetory")
	private String gender;

	@NotEmpty(message = "address is mandetory")
	@Pattern(regexp = "^[a-zA-Z0-9 .,-]+$", message = "please add valid address")
	private String address;

	@NotEmpty(message = "phone no is mandetory")
	@Positive(message = "phoneNo sould be positive")
	@Pattern(regexp = "(0|91)?[6-9][0-9]{9}", message = "please add valid phone no")
	private String phoneNo;

	@NotEmpty(message = "email is mandetory")
	@Email(message = "please give valid email")
	private String email;

//	@NotEmpty(message = "Class is mandetory")
	@Pattern(regexp = "[a-zA-Z]{3,}", message = "please add valid class")
	private String classe;

//	@NotEmpty(message = "section is mandetory")
	@Pattern(regexp = "[A-D]", message = "please add valid section")
	private String section;
	
	@NotEmpty(message = "Department is mandetory")
	@Pattern(regexp = "[a-zA-Z]{3,}", message = "please add valid department")
	private String department;
	
	
	public Set<Role> getRole() {
		return role;
	}

	public void setRole(Set<Role> role) {
		this.role = role;
	}
	




	public User() {
		super();
	}


	public User(@NotEmpty(message = "username is mandetory") @Pattern(regexp = "[a-zA-Z0-9]{4,}") String userName,
			@NotEmpty(message = "password is mandetory") @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z]).{5,}") String userPassword,
			@NotEmpty(message = "name is mandetory") @Pattern(regexp = "[a-zA-Z]{2}[a-zA-Z ]+", message = "please add valid name") String name,
			@NotEmpty(message = "userID is mandetory") String userID,
			@Email(message = "please give valid email") String email) {
		super();
		this.userName = userName;
		this.userPassword = userPassword;
		this.name = name;
	}


	// create a set collection because a user can have multiple roles, so to store multiple roles we use Set
	// here we use Assocoation to connect two tables
	// manyToMany coz many users can have many roles
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "USER_ROLE", 
				joinColumns = { 
						@JoinColumn(name = "USER_ID") 
						}, 
				inverseJoinColumns = {
						@JoinColumn(name = "ROLE_ID")  
						})
	private Set<Role> role;

	
}
