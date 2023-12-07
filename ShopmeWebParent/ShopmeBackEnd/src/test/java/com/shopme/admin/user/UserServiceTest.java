package com.shopme.admin.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shopme.admin.requests.UserRequest;
import com.shopme.common.entities.Role;
import com.shopme.common.entities.User;

import org.springframework.transaction.annotation.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import java.util.List;

@SpringBootTest
public class UserServiceTest {
	
	@Autowired
    private UserService userService;
	
	@Mock
	private UserService usrSrvMock;
	 
	private PodamFactory podamFactory = new PodamFactoryImpl();
	 
	@Autowired
	RoleRepository roleRepo;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	 

	@Transactional
	@Test
	public void testSaveUser() {
		 Role role = podamFactory.manufacturePojo(Role.class);
	        roleRepo.save(role);

	        UserRequest userToSave = podamFactory.manufacturePojo(UserRequest.class);
	        userToSave.addRole(role);

	        User userAfterSaved = userService.save(userToSave);

	        User retrievedUser = userService.findById(userAfterSaved.getId());
	        assertNotNull(retrievedUser);
	        assertEquals(userToSave.getEmail(), retrievedUser.getEmail());
	        assertEquals(userToSave.getRoles().size(), retrievedUser.getRoles().size());
	}	
	
	@Transactional
	@Test
	public void testSaveDua() {
		Role role = podamFactory.manufacturePojo(Role.class);
	    roleRepo.save(role);

	    UserRequest userToSave = podamFactory.manufacturePojo(UserRequest.class);
	    userToSave.addRole(role);

	    usrSrvMock.save(userToSave);
	    verify(usrSrvMock, times(1)).save(eq(userToSave));
	    assertEquals(1, ((List<User>) usrSrvMock.all()).size());
	    //when(userService.someMethod(any())).thenReturn(someValue);
	}
	
	 @Test
	 public void testSaveUserFailure() {
	        // Arrange
		 //"john.doe@example.com", "password", "John", "Doe"
		 UserRequest userToSave = new UserRequest();
		 userToSave.setEmail("john.doe@example.com");
		 userToSave.setFirstName("John");
		 userToSave.setLastName("Doe");
		 userToSave.setPassword("password");
		 userToSave.setConfirmPassword("password");

	     // Simulasikan kegagalan penyimpanan dengan always throwing exception
	     doThrow(new RuntimeException("Simulated save failure"))
		     .when(usrSrvMock)
		     .save(userToSave);
		     //.save((UserRequest) any(UserRequest.class));

	        // Act
	        try {
	        	usrSrvMock.save(userToSave);
	            fail("Expected exception not thrown");
	        } catch (RuntimeException e) {
	            // Assert
	            verify(usrSrvMock, times(1)).save(eq(userToSave)); // Verifikasi bahwa save dipanggil dengan userToSave yang tepat
	            assertEquals(0, ((List<User>) usrSrvMock.all()).size()); // Verifikasi bahwa user tidak berhasil disimpan
	            assertEquals("Simulated save failure", e.getMessage()); // Verifikasi pesan exception yang dihasilkan
	        }
	   }
}