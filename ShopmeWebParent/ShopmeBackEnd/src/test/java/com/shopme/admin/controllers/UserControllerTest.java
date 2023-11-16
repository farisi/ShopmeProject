package com.shopme.admin.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import com.shopme.admin.UserController;

@EnableAutoConfiguration
@WebMvcTest(UserController.class)
public class UserControllerTest {

	@Autowired
	MockMvc web;
	
	@Test
	public void testIndex() throws Exception {
		web.perform(get("/users"))
			.andExpect(status().isOk())
			.andExpect(view().name("users/index"));
	}
}
