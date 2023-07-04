package com.stl.rupam.SchoolWebApp.user.repo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.stl.rupam.SchoolWebApp.user.entity.User;

public interface UserRepo extends JpaRepository<User, String> {
	
	public User findByUserName(String userName);
	
	@Query(value = "select * from user where user_name = ?", nativeQuery = true)
	Optional<User> findNameUser(String username);
	
	public User getUserByUserID(String userID);
	
	List<User> findByEmail(String email);
	
//	List<User> getUserName(String userName);
	
	List<User> findByPhoneNo(String phoneNo);
	
	@Query(value = "select count(*) from students", nativeQuery = true)
	int getStudentCount();
	
	@Query(value = "select count(*) from teachers", nativeQuery = true)
	int getTeacherCount();
	
	@Query(value = "select count(*) from user_role where role_id = 'Admin'", nativeQuery = true)
	int getAdminCount();
	
	@Transactional
	@Modifying
	@Query(value = "insert into teachers(teacher_id, user_name, password, name, age, birth_date, gender, address, phone_no, email, department) values (?,?,?,?,?,?,?,?,?,?,?)", nativeQuery = true)
	void insertTeacher(String userID, String userName, String password, String name, int age, LocalDate birthdate, String gender, String address, String phoneNo, String email, String department);
	
	@Transactional
	@Modifying
	@Query(value = "insert into students(student_id, user_name, password, name, age, birth_date, gender, address, phone_no, email, classe, section) values (?,?,?,?,?,?,?,?,?,?,?,?)", nativeQuery = true)
	void insertStudent(String userID, String userName, String password, String name, int age, LocalDate birthdate, String gender, String address, String phoneNo, String email, String classe, String section);
	
	@Transactional
	@Modifying
	@Query(value = "update students s set s.user_name=:username, s.password=:password, s.name=:name, s.age=:age, s.birth_date=:birthdate, s.gender=:gender, s.address=:address, s.phone_no=:phoneNo, s.email=:email, s.classe=:classe, s.section=:section where s.student_id=:userID", nativeQuery = true)
	void updateStudentProfile(@Param("userID") String userID, @Param("username") String username, @Param("password") String password, @Param("name") String name, @Param("age") int age, @Param("birthdate") LocalDate birthdate, @Param("gender") String gender, @Param("address") String address, @Param("phoneNo") String phoneNo, @Param("email") String email, @Param("classe") String classe, @Param("section") String section);
	
	@Transactional
	@Modifying
	@Query(value = "update teachers s set s.user_name=:username, s.password=:password, s.name=:name, s.age=:age, s.birth_date=:birthdate, s.gender=:gender, s.address=:address, s.phone_no=:phoneNo, s.email=:email, s.department=:department where s.teacher_id=:userID", nativeQuery = true)
	void updateTeacherProfile(@Param("userID") String userID, @Param("username") String username, @Param("password") String password, @Param("name") String name, @Param("age") int age, @Param("birthdate") LocalDate birthdate, @Param("gender") String gender, @Param("address") String address, @Param("phoneNo") String phoneNo, @Param("email") String email, @Param("department") String department);
	    
}
