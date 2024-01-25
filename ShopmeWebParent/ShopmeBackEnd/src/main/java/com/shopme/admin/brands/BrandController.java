package com.shopme.admin.brands;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.common.entities.Brand;

@RequestMapping("/brands")
@Controller
public class BrandController {
	
	@Autowired
	private BrandService brandSrv;
	
	@GetMapping("")
	public String index(Model ui) {
		List<Brand> brands = brandSrv.brands();
		return "brands/index";
	}
	
	@GetMapping("/create")
	public String create(Brand brand,Model ui) {
		ui.addAttribute("brand", new Brand());
		return "brands/create";
	}
	
	@PostMapping("")
	public String store(Brand brand, @RequestParam("profile_foto") MultipartFile file,RedirectAttributes redirect) {
		return "redirect:/brands";
	}
	
	@GetMapping("/{id}")
	public String show(@PathVariable Integer id, Model ui) {
		return "brands/show";
	}
	
	@GetMapping("/{id}/edit")
	public String edit(@PathVariable Integer id, Model ui) {
		return "brands/edit";
	}
	
	@PatchMapping("/{id}")
	public String update(@PathVariable Integer id, Brand brand, RedirectAttributes redirect) {
		return "redirect:/brands";
	}
}
