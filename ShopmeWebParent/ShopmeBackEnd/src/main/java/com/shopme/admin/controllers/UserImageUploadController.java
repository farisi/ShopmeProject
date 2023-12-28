package com.shopme.admin.controllers;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.services.FilesStorageService;
import com.shopme.admin.user.UserService;
import com.shopme.common.entities.User;

@Controller
@RequestMapping("/users/{id}")
public class UserImageUploadController {
	
	
	@Autowired
	FilesStorageService storageService;
	
	@Autowired
	UserService usrSrv;
	
	@GetMapping("/images")
	public String edit(@PathVariable Integer id,Model ui) {
		ui.addAttribute("id",id);
		return "users/upload_image";
	}
	
	@PatchMapping("/images")
	public String update(@PathVariable Integer id, @RequestParam("profile_foto") MultipartFile file, RedirectAttributes redirectAttributes)
	{
		if(file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/users/" + id + "/images";
		}
		
		try {
			int index = file.getOriginalFilename().lastIndexOf(".");
			String extensi = file.getOriginalFilename().substring(index + 1);
			User user = usrSrv.findUserById(id);
			String namafile ="fotos_"+user.getId().toString()+"."+extensi;			
			storageService.save(file,namafile);
			user.setPhotos(namafile);
			usrSrv.save(user);
            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded " + namafile + "!");

        } catch (Exception  e) {
            e.printStackTrace();
        }
		 return "redirect:/users/" + id;
	}
}
