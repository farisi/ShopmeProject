package com.shopme.admin.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.shopme.admin.UserController;
import com.shopme.admin.requests.UserRequest;
import com.shopme.admin.user.RoleService;
import com.shopme.admin.user.UserService;


@WebMvcTest(UserController.class)
@ExtendWith(MockitoExtension.class)
public class UserControllerSecondTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	UserService userSrv;
	
	@MockBean
	RoleService roleSrv;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
    public void testStoreWithValidUser() throws Exception {
        // Persiapkan objek UserRequest yang valid
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("test@example.com");
        userRequest.setFirstName("John");
        userRequest.setLastName("Doe");
        userRequest.setPassword("securePassword");
        userRequest.setConfirmPassword("securePassword");

        // Lakukan pengujian dengan MockMvc
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email", userRequest.getEmail())
                .param("firstName", userRequest.getFirstName())
                .param("lastName", userRequest.getLastName())
                .param("password", userRequest.getPassword())
                .param("confirmPassword",userRequest.getConfirmPassword())
        ).andExpect(status().is3xxRedirection())  // Jika valid, seharusnya melakukan redirect
                .andExpect(redirectedUrl("/users"));
    }
	
	@Test
	public void testStoreWithoutFirstName() throws Exception {
	    // Perform the test
	    mockMvc.perform(post("/users")
	            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
	            .param("email", "salmandriva@gmail.com")
	            .param("firstName", "")
	            .param("lastName", "Farisi")
	            .param("password", "p455w0rd")
	            .param("confirmPassword","password")
	    ).andExpect(status().isOk())
	    .andExpect(view().name("users/create"));  // Expect HTTP status 200
	}
	
	@Test
	public void testStoreWithoutPassword() throws Exception {
	    // Perform the test
	    mockMvc.perform(post("/users")
	            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
	            .param("email", "salmandriva@gmail.com")
	            .param("firstName", "Salman")
	            .param("lastName", "Farisi")
	            .param("password", "")
	            .param("confirmPassword","")
	    ).andExpect(status().isOk());  // Expect HTTP status 200
	}
	

	@Test
	public void testStoreWithNotMatchPassword() throws Exception {
	    UserRequest userRequest = new UserRequest();
	    userRequest.setEmail("test@example.com");
	    userRequest.setFirstName("John");
	    userRequest.setLastName("Doe");
	    userRequest.setPassword("securePassword");
	    userRequest.setConfirmPassword("1234567");

	    // Perform the test
	    mockMvc.perform(post("/users")
	            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
	            .param("email", userRequest.getEmail())
	            .param("firstName", userRequest.getFirstName())
	            .param("lastName", userRequest.getLastName())
	            .param("password", userRequest.getPassword())
	            .param("confirmPassword",userRequest.getConfirmPassword())
	    ).andExpect(status().isOk());  // Expect HTTP status 200
	}
}
