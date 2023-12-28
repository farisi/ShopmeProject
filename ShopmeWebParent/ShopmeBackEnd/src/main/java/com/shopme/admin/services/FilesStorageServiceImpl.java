package com.shopme.admin.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

@Service
public class FilesStorageServiceImpl implements FilesStorageService{
	
	
	
	@Value("${upload.path}")
    private String uploadPath;
	
	private  Path root;
	private Path thumbnail;

	@Override
	public void init() {
		// TODO Auto-generated method stub
		try {
			 this.root  = Paths.get("src/main/resources/" + uploadPath);
			 thumbnail = Paths.get("src/main/resources/" + uploadPath + "/thumbnails/");
		      Files.createDirectories(root);
		      Files.createDirectories(thumbnail);
		    } catch (IOException e) {
		      throw new RuntimeException("Could not initialize folder for upload!");
		    }
		
	}

	@Override
	public void save(MultipartFile file, String name) {
		// TODO Auto-generated method stub
		try {
				Files.deleteIfExists(this.root.resolve(name));
				Files.copy(file.getInputStream(), this.root.resolve(name));
				System.out.println(" file " + this.root.resolve(name).toString());
				Thumbnails.of(this.root.resolve(name).toString()).size(100, 100).toFile(this.thumbnail.resolve(name).toFile());
				
		    } catch (Exception e) {
		      if (e instanceof FileAlreadyExistsException) {
		        throw new RuntimeException("A file of that name already exists.");
		      }
		      
		      if(e instanceof UnsupportedOperationException) {
		    	  throw new RuntimeException(" Operation not supported");
		      }

		      throw new RuntimeException(e.getMessage());
		    }
	}

	@Override
	public Resource load(String filename) {
		// TODO Auto-generated method stub
		try {
		      Path file = root.resolve(filename);
		      Resource resource = new UrlResource(file.toUri());

		      if (resource.exists() || resource.isReadable()) {
		        return resource;
		      } else {
		        throw new RuntimeException("Could not read the file!");
		      }
		    } catch (MalformedURLException e) {
		      throw new RuntimeException("Error: " + e.getMessage());
		    }
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		FileSystemUtils.deleteRecursively(root.toFile());
	}

	@Override
	public Stream<Path> loadAll() {
		// TODO Auto-generated method stub
		try {
		      return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
		    } catch (IOException e) {
		      throw new RuntimeException("Could not load the files!");
		    }
	}

}
