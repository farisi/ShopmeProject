package com.shopme.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;

import com.shopme.admin.services.FilesStorageService;

import jakarta.annotation.Resource;

@SpringBootApplication
@EntityScan({"com.shopme.common.entities","com.shopme.admin.user"})
@EnableCaching
public class ShopmeBackEndApplication implements CommandLineRunner{
	
//	@Resource
//	FilesStorageService storageService;

	public static void main(String[] args) {
		//SpringApplication.run(ShopmeBackEndApplication.class, args);
		SpringApplication application = new SpringApplication(ShopmeBackEndApplication.class);
        application.setBannerMode(Banner.Mode.LOG);
        application.run(args);
	}
	
	@Override
	  public void run(String... arg) throws Exception {
//	    storageService.deleteAll();
	    //storageService.init();
	  }
}
