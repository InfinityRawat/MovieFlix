package com.MovieApi.MovieFlix.Controller;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.MovieApi.MovieFlix.Service.FileService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/file")
public class FileController {
	@Autowired
	private FileService fileServ;
	@Value("${movie.path}")
	private String path;
	
	@PostMapping
	public ResponseEntity<String> save(@RequestPart MultipartFile file) {
		String uploadFile = null;
		
		try {
			 uploadFile = fileServ.uploadFile(path, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(uploadFile);

	}
	
	@GetMapping("/{filename}")
	public void getMovie(@PathVariable String filename,HttpServletResponse response) throws IOException {
//		MovieDto movie = service.getMovie(movieId);
		InputStream resourceFile = fileServ.getResourceFile(path, filename);
		response.setContentType(MediaType.IMAGE_PNG_VALUE);
		StreamUtils.copy(resourceFile, response.getOutputStream());
		
	}

}
