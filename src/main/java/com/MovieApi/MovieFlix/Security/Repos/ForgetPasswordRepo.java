package com.MovieApi.MovieFlix.Security.Repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.MovieApi.MovieFlix.Security.Entity.ForgetPassword;
import com.MovieApi.MovieFlix.Security.Entity.User;

public interface ForgetPasswordRepo extends  JpaRepository<ForgetPassword, Integer> {
	
		@Query(name = "select * from forgetPass where user = ?1 and otp =?2",nativeQuery = true)
		Optional<ForgetPassword> findByUserAndOtp(User user, Integer OTP);
}
