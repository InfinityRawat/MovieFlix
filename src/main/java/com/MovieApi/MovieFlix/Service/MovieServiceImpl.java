package com.MovieApi.MovieFlix.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.MovieApi.MovieFlix.DTO.MovieDto;
import com.MovieApi.MovieFlix.DTO.PageResponse;
import com.MovieApi.MovieFlix.Entity.Movie;
import com.MovieApi.MovieFlix.Exception.MovieNotFoundException;
import com.MovieApi.MovieFlix.Repository.MovieRepo;
import com.MovieApi.MovieFlix.Utils.MovieMapper;

import lombok.AllArgsConstructor;

@Service
public class MovieServiceImpl implements MovieService {

	private MovieRepo repo;

	@Value("${movie.path}")
	private String path;
	private FileService fileService;
	private MovieMapper mapper;

	@Value("${movie.baseUrl}")
	private String baseUrl;

	public MovieServiceImpl(MovieRepo repo, FileService fileService, MovieMapper mapper) {
		super();
		this.repo = repo;
		this.fileService = fileService;
		this.mapper = mapper;
	}

	@Override
	public MovieDto saveMovie(MovieDto movie, MultipartFile file) throws FileAlreadyExistsException {

		if (Files.exists(Paths.get(path + File.pathSeparator + file.getOriginalFilename()))) {
			throw new FileAlreadyExistsException("File already exist, please give new file name");
		}

		String filename = null;
		try {
			filename = fileService.uploadFile(path, file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		movie.setPoster(filename);

//			mapping dto to entity

		Movie mapToEntity = mapper.mapToEntity(movie);

		Movie save = repo.save(mapToEntity);

//			generate a url 

		String posterUrl = baseUrl + "/file/" + filename;

		MovieDto response = mapper.mapToDto(mapToEntity);
		response.setPosterUrl(posterUrl);

		return response;
	}

	@Override
	public MovieDto getMovie(Integer movieId) {
		Movie movie = repo.findById(movieId)
				.orElseThrow(() -> new MovieNotFoundException("Movie with the id does not exist"));
//		Mapper 
		MovieDto dto = mapper.mapToDto(movie);
		return dto;
	}

	public List<MovieDto> getAllMovie() {
		List<Movie> allMovie = repo.findAll();
		return mapper.mapToListDto(allMovie);
	}

	public String deleteMovie(Integer movieId) throws IOException {
		Movie movie = repo.findById(movieId)
				.orElseThrow(() -> new MovieNotFoundException("movie with id " + movieId + " is not available"));
		repo.delete(movie);

		Path path2 = Paths.get(movie.getPoster());
		System.out.println(path2.toString());
		Files.deleteIfExists(path2);
		return "Movie is deleted with id " + movie.getMovieId();
	}

	@Override
	@Transactional
	public MovieDto updateMovie(Integer movieId, MovieDto movie, MultipartFile file) throws IOException {

		Path filePath = Path.of(path + File.separator + file.getOriginalFilename());
		System.out.println(filePath);
		if (Files.exists(filePath)) {
			Files.deleteIfExists(filePath);

		}
		String uploadFile = fileService.uploadFile(path, file);

		movie.setPoster(uploadFile);
		System.out.println("movie upload file poster " + uploadFile);
//			mapping dto to entity

		Movie newValues = mapper.mapToEntity(movie);
		Movie oldValues = repo.findById(movieId).orElseThrow(() -> new MovieNotFoundException("Movie not present"));

		oldValues.setDirector(newValues.getDirector());
		oldValues.setMovieCast(newValues.getMovieCast());
		oldValues.setMovieName(newValues.getMovieName());
		oldValues.setPoster(newValues.getPoster());
		oldValues.setReleaseYear(newValues.getReleaseYear());
		oldValues.setStudio(newValues.getStudio());

		Movie save = repo.save(oldValues);

		MovieDto response = mapper.mapToDto(oldValues);

		return response;
	}

	@Override
	public PageResponse getDataInPages(Integer page, Integer size) {
		
		Pageable pageable = PageRequest.of(page, size);
		
//		making pagenation request by sending pageable in repository
// 		it return Page interface which contain important information about pages coming from repo
		Page<Movie> pages = repo.findAll(pageable);
		List<Movie> content = pages.getContent();

		List<MovieDto> list = mapper.mapToListDto(content);

		return PageResponse.builder().entities(list).isLast(pages.isLast()).totalPage(pages.getTotalPages())
				.pageNumber(page).pageSize(size).build();
	}

	@Override
	public PageResponse getDataInPagesSorted(Integer page, Integer size, String sortBy, String direction) {

		Sort sort = (direction.equalsIgnoreCase("dsc"))?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(page, size, sort);
		Page<Movie> pages = repo.findAll(pageable);

		List<Movie> content = pages.getContent();
		List<MovieDto> list = mapper.mapToListDto(content);

		return PageResponse.builder().entities(list).isLast(pages.isLast()).totalPage(pages.getTotalPages())
				.pageNumber(page).pageSize(size).build();
	}
}
