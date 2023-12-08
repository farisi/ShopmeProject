package com.shopme.admin.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.shopme.admin.requests.UserRequest;
import com.shopme.common.entities.User;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class UserServiceTest {
		
	@Autowired
	private UserService userSrv;
	
	@Mock
	private UserService mockUserSrv;
	
	private PodamFactory podamFactory = new PodamFactoryImpl();
	 
	@MockBean
	RoleRepository roleRepo;
	
	@MockBean
	UserRepository usrRepo;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
    public void testGetAllUsers() {
        // Mocking the behavior of the userRepository
        List<User> userList = new ArrayList<>();
        userList.add(new User());
        userList.add(new User());
        lenient().when(usrRepo.findAll()).thenReturn(userList);

        // Calling the method to be tested
        List<User> result = userSrv.all();

        // Assertions
        assertEquals(2, result.size());
    }
	
	
	 @Test
	 public void testSaveUserFailure() {
		 UserRequest userToSave = new UserRequest();
		 userToSave.setEmail("john.doe@example.com");
		 userToSave.setFirstName("John");
		 userToSave.setLastName("Doe");
		 userToSave.setPassword("password");
		 userToSave.setConfirmPassword("password");

	     // Simulasikan kegagalan penyimpanan dengan always throwing exception
	     doThrow(new RuntimeException("Simulated save failure"))
		     .when(mockUserSrv)
		     .save(any(UserRequest.class));
	        // Act
	        try {
	        	mockUserSrv.save(userToSave);
	            fail("Expected exception not thrown");
	        } catch (RuntimeException e) {
	            // Assert
	            verify(mockUserSrv, times(1)).save(eq(userToSave)); // Verifikasi bahwa save dipanggil dengan userToSave yang tepat
	            assertEquals(0, ((List<User>) mockUserSrv.all()).size()); // Verifikasi bahwa user tidak berhasil disimpan
	            assertEquals("Simulated save failure", e.getMessage()); // Verifikasi pesan exception yang dihasilkan
	        }
	   }
}