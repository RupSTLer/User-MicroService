package com.stl.rupam.SchoolWebApp.user.jwt;

public class JwtRequest {
	//this class is responsible for taking userName, userPassword & also name
	private String userName;
	private String userPassword;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	
}
