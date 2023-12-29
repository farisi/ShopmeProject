package com.shopme.admin.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.shopme.admin.services.FilesStorageService;

@AutoConfigureMockMvc
@WebMvcTest(MainController.class)
public class MainControllerTest {
	
	
	
	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	FilesStorageService fss;


    @Test
    public void testThymeleafTemplate() throws Exception {
        mockMvc
        .perform(get("/"))
        .andExpect(status().isUnauthorized());
    }
}
