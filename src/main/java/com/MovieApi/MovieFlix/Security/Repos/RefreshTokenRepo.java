package com.MovieApi.MovieFlix.Security.Repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.MovieApi.MovieFlix.Security.Entity.RefreshToken;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Integer> {
		
		Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
