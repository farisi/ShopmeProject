package com.shopme.admin.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.test.web.servlet.MockMvc;

import com.shopme.admin.exceptions.PageNotFoundException;
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
	public void storeWithSuccess()throws Exception {
		PasswordReset pr = new PasswordReset();
		pr.setEmail("valid@example.com");
		
		User u = new User();
		u.setEmail("valid@example.com");
		
		when(usrSrv.findByEmail(pr.getEmail())).thenReturn(Optional.of(u));
		when(prSrv.save( any(PasswordReset.class))).thenReturn(any(PasswordReset.class));
		
		mockMvc.perform(post("/auth/reset_password")
				.param("email", pr.getEmail())
				)
		.andExpect(status().is3xxRedirection());
		
		verify(usrSrv).findByEmail(pr.getEmail());
	}
	
	@Test
	public void storewithInvalidEmailTest() throws Exception {
		PasswordReset pr = new PasswordReset();
		pr.setEmail("valid@example.com");
		
		when(usrSrv.findByEmail(pr.getEmail())).thenReturn(Optional.empty());
		when(prSrv.save( any(PasswordReset.class))).thenReturn(any(PasswordReset.class));
		
		mockMvc.perform(post("/auth/reset_password")
				.param("email", "salmandriva@gmail.com")
				)
		.andExpect(status().isOk())
		.andExpect(result -> {
            // Pastikan bahwa exception yang dilempar adalah PageNotFoundException
            Throwable exception = result.getResolvedException();
            Assertions.assertTrue(exception instanceof PageNotFoundException);
            Assertions.assertEquals("Your email could not found", exception.getMessage());
        });
		;
	}
	
	@Test
	public void editValidTokenAndValidEmail() throws Exception {
		
		UUID validToken = UUID.randomUUID();
		PasswordResetRequest prr = new PasswordResetRequest();
		prr.setEmail("valid@example.com");
		prr.setToken(validToken);
		PasswordReset pr = new PasswordReset();
		pr.setEmail("valid@example.com");
		pr.setToken(validToken);
		
		User u = new User();
		u.setEmail("test@example.com");
		
		when(prSrv.findByToken(validToken)).thenReturn(Optional.of(pr));
		when(prSrv.isTokenValid(pr, Duration.ofHours(1))).thenReturn(true);
		when(usrSrv.findByEmail(prr.getEmail())).thenReturn(Optional.of(u));
		
		mockMvc.perform(get("/auth/reset_password/" + validToken)
				.flashAttr("user", prr))
		.andExpect(view().name("auths/form_reset_password"));
	}
	
	@Test
	public void editInvalidToken() throws Exception {
		UUID invalidToken = UUID.randomUUID();
		when(prSrv.findByToken(invalidToken)).thenReturn(Optional.empty());
		mockMvc.perform(get("/auth/reset_password/" + invalidToken))
		.andExpect(status().isOk())
		.andExpect(result -> {
            // Pastikan bahwa exception yang dilempar adalah PageNotFoundException
            Throwable exception = result.getResolvedException();
            Assertions.assertTrue(exception instanceof PageNotFoundException);
            Assertions.assertEquals("Invalid Token", exception.getMessage());
        });
				
	}
	
	@Test
	public void editExpireToken()throws Exception {
		UUID validToken = UUID.randomUUID();
		PasswordResetRequest prr = new PasswordResetRequest();
		prr.setEmail("valid@example.com");
		prr.setToken(validToken);
		PasswordReset pr = new PasswordReset();
		pr.setEmail("valid@example.com");
		pr.setToken(validToken);
		
		User u = new User();
		u.setEmail("test@example.com");
		
		when(prSrv.findByToken(validToken)).thenReturn(Optional.of(pr));
		when(prSrv.isTokenValid(pr, Duration.ofHours(1))).thenReturn(false);
		
		
		mockMvc.perform(get("/auth/reset_password/" + validToken))
		.andExpect(status().isOk())
		.andExpect(result -> {
            // Pastikan bahwa exception yang dilempar adalah PageNotFoundException
            Throwable exception = result.getResolvedException();
            Assertions.assertTrue(exception instanceof PageNotFoundException);
            Assertions.assertEquals("Invalid or expired token", exception.getMessage());
        });
	}
	
	@Test
	public void editInvalidEmail() throws Exception {
		UUID validToken = UUID.randomUUID();
		PasswordResetRequest prr = new PasswordResetRequest();
		prr.setEmail("invalid@example.com");
		prr.setToken(validToken);
		PasswordReset pr = new PasswordReset();
		pr.setEmail("invalid@example.com");
		pr.setToken(validToken);
		
		User u = new User();
		u.setEmail("invalid@example.com");
		
		when(prSrv.findByToken(validToken)).thenReturn(Optional.of(pr));
		when(prSrv.isTokenValid(pr, Duration.ofHours(1))).thenReturn(true);
		when(usrSrv.findByEmail(prr.getEmail())).thenReturn(Optional.empty());
		
		mockMvc.perform(get("/auth/reset_password/" + validToken))
		.andExpect(status().isOk())
		.andExpect(result -> {
            // Pastikan bahwa exception yang dilempar adalah PageNotFoundException
            Throwable exception = result.getResolvedException();
            Assertions.assertTrue(exception instanceof PageNotFoundException);
            Assertions.assertEquals("Invalid Email", exception.getMessage());
        });
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

		PasswordResetRequest invalidUser = new PasswordResetRequest();
	    invalidUser.setPassword("pass");
	    invalidUser.setConfirmPassword("pass2");
	    invalidUser.setEmail("invalidemail");

	    mockMvc.perform(patch("/auth/reset_password/3a5a091c-b48c-41e5-812c-819898a16b1f/admin@gmail.com")
	            .param("password", invalidUser.getPassword())
	            .param("confirmPassword", invalidUser.getConfirmPassword())
	            .param("email", invalidUser.getEmail()))
	            .andExpect(status().isOk())
	            .andExpect(model().size(1))  // Expecting a success status because it stays on the same page
	            .andExpect(view().name("auths/form_reset_password")) ; // Expecting the same view
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
    }

    @Test
    void testUpdateUserNotFound() throws Exception {
        // Simulasi user tidak ditemukan
        when(usrSrv.findByEmail(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(patch("/auth/reset_password/3a5a091c-b48c-41e5-812c-819898a16b1f/admin@gmail.com")
                .param("password", "password")
                .param("confirmPassword", "password")
                .param("email", "admin@gmail.com"))
        .andExpect(result -> {
            // Pastikan bahwa exception yang dilempar adalah PageNotFoundException
            Throwable exception = result.getResolvedException();
            Assertions.assertTrue(exception instanceof PageNotFoundException);
            Assertions.assertEquals("Your email could not be found", exception.getMessage());
        });

		// Verifikasi bahwa metode service dipanggil
		verify(usrSrv).findByEmail(anyString());
           
    }
}