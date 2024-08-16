package com.MovieApi.MovieFlix.Security.Service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.MovieApi.MovieFlix.Security.Entity.RefreshToken;
import com.MovieApi.MovieFlix.Security.Entity.User;
import com.MovieApi.MovieFlix.Security.Entity.UserRole;
import com.MovieApi.MovieFlix.Security.Repos.RefreshTokenRepo;
import com.MovieApi.MovieFlix.Security.Repos.UserRepo;
import com.MovieApi.MovieFlix.Security.Utils.AuthResponse;
import com.MovieApi.MovieFlix.Security.Utils.LoginRequest;
import com.MovieApi.MovieFlix.Security.Utils.RegisterRequest;

@Service
public class AuthService {


	private UserDetailsService service;
	private RefreshTokenService refServ;
	private JwtService jwtServ;
	private RefreshTokenRepo refRepo;
	private UserRepo userRepo;
	private AuthenticationManager manager;
	private PasswordEncoder encoder;
	
	
	
	public AuthService(UserDetailsService service, RefreshTokenService refServ, JwtService jwtServ,
			RefreshTokenRepo refRepo, UserRepo userRepo, AuthenticationManager manager, PasswordEncoder encoder) {
		super();
		this.service = service;
		this.refServ = refServ;
		this.jwtServ = jwtServ;
		this.refRepo = refRepo;
		this.userRepo = userRepo;
		this.manager = manager;
		this.encoder = encoder;
	}

	public AuthResponse register(RegisterRequest req) {
	
			User user = User.builder().name(req.getName())
					.email(req.getEmail())
					.password(encoder.encode(req.getPassword()))
					.role(UserRole.USER)
					.build();
					
		
			User saveUser = userRepo.save(user);
		String token = jwtServ.generateToken(saveUser);
		RefreshToken refreshToken = refServ.createRefreshToken(saveUser.getEmail());
		
		return  AuthResponse.builder().accessToken(token).refreshToken(refreshToken.getRefreshToken()).build();
		
	}
	
	public AuthResponse login(LoginRequest req) {
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword());
		manager.authenticate(auth);
		User user = userRepo.findByEmail(req.getEmail()).orElseThrow(()->new RuntimeException("user not found, please register first!"));
		
		String token = jwtServ.generateToken(user);
		RefreshToken refreshToken = refServ.createRefreshToken(user.getEmail());
		
		return  AuthResponse.builder().accessToken(token).refreshToken(refreshToken.getRefreshToken()).build();
		
	}
}
