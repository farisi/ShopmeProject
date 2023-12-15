package com.shopme.admin.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.springframework.http.HttpStatus;

//@ControllerAdvice
public class ExceptionControllerAdvice {
	//@ExceptionHandler(PageNotFoundException.class)
    //(HttpStatus.NOT_FOUND)
    public String handlePageNotFoundException(PageNotFoundException ex, Model model) {
        //model.addAttribute("errorMessage", ex.getMessage());
        return "errors/error-page"; // Ganti dengan nama tampilan yang sesuai
    }
}
