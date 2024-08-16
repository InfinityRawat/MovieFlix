package com.MovieApi.MovieFlix.Exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorStatus {

	 	private HttpStatus status;
	    private String message;
	    private String details;
}
