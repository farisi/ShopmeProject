package com.shopme.admin;

import java.io.File;
import java.io.IOException;

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

@Controller
@RequestMapping("/users/{id}")
public class UserImageUploadController {
	
	@Value("${upload.path}")
    private String uploadPath;
	
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
			System.out.println("upload Path " + uploadPath);
            // Mendapatkan path lengkap di server untuk menyimpan file
            String fullPath = uploadPath + file.getOriginalFilename();

            // Simpan file di server
            file.transferTo(new File(fullPath));

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded " + file.getOriginalFilename() + "!");

        } catch (IOException e) {
            e.printStackTrace();
        }
		 return "redirect:/users/" + id + "/images";
	}
}
