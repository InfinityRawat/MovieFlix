package com.MovieApi.MovieFlix.Security.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.MovieApi.MovieFlix.Security.Repos.UserRepo;

@Configuration
public class SecurityConfig {



		private UserRepo repo;
			
		
		public SecurityConfig(UserRepo repo) {
		super();
		this.repo = repo;
	}

		@Bean
		UserDetailsService getUserFrom() {
			
			return  (username) -> repo.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User not in database"));
		}
		
		@Bean
		AuthenticationProvider authProvider() {
			DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
			provider.setUserDetailsService(getUserFrom());
			provider.setPasswordEncoder(passEncode());
			return provider;
		}
		
		@Bean
		AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
			return config.getAuthenticationManager();
		}
		
		
		@Bean
		PasswordEncoder passEncode() {
			return new BCryptPasswordEncoder();
		}
}
