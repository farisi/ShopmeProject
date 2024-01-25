package com.shopme.site.exceptions;

public class PageNotFoundException extends RuntimeException {
	public PageNotFoundException(String message) {
       super(message);
    }
}