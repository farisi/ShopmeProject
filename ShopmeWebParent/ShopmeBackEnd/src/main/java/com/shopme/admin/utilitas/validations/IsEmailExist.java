package com.shopme.admin.utilitas.validations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IsEmailExistValidation.class)
public @interface IsEmailExist {
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payloads() default {};
}
