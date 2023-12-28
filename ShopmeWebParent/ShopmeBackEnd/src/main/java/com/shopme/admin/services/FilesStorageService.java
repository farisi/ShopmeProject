package com.shopme.admin.services;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;

public interface FilesStorageService {
		public void init();

	  public void save(MultipartFile file,String name);

	  public Resource load(String filename);
	  
	  public void deleteAll();

	  public Stream<Path> loadAll();
}
