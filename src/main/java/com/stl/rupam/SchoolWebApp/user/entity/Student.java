package com.stl.rupam.SchoolWebApp.user.entity;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.persistence.JoinColumn;

import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
//	@NotEmpty(message = "student id is mandetory")
	private String studentId;
	
//	@NotEmpty(message = "username is mandetory")
	private String userName;
	
//	@NotEmpty(message = "password is mandetory")
//	@Pattern(regexp = "")
	private String password;
	
//	@NotEmpty(message = "student name is mandetory")
	private String name;
	
//	@Email(message = "email not valid")
//	@Pattern(regexp = "")
	private String email;
	
	public Set<Role> getRole() {
		return role;
	}



	public void setRole(Set<Role> role) {
		this.role = role;
	}


	
//	@ElementCollection(fetch = FetchType.EAGER, targetClass = Role.class)
//	@Enumerated(EnumType.STRING)
//	@Transient
//	private Set<Role> role;
	
//	@Column(name = "full_name")
//	private String fullName;
//	
//	private String firstName;
//	
//	private String lastName;
//	
////	@Column(name = "gender")
//	private String gender;
//	
////	@Column(name = "email", nullable = false)
//	private String email;
//	
////	@Column(name = "phone_no")
//	private String phoneNo;
//	
////	@Column(name = "DOB", nullable = false)
//	private String DOB;
//	
////	@Column(name = "full_name")
//	private String address;
//	
////	@Column(name = "class")
//	private String classe;
//	
////	@Column(name = "full_name")
//	private String section;

	
	//@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//	@JoinTable(name = "USER_ROLE", 
//				joinColumns = { 
//						@JoinColumn(name = "ROLE_ID") 
//						}, 
//				inverseJoinColumns = {
//						@JoinColumn(name = "STUDENT_ID")  
//						})
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "STUDENT_ROLE", 
				joinColumns = { 
						@JoinColumn(name = "STUDENT_ID", referencedColumnName = "id") 
						}, 
				inverseJoinColumns = {
						@JoinColumn(name = "ROLE_ID", referencedColumnName = "id")  
						})
	private Set<Role> role;


}
