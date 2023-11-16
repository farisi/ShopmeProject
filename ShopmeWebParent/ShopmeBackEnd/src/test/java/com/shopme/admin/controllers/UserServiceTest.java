package com.shopme.admin.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import com.shopme.admin.user.UserService;
import com.shopme.admin.user.UserServiceImplement;
import com.shopme.common.entities.Role;
import com.shopme.common.entities.User;

//import uk.co.jemos.podam.api.PodamFactory;
//import uk.co.jemos.podam.api.PodamFactoryImpl;

@SpringBootTest
public class UserServiceTest {
	
	 @Mock
	 private UserService userService;  // Menggunakan anotasi @Mock untuk membuat mock objek

	 @Autowired
	 private UserServiceImplement userServiceImplement;  // Memastikan bahwa implementasi juga diinjeksikan oleh Spring
	 
	 //private PodamFactory podamFactory = new PodamFactoryImpl();
	 
//
//	 @Test
//	 public void testSaveUser() {
//		 Role role = podamFactory.manufacturePojo(Role.class);
//	     User userToSave = podamFactory.manufacturePojo(User.class); //new User("ravi@codejava.net", "ravi2020", "Ravi", "Kumar");
//	     
//	     userToSave.addRole(role);
//
//	     userServiceImplement.save(userToSave);  // Panggil metode save pada implementasi
//	     verify(userService, times(1)).save(userToSave);  // Verifikasi apakah metode save dipanggil pada mock objek userService
//	 }	
}