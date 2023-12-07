package com.shopme.admin.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.validation.BindingResult;

import com.shopme.admin.UserController;
import com.shopme.admin.requests.UserRequest;
import com.shopme.admin.user.UserService;
import com.shopme.common.entities.User;

@WebMvcTest(UserController.class)
@ExtendWith(MockitoExtension.class)
public class UserControllerSecondTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Mock
    private BindingResult bindingResult;
	
	@MockBean
	UserService userSrv;
	

	@Test
    public void testStoreWithValidUser() throws Exception {
        // Persiapkan objek UserRequest yang valid
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("test@example.com");
        userRequest.setFirstName("John");
        userRequest.setLastName("Doe");
        userRequest.setPassword("securePassword");
        userRequest.setConfirmPassword("securePassword");

        // Mocking behavior of UserService
        Mockito.when(userSrv.save(Mockito.any(UserRequest.class))).thenReturn(new User());
        
        lenient().when(bindingResult.hasErrors()).thenReturn(false);

        // Lakukan pengujian dengan MockMvc
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email", userRequest.getEmail())
                .param("firstName", userRequest.getFirstName())
                .param("lastName", userRequest.getLastName())
                .param("password", userRequest.getPassword())
                .param("confirmPassword",userRequest.getConfirmPassword())
        )
                .andExpect(status().is3xxRedirection())  // Jika valid, seharusnya melakukan redirect
                .andExpect(redirectedUrl("/users"));

        // Verifikasi bahwa metode save dari UserService dipanggil dengan parameter yang benar
        Mockito.verify(userSrv, Mockito.times(1)).save(Mockito.any(UserRequest.class));
    }
	
	@Test
	public void testStoreWithInvalidUser() throws Exception {
	    UserRequest userRequest = new UserRequest();
	    userRequest.setEmail("test@example.com");
	    userRequest.setFirstName("John");
	    userRequest.setLastName("Doe");
	    userRequest.setPassword("securePassword");
	    userRequest.setConfirmPassword("securePassword");

	    // Mocking behavior of UserService
	    lenient().when(userSrv.save(Mockito.any(UserRequest.class))).thenReturn(new User());

	    // Setting up bindingResult to return true
	    when(bindingResult.hasErrors()).thenReturn(true);

	    // Perform the test
	    mockMvc.perform(post("/users")
	            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
	            .param("email", userRequest.getEmail())
	            .param("firstName", userRequest.getFirstName())
	            .param("lastName", userRequest.getLastName())
	            .param("password", userRequest.getPassword())
	            .param("confirmPassword",userRequest.getConfirmPassword())
	    )
	            .andExpect(status().isOk());  // Expect HTTP status 200

	    // Verify that bindingResult.hasErrors() is called exactly once
	    Mockito.verify(bindingResult, Mockito.times(1)).hasErrors();
	}
}
