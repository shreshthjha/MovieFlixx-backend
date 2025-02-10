package com.movieflix.services;

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
		
		//to get name of the file
			String fileName = file.getOriginalFilename();
			
		//to get the file path
			String filePath = path + File.separator + fileName;
			
		//create a file object 
			File f =  new File(path);
			if(!f.exists()) {
				f.mkdir();
			}
		//copy the file to that path and upload the file to the path
			Files.copy(file.getInputStream(), Paths.get(filePath));
			
		return fileName;
	}

	@Override
	public InputStream getResourceFile(String path, String filename) throws FileNotFoundException {
		//to get the file path
		String filePath = path + File.separator + filename;
		return new FileInputStream(filePath);
	}

}
