package com.shopme.admin.controllers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.shopme.admin.UserController;
import com.shopme.admin.requests.UserRequest;
import com.shopme.admin.user.EmailService;
import com.shopme.admin.user.RoleService;
import com.shopme.admin.user.UserService;
import com.shopme.admin.utilitas.securities.CustomAuthenticationProvider;
import com.shopme.admin.utilitas.securities.MySecurityUser;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerSecondTest {
	
	@Autowired
	protected MockMvc mockMvc;
	
	@MockBean
    private CustomAuthenticationProvider customAuthenticationProvider;
	
	@MockBean
	UserService userSrv;
	
	@MockBean
	RoleService roleSrv;
	
	@MockBean
	EmailService emailSrv;

	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void testListUser() throws Exception {
		List<GrantedAuthority> authorities = new ArrayList<>();

        // Menambahkan granted authorities sesuai kebutuhan
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        MySecurityUser usrDetail = new MySecurityUser.Builder()
				.withId(1)
				.withEmail("salmandriva@gmail.com")
				.withUsername("salmandriva@gmail.com")
				.withFullname("Salman Farisi")
				.withPassword("123456")
				.withPhoto("foto.jpeg")
				.withAuthorities(authorities)
				.build();
        
		 mockMvc.perform(get("/users")
				 .with(user(usrDetail)))
		 .andExpect(status().isOk());
	}

	@Test
    public void testStoreWithValidUser() throws Exception {
		
		List<GrantedAuthority> authorities = new ArrayList<>();

        // Menambahkan granted authorities sesuai kebutuhan
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        MySecurityUser usrDetail = new MySecurityUser.Builder()
				.withId(1)
				.withEmail("salmandriva@gmail.com")
				.withUsername("salmandriva@gmail.com")
				.withFullname("Salman Farisi")
				.withPassword("123456")
				.withPhoto("foto.jpeg")
				.withAuthorities(authorities)
				.build();

        // Persiapkan objek UserRequest yang valid
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("test@example.com");
        userRequest.setFirstName("John");
        userRequest.setLastName("Doe");
        userRequest.setPassword("securePassword");
        userRequest.setConfirmPassword("securePassword");

        // Lakukan pengujian dengan MockMvc
       
        mockMvc.perform(post("/users")
        		.with(user(usrDetail))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)      
                .param("email", userRequest.getEmail())
                .param("firstName", userRequest.getFirstName())
                .param("lastName", userRequest.getLastName())
                .param("password", userRequest.getPassword())
                .param("confirmPassword",userRequest.getConfirmPassword())
        		).andExpect(status().isOk());
    }
	
	
	@Test
	public void testStoreWithoutFirstName() throws Exception {
		List<GrantedAuthority> authorities = new ArrayList<>();

        // Menambahkan granted authorities sesuai kebutuhan
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        MySecurityUser usrDetail = new MySecurityUser.Builder()
				.withId(1)
				.withEmail("salmandriva@gmail.com")
				.withUsername("salmandriva@gmail.com")
				.withFullname("Salman Farisi")
				.withPassword("123456")
				.withPhoto("foto.jpeg")
				.withAuthorities(authorities)
				.build();
   	
	    // Perform the test
	    mockMvc.perform(post("/users")
	    		.with(user(usrDetail))
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
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        MySecurityUser usrDetail = new MySecurityUser.Builder()
				.withId(1)
				.withEmail("salmandriva@gmail.com")
				.withUsername("salmandriva@gmail.com")
				.withFullname("Salman Farisi")
				.withPassword("123456")
				.withPhoto("foto.jpeg")
				.withAuthorities(authorities)
				.build();
	    // Perform the test
	    mockMvc.perform(post("/users")
	    		.with(user(usrDetail))
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
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        MySecurityUser usrDetail = new MySecurityUser.Builder()
				.withId(1)
				.withEmail("salmandriva@gmail.com")
				.withUsername("salmandriva@gmail.com")
				.withFullname("Salman Farisi")
				.withPassword("123456")
				.withPhoto("foto.jpeg")
				.withAuthorities(authorities)
				.build();
		
	    UserRequest userRequest = new UserRequest();
	    userRequest.setEmail("test@example.com");
	    userRequest.setFirstName("John");
	    userRequest.setLastName("Doe");
	    userRequest.setPassword("securePassword");
	    userRequest.setConfirmPassword("1234567");

	    // Perform the test
	    mockMvc.perform(post("/users")
	    		.with(user(usrDetail))
	            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
	            .param("email", userRequest.getEmail())
	            .param("firstName", userRequest.getFirstName())
	            .param("lastName", userRequest.getLastName())
	            .param("password", userRequest.getPassword())
	            .param("confirmPassword",userRequest.getConfirmPassword())
	    ).andExpect(status().isOk());  // Expect HTTP status 200
	}
}
