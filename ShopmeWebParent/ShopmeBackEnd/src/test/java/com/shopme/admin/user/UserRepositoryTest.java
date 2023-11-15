package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.shopme.admin.utilities.ExcelDataReader;
import com.shopme.common.entities.Role;
import com.shopme.common.entities.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryTest {

	@Autowired
	private UserRepository repo;
	
	@Autowired
	TestEntityManager entityManager;
	
	private static TestEntityManager staticEntityManager;
	
	@BeforeEach
	public void tearUp() {
		List<Object[]> objects = ExcelDataReader.readTestDataFromExcel(getResourceFile("testroles.xlsx"), "Sheet1");
		for(Object[] o : objects) {
			Role role = new Role(o[0].toString(),o[1].toString(),o[2].toString());
			entityManager.persistAndFlush(role);
		}
	}
	
	static List<Object[]> getDataFromExcel() {
        String filePath = "testroles.xlsx";
        return ExcelDataReader.readTestDataFromExcel(getResourceFile(filePath), "Sheet1");
    }

    // Metode untuk mendapatkan InputStream dari resource di dalam folder src/test/resources
    private static InputStream getResourceFile(String fileName) {
        ClassLoader classLoader = UserRepositoryTest.class.getClassLoader();
        return classLoader.getResourceAsStream(fileName);
    }
	
	@Test
	public void testCreateUser() {
		Role admin = (Role) entityManager.getEntityManager().createQuery("SELECT r from Role r where name=?1").setParameter(1, "admin").getResultList().get(0);
		System.out.println(" role id " + admin.getId());
		User user1 = new User("ravi@codejava.net", "ravi2020", "Ravi", "Kumar");
		Role roleAdmin = new Role(1);
		user1.addRole(roleAdmin);
		User saveUser=repo.save(user1);
		assertThat(saveUser).isNotNull();
		
	}
	
	@Test
	public void testCreateNewUserWithTwoRoles() {
		User rizal = new User("rizal.firdaus@gmail.com","captain2020","Rizal", "Firdaus");
		Role roleEditor = new Role(4);
		Role roleAssitant = new Role(5);
		rizal.addRole(roleAssitant);
		rizal.addRole(roleEditor);
		User saveUser = repo.save(rizal);
		assertThat(saveUser).isNotNull();
	}
	
	@Test
	public void testAddRoleEditor() {
		User rizal = repo.findById(9).get();
		Role roleEditor = new Role(4);
		rizal.addRole(roleEditor);
		assertThat(rizal.getRoles().size()).isGreaterThan(1);
	}
	
	@Test
	public void testListAllUsers() {
		Iterable<User> listusers = repo.findAll();
		listusers.forEach(u->System.out.println(u));
	}
	
	@Test
	public void testGetUserById() {
		User userNam = repo.findById(1).get();
		assertThat(userNam).isNotNull();
	}
	
	@Test
	public void testUpdateUserDetails() {
		User userNam = repo.findById(1).get();
		userNam.setEnabled(true);
		User userNamUpdated = repo.save(userNam);
		assertThat(userNamUpdated.isEnabled()).isEqualTo(true);		
	}
	
	@Test
	public void testUpdateUserRoles() {
		User userRavi = repo.findById(9).get();
		Role roleEditor = entityManager.find(Role.class, 4) ;
		userRavi.getRoles().remove(roleEditor);
		assertThat(userRavi.getRoles().size()).isLessThanOrEqualTo(1);
	}
	
	
	@Test
	public void testRemoveUserById() {
		Integer userId=3;
		repo.deleteById(userId);
		assertThatNoException();
	}
	
	@AfterAll
	public static   void tearDown() {
		if (staticEntityManager != null) {
	        // Hapus data dari tabel roles
	        staticEntityManager.getEntityManager().createQuery("DELETE FROM Role").executeUpdate();
	        
	        // Panggil flush
	        staticEntityManager.flush();
	        
	        // Atur ulang nilai auto-increment di tabel roles
	        staticEntityManager.getEntityManager().createNativeQuery("ALTER TABLE roles AUTO_INCREMENT = 1").executeUpdate();
	    }
	}
}
