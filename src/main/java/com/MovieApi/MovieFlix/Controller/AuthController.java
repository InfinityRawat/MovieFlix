package com.MovieApi.MovieFlix.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MovieApi.MovieFlix.Security.Entity.RefreshToken;
import com.MovieApi.MovieFlix.Security.Service.AuthService;
import com.MovieApi.MovieFlix.Security.Service.JwtService;
import com.MovieApi.MovieFlix.Security.Service.RefreshTokenService;
import com.MovieApi.MovieFlix.Security.Utils.AuthResponse;
import com.MovieApi.MovieFlix.Security.Utils.LoginRequest;
import com.MovieApi.MovieFlix.Security.Utils.RefreshTokenRequest;
import com.MovieApi.MovieFlix.Security.Utils.RegisterRequest;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

		private RefreshTokenService refServ;
		private JwtService jwtServ;
		private AuthService authService;
	
		


		public AuthController(RefreshTokenService refServ, JwtService jwtServ, AuthService authService) {
			super();
			this.refServ = refServ;
			this.jwtServ = jwtServ;
			this.authService = authService;
		}

		@PostMapping("/login")
		public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
				
			AuthResponse login = authService.login(request);
			
			return new ResponseEntity<AuthResponse>(login,HttpStatus.ACCEPTED);
		}
		
		@PostMapping("/register")
		public ResponseEntity<AuthResponse> login(@RequestBody RegisterRequest request){
				
			 AuthResponse register = authService.register(request);
			
			return new ResponseEntity<AuthResponse>(register,HttpStatus.ACCEPTED);
		}
		
		@PostMapping("/refresh")
		public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshTokenRequest request ){
				
			 RefreshToken register = refServ.validateRefreshToken(request.getRefreshToken());
			 String token = jwtServ.generateToken(register.getUser());
			 
			return new ResponseEntity<AuthResponse>
			(AuthResponse.builder()
					.accessToken(token)
					.refreshToken(register.getRefreshToken()).build(),HttpStatus.ACCEPTED);
		}
}


















