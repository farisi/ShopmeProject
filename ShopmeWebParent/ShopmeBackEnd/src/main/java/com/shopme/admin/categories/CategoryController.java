package com.shopme.admin.categories;

import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.services.FilesStorageService;
import com.shopme.common.entities.Category;

import jakarta.validation.Valid;

@RequestMapping("/categories")
@Controller
public class CategoryController {

	@Autowired
	private CategoryService categorySrv;
	
	@Autowired
	FilesStorageService storageService;
	
	final Logger log = LoggerFactory.getLogger(CategoryController.class);
	
	private static final DateTimeFormatter FORMATTER
    = DateTimeFormatter.ofPattern("yyMMddHHmmss");
	
	@GetMapping("")
	public String index(Model ui) {
		ui.addAttribute("categories",categorySrv.categories());
		return "categories/index";
	}
	
	@GetMapping("/create")
	public String create(Category category, Model ui) {
		ui.addAttribute("categories",categorySrv.listCategoryUseByForm());
		ui.addAttribute("category",new Category());
		return "categories/create";
	}
	
	@PostMapping("")
	public String store(@RequestParam("profile_foto") MultipartFile profile_foto, 
			@Valid @ModelAttribute Category category, 
			BindingResult valid, Model ui, RedirectAttributes redirect) {
		
		String namafile="image-thumbnail.png";
		if(!profile_foto.isEmpty()) {
			String filename =StringUtils.cleanPath(profile_foto.getOriginalFilename());
			log.info("filename adalah " + filename);
			int index = profile_foto.getOriginalFilename().lastIndexOf(".");
			String extensi = profile_foto.getOriginalFilename().substring(index + 1);
			LocalDateTime time = LocalDateTime.now(ZoneId.systemDefault())
		            .truncatedTo(ChronoUnit.SECONDS);
			namafile = time.format(FORMATTER)+"."+extensi;
			storageService.save(profile_foto, "categories", namafile);
		}
		
		if(valid.hasErrors()) {
			log.info(" validasi tidak lewat");
			valid.getAllErrors().stream().forEach(e->log.info(" error {e}" + e.toString()));
			ui.addAttribute("categories",categorySrv.categories());
			ui.addAttribute("category",category);
			return "categories/create";
		}
		
		log.info(" sebelum proses print terjadi ");
		category.setImage(namafile);
		categorySrv.save(category);
		redirect.addFlashAttribute("success", "Category has added!");
		return "redirect:/categories";
	}
	
	@PatchMapping("/{id}/enable")
	public String doEnable(@PathVariable("id") int id, RedirectAttributes redirect) {
		Optional<Category> optcat = categorySrv.findOne(id);
		if(optcat.isPresent()) {
			Category cat = optcat.get();
			cat.setEnabled(true);
			categorySrv.save(cat);
			redirect.addFlashAttribute("success", "Success to enable " + cat.getName());
		}
		return "redirect:/categories";
	}
	
	@PatchMapping("/{id}/disable")
	public String doDisable(@PathVariable("id") int id, RedirectAttributes redirect) {
		Optional<Category> optcat = categorySrv.findOne(id);
		if(optcat.isPresent()) {
			Category cat = optcat.get();
			cat.setEnabled(false);
			categorySrv.save(cat);
			redirect.addFlashAttribute("success", "Success to Disabled " + cat.getName());
		}
		return "redirect:/categories";
	}
}
