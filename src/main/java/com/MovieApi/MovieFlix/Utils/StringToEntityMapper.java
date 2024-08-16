package com.MovieApi.MovieFlix.Utils;

import org.springframework.stereotype.Service;

import com.MovieApi.MovieFlix.DTO.MovieDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class StringToEntityMapper {
	
		public MovieDto toMovieDto(String movie) throws JsonMappingException, JsonProcessingException {
			
				ObjectMapper oMap = new ObjectMapper();
				MovieDto value = oMap.readValue(movie, MovieDto.class);
				return value;
		}
}
