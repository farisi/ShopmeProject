package com.shopme.admin.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {
	@ExceptionHandler(PageNotFoundException.class)
    //(HttpStatus.NOT_FOUND)
    public String handlePageNotFoundException(PageNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "index"; // Ganti dengan nama tampilan yang sesuai
    }
	
	@ExceptionHandler(CannotRemoveParentException.class)
	public String handleCannotRemoveParentException(CannotRemoveParentException ex, Model model) {
		return "errors/error-page";
	}
}
