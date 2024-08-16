package com.MovieApi.MovieFlix.Security.Service;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthFilterService extends OncePerRequestFilter {

	private JwtService service;
	private UserDetailsService userService;
	
	
	public AuthFilterService(JwtService service, UserDetailsService userService) {
		super();
		this.service = service;
		this.userService = userService;
	}


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String jwttoken = request.getHeader("Authorization");
		
		if(jwttoken!=null&&jwttoken.startsWith("Bearer ")) {
			
			String token = jwttoken.substring(7);
			String username = service.extractUsername(token);
			
			if(username!=null||SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userByUsername = userService.loadUserByUsername(username);
			
			if(service.isTokenValid(token, userByUsername)) {
				UsernamePasswordAuthenticationToken authToken = 
						new UsernamePasswordAuthenticationToken(userByUsername,null,userByUsername.getAuthorities());
				
				authToken.setDetails( new WebAuthenticationDetailsSource().buildDetails(request));
	            SecurityContextHolder.getContext().setAuthentication(authToken);

			}

			
		}
							
	}
		filterChain.doFilter(request, response);

	}

}
