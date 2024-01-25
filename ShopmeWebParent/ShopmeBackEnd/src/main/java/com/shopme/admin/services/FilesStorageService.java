package com.shopme.admin.services;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;

public interface FilesStorageService {
		public void init();

	  public void save(MultipartFile file,String path,String name);

	  public Resource load(String filename,String path);
	  
	  public void deleteAll(String path);

	  public Stream<Path> loadAll(String rootpath);
}
