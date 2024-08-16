package com.MovieApi.MovieFlix.DTO;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieDto {


	private Integer movieId;
	
	@NotBlank(message = "Please provide movie name")
	private String movieName;
	
	@NotBlank(message = "Please provide director name")
	private String director;
	
	@NotBlank(message = " Studio name is missing")
	private String Studio;
	
	@NotBlank(message="Please provide Movie case names")
	private Set<String> MovieCast;
	
	private Integer releaseYear;
	
	@NotBlank(message = "Please provide movie poster")
	private String poster;
	
	private String posterUrl;
	
}
