package com.MovieApi.MovieFlix.Exception;

import java.nio.file.FileAlreadyExistsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;


@ControllerAdvice
public class GlobalException {
	
    private static final Logger logger = LoggerFactory.getLogger(GlobalException.class);

		
	@ExceptionHandler(MovieNotFoundException.class)
	public ResponseEntity<ErrorStatus> movieNotFound(MovieNotFoundException exception,WebRequest request){
		
		ErrorStatus error = new ErrorStatus();
		error.setStatus(HttpStatus.BAD_REQUEST);
		error.setMessage(exception.getLocalizedMessage());
		error.setDetails(request.getDescription(false));
		
		return new ResponseEntity<ErrorStatus>(error,HttpStatus.BAD_REQUEST);
		
	}
	
	
	
	@ExceptionHandler(FileAlreadyExistsException.class)
	public ResponseEntity<ErrorStatus> fileAlreadyExist(FileAlreadyExistsException exception,WebRequest request){
		
		ErrorStatus error = new ErrorStatus();
		error.setStatus(HttpStatus.BAD_REQUEST);
		error.setMessage(exception.getLocalizedMessage());
		error.setDetails(request.getDescription(false));
		
		return new ResponseEntity<ErrorStatus>(error,HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ValidationViolationException.class)
	public ResponseEntity<ErrorStatus> validationException(ValidationViolationException exception, WebRequest request ){
		
		ErrorStatus error = new ErrorStatus();
		error.setStatus(HttpStatus.BAD_REQUEST);
		error.setMessage(exception.getLocalizedMessage());
		error.setDetails(request.getDescription(false));
		
		logger.info(exception.getLocalizedMessage());

		return new ResponseEntity<ErrorStatus>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(JsonMappingException.class)
	public ResponseEntity<ErrorStatus> jsonMapping(JsonMappingException exception, WebRequest request){
//	
//	MovieException exc = new MovieException();
//	exc.setStatusCode(HttpStatus.BAD_REQUEST);
//	exc.setDescription(exception.getLocalizedMessage());
//	
	ErrorStatus error = new ErrorStatus();
	error.setStatus(HttpStatus.BAD_REQUEST);
	error.setMessage(exception.getLocalizedMessage());
	error.setDetails(request.getDescription(false));
	
	return new ResponseEntity<ErrorStatus>(error,HttpStatus.BAD_REQUEST);
	}
	
	
	
	
	@ExceptionHandler(JsonProcessingException.class)
	public ResponseEntity<ErrorStatus> jsonParser(JsonProcessingException exception,WebRequest request){
	
		ErrorStatus error = new ErrorStatus();
		error.setStatus(HttpStatus.BAD_REQUEST);
		error.setMessage(exception.getLocalizedMessage());
		error.setDetails(request.getDescription(false));
		
		return new ResponseEntity<ErrorStatus>(error,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorStatus> allExceptions(Exception exception,WebRequest request){
	
		ErrorStatus error = new ErrorStatus();
		error.setStatus(HttpStatus.BAD_REQUEST);
		error.setMessage(exception.getLocalizedMessage());
		error.setDetails(request.getDescription(true));
		
		return new ResponseEntity<ErrorStatus>(error,HttpStatus.BAD_REQUEST);
	}

}
