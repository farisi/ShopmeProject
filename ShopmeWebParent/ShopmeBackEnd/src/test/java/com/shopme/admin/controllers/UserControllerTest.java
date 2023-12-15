package com.shopme.admin.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.UserController;
import com.shopme.admin.requests.UserRequest;
import com.shopme.admin.user.RoleService;
import com.shopme.admin.user.UserService;
import com.shopme.common.entities.User;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
	
	@InjectMocks
    private UserController userController;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @Mock
    private BindingResult bindingResult;

	
	@Mock
	UserService userSrv;
	
	@Mock
	RoleService roleSrv;

    @Test
    public void testStoreWithValidUser() {
        UserRequest userRequest = new UserRequest();
        // Set userRequest properties here

        // Mocking the behavior of the userService
        Mockito.lenient().when(userSrv.save(userRequest)).thenReturn(new User());

        // Mocking the behavior of the bindingResult
        Mockito.lenient().when(bindingResult.hasErrors()).thenReturn(false);

        // Calling the method to be tested
        String result = userController.store(userRequest, bindingResult, model, redirectAttributes);

        // Assertions
        assertEquals("redirect:/users", result);
        // You can add more assertions based on your specific requirements
    }

    @Test
    public void testStoreWithInvalidUser() {
        UserRequest userRequest = new UserRequest();
        // Set userRequest properties here

        // Mocking the behavior of the bindingResult
        lenient().when(bindingResult.hasErrors()).thenReturn(true);

        // Calling the method to be tested
        String result = userController.store(userRequest, bindingResult, model, redirectAttributes);

        // Assertions
        assertEquals("users/create", result);
        // You can add more assertions based on your specific requirements
    }
}
