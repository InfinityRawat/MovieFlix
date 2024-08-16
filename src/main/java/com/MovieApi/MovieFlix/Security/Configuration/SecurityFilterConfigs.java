package com.MovieApi.MovieFlix.Security.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.MovieApi.MovieFlix.Security.Service.AuthFilterService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity

public class SecurityFilterConfigs {
		
	private AuthFilterService authFilter;
	private AuthenticationProvider provider;
	
	
		public SecurityFilterConfigs(AuthFilterService authFilter, AuthenticationProvider provider) {
		super();
		this.authFilter = authFilter;
		this.provider = provider;
	}

		@Bean
		SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
			
			return http.csrf(csrf->csrf.disable())
					.authorizeHttpRequests(req->req.requestMatchers("/api/v1/auth/**","/forget/**")
							.permitAll().
							anyRequest()
							.authenticated())
					.sessionManagement(sess->sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
					.authenticationProvider(provider)
					.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
					.build();
		}
}
