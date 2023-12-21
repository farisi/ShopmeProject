package com.shopme.admin.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.test.web.servlet.MockMvc;

import com.shopme.admin.services.PasswordResetService;
import com.shopme.admin.user.UserService;
import com.shopme.common.entities.PasswordReset;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PasswordResetControllerTest {

	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
    JavaMailSender jms;
	
	@MockBean
	UserService usrSrv;
	
	@MockBean
	private PasswordResetService prSrv;
	
	@MockBean
	DaoAuthenticationProvider authProvider;
	
	@Test
	public void createTest() throws Exception {
		mockMvc.perform(get("/auth/reset_password"))
		.andExpect(view().name("auths/reset_password"))
		.andExpect(status().isOk());
	}
	
	@Test
	public void storeTest() throws Exception {
		
		when(prSrv.save( any(PasswordReset.class))).thenReturn(any(PasswordReset.class));
		
		mockMvc.perform(post("/auth/reset_password")
				.param("email", "salmandriva@gmail.com")
				)
		.andExpect(status().is3xxRedirection());
	}
}