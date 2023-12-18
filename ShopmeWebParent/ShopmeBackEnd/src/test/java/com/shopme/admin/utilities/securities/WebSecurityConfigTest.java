package com.shopme.admin.utilities.securities;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.ui.Model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopme.admin.repositories.PasswordResetRepository;
import com.shopme.admin.requests.PasswordResetRequest;
import com.shopme.admin.services.PasswordResetService;
import com.shopme.admin.user.EmailService;
import com.shopme.admin.user.UserService;
import com.shopme.admin.utilitas.UrlUtils;
import com.shopme.admin.utilitas.securities.WebSecurityConfig;

import net.bytebuddy.NamingStrategy.Suffixing.BaseNameResolver.ForGivenType;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class WebSecurityConfigTest {

    @Autowired
    private WebSecurityConfig webSecurityConfig;
    

    @Autowired
    private MockMvc mockMvc;
   
    @MockBean
    JavaMailSender jms;
    
    @MockBean
	private PasswordResetService prSrv;
    
    @MockBean
	PasswordResetRepository prRepo;
	
    @MockBean
	EmailService emailSrv;
	
    @MockBean
	UrlUtils urlSrv;
	
    @MockBean
	UserService usrSrv;
    
    @MockBean
    Model ui;

    @Test
    public void testPermitAllForResetPasswordPath() throws Exception {
    	ObjectMapper objectMapper = new ObjectMapper();
    	PasswordResetRequest user = new PasswordResetRequest();
    	user.setEmail("salmandriva@gmail.com");
    	
    	 RequestPostProcessor requestPostProcessor = new RequestPostProcessor() {
    	     
				@Override
				public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
					// TODO Auto-generated method stub
					String json = null;
					try {
						json = objectMapper.writeValueAsString(user);
					} catch (JsonProcessingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    byte[] content = json.getBytes(StandardCharsets.UTF_8);
				    request.setContent(content);
				    request.setContentType(MediaType.APPLICATION_JSON_VALUE);
				    return request;
				}
    	    };
    	
        mockMvc.perform(MockMvcRequestBuilders.get("/auth/reset_password/3a5a091c-b48c-41e5-812c-819898a16b1f")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .with(requestPostProcessor)
        		)
                .andExpect(status().isOk());
    }

    @Test
    public void testAnyRequestRequiresAuthentication() throws Exception {
    	
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void testFormLoginLoginPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("auths/login"));
    }

    @Test
    public void testLogoutSuccessUrl() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}