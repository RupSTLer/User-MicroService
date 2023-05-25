package com.stl.rupam.SchoolWebApp.user.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stl.rupam.SchoolWebApp.user.entity.User;

public interface UserRepo extends JpaRepository<User, String> {
	
	public List<User> findByUserName(String userName);
	
}
