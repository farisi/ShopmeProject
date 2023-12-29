package com.shopme.admin.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;

import com.shopme.admin.controllers.auth.PasswordResetController;
import com.shopme.admin.requests.PasswordResetRequest;
import com.shopme.admin.services.PasswordResetService;
import com.shopme.admin.user.UserService;
import com.shopme.common.entities.PasswordReset;
import com.shopme.common.entities.User;

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

	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
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
	
	@Test
	public void editTest() throws Exception {
		PasswordResetRequest prr = new PasswordResetRequest();
		mockMvc.perform(get("/auth/reset_password/3a5a091c-b48c-41e5-812c-819898a16b1f")
				.flashAttr("user", prr))
		.andExpect(view().name("auths/form_reset_password"));
	}
	
	@Test
	public void successUpdateTest() throws Exception {
		User u = new User();
		u.setEmail(anyString());
		when(usrSrv.findByEmail(u.getEmail())).thenReturn(Optional.of(u));
		when(usrSrv.updateUserPassword(anyString(), any(User.class))).thenReturn(any(User.class));
		mockMvc.perform(patch("/auth/reset_password/3a5a091c-b48c-41e5-812c-819898a16b1f/admin@gmail.com")
				.param("password", "123456")
				.param("confirmPassword", "123456")
				.param("email","salmandriva@gmail.com")
				)
		.andExpect(status().is3xxRedirection())
		.andExpect(redirectedUrl("/"));
	}
	
	@Test
    void testUpdateValidationFailure() throws Exception {
        // Simulasi validasi gagal
        //when(usrSrv.findByEmail(any())).thenReturn(Optional.empty());

        mockMvc.perform(patch("/auth/reset_password/3a5a091c-b48c-41e5-812c-819898a16b1f/admin@gmail.com")
                .param("password", "password")
                .param("confirmPassword", "password")
                .param("email", ""))
        	
            .andExpect(status().is3xxRedirection()) // Anda bisa mengubah ini sesuai kebutuhan
            //.andExpect(model().attributeExists("user"))
            .andExpect(redirectedUrl("/auth/reset_password/3a5a091c-b48c-41e5-812c-819898a16b1f"))
//        .andExpect(status().isBadRequest()) // Expecting a 400 Bad Request status
//        .andExpect(view().name("auths/form_reset_password")) // Expecting the view name
//        .andExpect(model().hasErrors())
            .andDo(print());

        //verifyZeroInteractions(usrSrv, prSrv);
    }

    @Test
    void testUpdateValidationSuccess() throws Exception {
        // Simulasi validasi sukses
    	User u = new User();
		u.setEmail(anyString());
        when(usrSrv.findByEmail(u.getEmail())).thenReturn(Optional.of(u));
        when(usrSrv.updateUserPassword(any(), any())).thenReturn(any(User.class));

        mockMvc.perform(patch("/auth/reset_password/3a5a091c-b48c-41e5-812c-819898a16b1f/admin@gmail.com")
                .param("password", "password")
                .param("confirmPassword", "password")
                .param("email", "email@example.com"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/")); // Anda bisa mengubah ini sesuai kebutuhan
            

//        verify(usrSrv).updateUserPassword(eq("password"), any());
        //verifyNoMoreInteractions(usrSrv, passwordResetService);
    }

    @Test
    void testUpdateUserNotFound() throws Exception {
        // Simulasi user tidak ditemukan
        when(usrSrv.findByEmail(any())).thenReturn(Optional.empty());

        mockMvc.perform(patch("/auth/reset_password/token/email")
                .param("password", "password")
                .param("confirmPassword", "password")
                .param("email", "email@example.com"))
            .andExpect(status().is3xxRedirection()) // Anda bisa mengubah ini sesuai kebutuhan
            .andExpect(flash().attribute("fails", "Email is not found!"))
            .andExpect(view().name("redirect:/auth/reset_password/token"));

        //verifyZeroInteractions(passwordResetService);
    }
}