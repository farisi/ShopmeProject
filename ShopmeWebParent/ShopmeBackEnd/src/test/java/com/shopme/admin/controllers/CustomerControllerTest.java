package com.shopme.admin.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import com.shopme.admin.CustomerController;

@EnableAutoConfiguration
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

	@Autowired
	MockMvc web;
	
	public void testAccessCustomerIndex() throws Exception {
		web.perform(get("/customers"))
		 	.andExpect(status().isOk());
	}
}
