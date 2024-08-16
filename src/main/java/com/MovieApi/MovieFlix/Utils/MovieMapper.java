package com.MovieApi.MovieFlix.Utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.MovieApi.MovieFlix.DTO.MovieDto;
import com.MovieApi.MovieFlix.Entity.Movie;

@Service
public class MovieMapper {
	
	@Value("${movie.baseUrl}")
	private String baseUrl;
	
	@Value("${movie.path}")
	private String path;
	
		public Movie mapToEntity(MovieDto dto) {
			
				return Movie.builder()
						.movieId(dto.getMovieId())
						.director(dto.getDirector())
						.MovieCast(dto.getMovieCast())
						.movieName(dto.getMovieName())
						.poster(dto.getPoster())
						.releaseYear(dto.getReleaseYear())
						.Studio(dto.getStudio())
						.build();
		}
		
		public MovieDto mapToDto(Movie dto) {
			
			return MovieDto.builder()
					.movieId(dto.getMovieId())
					.director(dto.getDirector())
					.MovieCast(dto.getMovieCast())
					.movieName(dto.getMovieName())
					.poster(dto.getPoster())
					.posterUrl(baseUrl+"/file/"+dto.getPoster())
					.releaseYear(dto.getReleaseYear())
					.Studio(dto.getStudio())
					.build();
	}
		
	public List<MovieDto> mapToListDto(List<Movie> dto){
		 return dto.stream().map(e->mapToDto(e)).toList();
	}
}
