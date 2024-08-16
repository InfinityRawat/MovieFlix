package com.MovieApi.MovieFlix.Security.Utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AuthResponse {
	private String accessToken;
	private String refreshToken;
	
}
