package com.stl.rupam.SchoolWebApp.user.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.stl.rupam.SchoolWebApp.user.entity.User;

public interface UserRepo extends JpaRepository<User, String> {
	
	public User findByUserName(String userName);
	
	@Query(value = "select * from user where user_name = ?", nativeQuery = true)
	Optional<User> findNameUser(String username);
	
//	List<User> getAllDetailsByUserName(String userName)
}
