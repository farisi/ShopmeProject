package com.shopme.admin.exceptions;

public class PageNotFoundException extends RuntimeException {
	public PageNotFoundException(String message) {
       super(message);
    }
}
