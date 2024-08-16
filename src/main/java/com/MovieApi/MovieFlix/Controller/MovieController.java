package com.MovieApi.MovieFlix.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.MovieApi.MovieFlix.DTO.MovieDto;
import com.MovieApi.MovieFlix.DTO.PageResponse;
import com.MovieApi.MovieFlix.Entity.Movie;
import com.MovieApi.MovieFlix.Service.MovieService;
import com.MovieApi.MovieFlix.Utils.StringToEntityMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;


@RestController
@CrossOrigin(value = "http://localhost:5173/")
@RequestMapping("/movie")
public class MovieController {
	
	@Autowired
	private MovieService service;
	@Autowired
	private StringToEntityMapper objMapper;
	
	@Value("${movie.path}")
	private String path;
	

	

		@PostMapping(path =  "/addMovie",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
		public ResponseEntity<MovieDto> save(@RequestPart String movie,@RequestPart MultipartFile file) throws JsonMappingException, JsonProcessingException {
			
			MovieDto movieDto = objMapper.toMovieDto(movie);
			
			MovieDto uploadFile=null;
			try {
				 uploadFile = service.saveMovie(movieDto, file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return ResponseEntity.ok(uploadFile);

		}
		
		@PutMapping(path =  "/updateMovie/{id}",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
		public ResponseEntity<MovieDto> update(@PathVariable Integer id, @RequestPart String movie,@RequestPart MultipartFile file) throws JsonMappingException, JsonProcessingException {
			
			MovieDto movieDto = objMapper.toMovieDto(movie);
			
			MovieDto uploadFile=null;
			try {
				 uploadFile = service.updateMovie(id,movieDto, file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return ResponseEntity.ok(uploadFile);

		}
		@GetMapping("/{id}")
		public ResponseEntity<MovieDto> getMovie(@PathVariable Integer id)  {
//			MovieDto movie = service.getMovie(movieId);
			MovieDto resourceFile = service.getMovie(id);
			return ResponseEntity.ok(resourceFile);
			
		}
		
		@GetMapping("/allMovie")
		
		public ResponseEntity<List<MovieDto>> getAllMovie(){
				List<MovieDto> allMovie = service.getAllMovie();
				
				return ResponseEntity.ok(allMovie);
		}
		
		@DeleteMapping
		public ResponseEntity<String> deleteMovie(@RequestParam Integer id){
			String deleteMovie=null;
				try {
					 deleteMovie= service.deleteMovie(id);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return ResponseEntity.ok(deleteMovie);
		}
		
		@GetMapping("/pages")
		public PageResponse getByPage(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size){
					
			PageResponse dataInPages = service.getDataInPages(page, size);
			return  dataInPages;
		}
		
		@GetMapping("/pages/sort")
		public PageResponse getByPageSort(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size,@RequestParam(defaultValue = "movieId") String sortBy,@RequestParam(defaultValue = "asc") String direction){
					
			PageResponse dataInPages = service.getDataInPagesSorted(page, size,sortBy,direction);
			return  dataInPages;
		}
}




















