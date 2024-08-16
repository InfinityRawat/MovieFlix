package com.MovieApi.MovieFlix.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadFile(String path, MultipartFile file) throws IOException {
		
//		get file name
		String filename = file.getOriginalFilename();
		
//		create path 
		String originalPath = path + File.separator + filename;
		System.out.println(originalPath+"is original path ----------------");
//		create File object to create a new file 
		File f = new File(path);
		
		if(!f.exists()) {
			f.mkdir();
		}
		
//		copy the file or upload file to path 
//		\resumeFolder\AmitRawat_resume.pdf
		
		System.out.println(originalPath);
		Files.copy(file.getInputStream(),Paths.get(originalPath),StandardCopyOption.REPLACE_EXISTING);
		
		return filename;
	}

	@Override
	public InputStream getResourceFile(String path, String Filename) throws FileNotFoundException {
		
		String filePath = path + File.separator + Filename;
		return new FileInputStream(filePath);
	}
	
	public String getMoviePoster(String path, String Filename) throws FileNotFoundException {
		
		String filePath = path + File.separator + Filename;
		return filePath;
	}

}
