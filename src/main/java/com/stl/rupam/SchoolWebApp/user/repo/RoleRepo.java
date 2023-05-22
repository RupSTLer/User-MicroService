package com.stl.rupam.SchoolWebApp.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stl.rupam.SchoolWebApp.user.entity.Role;

public interface RoleRepo extends JpaRepository<Role, String>{

}
