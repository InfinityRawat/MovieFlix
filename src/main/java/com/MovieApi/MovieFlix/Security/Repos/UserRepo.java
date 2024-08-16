package com.MovieApi.MovieFlix.Security.Repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.MovieApi.MovieFlix.Security.Entity.User;

import jakarta.transaction.Transactional;

public interface UserRepo extends JpaRepository<User, Integer>{

	Optional<User> findByEmail(String email);
	
	@Modifying
	@Transactional
	@Query(value = "update users set password = ?2 where email= ?1 ",nativeQuery =true)
	void updatePassword(String email, String 	password);
}
