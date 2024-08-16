package com.MovieApi.MovieFlix.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.MovieApi.MovieFlix.DTO.MovieDto;
import com.MovieApi.MovieFlix.DTO.PageResponse;
import com.MovieApi.MovieFlix.Entity.Movie;

public interface MovieService {
	
	
		public MovieDto saveMovie(MovieDto movie, MultipartFile file) throws IOException;
		public MovieDto getMovie(Integer MovieId);
		public List<MovieDto> getAllMovie();
		
		public String deleteMovie(Integer MovieId)throws IOException;
		MovieDto updateMovie(Integer movieId, MovieDto movie, MultipartFile file) throws FileNotFoundException, IOException;
		
		PageResponse getDataInPages(Integer pages, Integer size);
		PageResponse getDataInPagesSorted(Integer pages, Integer size,String sortBy,String direction);
}
