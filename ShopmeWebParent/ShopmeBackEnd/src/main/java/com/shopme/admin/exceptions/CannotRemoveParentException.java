package com.shopme.admin.exceptions;

public class CannotRemoveParentException extends RuntimeException  {
	private static final long serialVersionUID = -6979041640409866012L;
	public CannotRemoveParentException(String message) {
	       super(message);
	}
}
