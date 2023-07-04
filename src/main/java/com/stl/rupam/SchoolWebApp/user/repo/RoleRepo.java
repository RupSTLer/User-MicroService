package com.stl.rupam.SchoolWebApp.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.stl.rupam.SchoolWebApp.user.entity.Role;

public interface RoleRepo extends JpaRepository<Role, String>{
	
	@Transactional
	@Modifying
	@Query(value = "update user_role u set u.user_id = ? where u.user_id = ?", nativeQuery = true)
	void updateUserName(@Param("user_id") String user_id);

}
//@Query(value = "update leaves l set l.status = ('rejected') where l.id = ?", nativeQuery = true)