package com.MovieApi.MovieFlix.Security.Service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.MovieApi.MovieFlix.Security.Entity.RefreshToken;
import com.MovieApi.MovieFlix.Security.Entity.User;
import com.MovieApi.MovieFlix.Security.Repos.RefreshTokenRepo;
import com.MovieApi.MovieFlix.Security.Repos.UserRepo;

@Service
public class RefreshTokenService {
	
		private UserRepo userRepo;
		private RefreshTokenRepo tokenRepo;
	
		
		
		public RefreshTokenService(UserRepo userRepo, RefreshTokenRepo tokenRepo) {
			super();
			this.userRepo = userRepo;
			this.tokenRepo = tokenRepo;
		}



		public RefreshToken createRefreshToken(String username) {
			
				User user = userRepo.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("user not available in db"));
				RefreshToken token = user.getToken();
				if(token==null) {
					
		            long refreshTokenValidity = 600 * 1000;

					token =  RefreshToken.builder().refreshToken(UUID.randomUUID().toString())
							.expirationTime(Instant.now().plusMillis(refreshTokenValidity))
							.user(user).build();
					
					tokenRepo.save(token);

				}
				return token;
		}
		
		public RefreshToken validateRefreshToken(String refToken) {
			
			
			RefreshToken refresh = tokenRepo.findByRefreshToken(refToken).orElseThrow(()->new RuntimeException("refresh token not found!!"));
			
			if(refresh.getExpirationTime().compareTo(Instant.now())<0) {
				
	            tokenRepo.delete(refresh);
	            throw new RuntimeException("Token expired now!!");

			}
			return refresh;
	}
}
