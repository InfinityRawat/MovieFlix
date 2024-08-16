package com.MovieApi.MovieFlix.Exception;

public class MovieNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4723442351448135916L;

	public MovieNotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MovieNotFoundException(String message) {
		super("Movie with id does not fount");// TODO Auto-generated constructor stub
	}
	
	
	
		
	
}
