package com.stl.rupam.SchoolWebApp.user.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.persistence.JoinColumn;

import lombok.Data;

@Entity
@Data
public class User {
//	private Long id;
//	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Id
	@NotEmpty(message = "username is mandetory")
	@Pattern(regexp = "[a-zA-Z0-9]{4,}")
	private String userName;
	
	@NotEmpty(message = "firstname is mandetory")
	@Pattern(regexp = "[a-zA-Z]{2}[a-zA-Z ]+", message = "please add valid name")
	private String userFirstName;
	
	@NotEmpty(message = "lastname is mandetory")
	@Pattern(regexp = "[a-zA-Z]{2}[a-zA-Z ]+", message = "please add valid name")
	private String userLastName;
	
	@NotEmpty(message = "password is mandetory")
	@Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z]).{5,}")
	private String userPassword;
	
	
	public Set<Role> getRole() {
		return role;
	}

	public void setRole(Set<Role> role) {
		this.role = role;
	}

	public User(@NotEmpty(message = "username is mandetory") String userName,
			@NotEmpty(message = "firstname is mandetory") String userFirstName,
			@NotEmpty(message = "lastname is mandetory") String userLastName,
			@NotEmpty(message = "password is mandetory") String userPassword) {
		super();
		this.userName = userName;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.userPassword = userPassword;
	}


	public User() {
		super();
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
