package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

import java.io.InputStream;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.shopme.admin.services.FilesStorageService;
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
	
	@MockBean
	FilesStorageService fss;
	

	@BeforeEach
	public void tearUp() {
		List<Object[]> objects = ExcelDataReader.readTestDataFromExcel(getResourceFile("testroles.xlsx"), "Sheet1");
		for(Object[] o : objects) {
			Role role = new Role(o[0].toString(),o[1].toString(),o[2].toString());
			entityManager.persist(role);
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
		User user1 = new User("ravi@codejava.net", "ravi2020", "Ravi", "Kumar");
		user1.addRole(admin);
		User saveUser=repo.save(user1);
		assertThat(saveUser).isNotNull();
		
	}
	
	@Test
	public void testCreateNewUserWithTwoRoles() {
		User rizal = new User("rizal.firdaus@gmail.com","captain2020","Rizal", "Firdaus");
		Role roleEditor = new Role(3);
		Role roleAssitant = new Role(4);
		rizal.addRole(roleAssitant);
		rizal.addRole(roleEditor);
		User saveUser = repo.save(rizal);
		assertThat(saveUser).isNotNull();
	}
	
	@Test
	public void testAddRoleEditor() {
		User wira = new User("wira.laoli@gmail.com","wira2020","Wira", "Lauli");
		Role roleEditor = entityManager.find(Role.class, 3);
		wira.addRole(roleEditor);
		repo.save(wira);
		
		Integer idBaru = wira.getId();
		Role roleAssintantFromDb = entityManager.find(Role.class, 4);
		User wiraFromDatabase = repo.findById(idBaru).get();
		wiraFromDatabase.addRole(roleAssintantFromDb);
		wiraFromDatabase.getRoles().forEach(r->System.out.println(r.getName()));
		assertThat(wiraFromDatabase.getRoles()).contains(roleAssintantFromDb);
		assertThat(wiraFromDatabase.getRoles().size()).isEqualTo(2);
	}
	
	@Test
	public void testListAllUsers() {
		Iterable<User> listusers = repo.findAll();
		listusers.forEach(u->System.out.println(u));
	}
	
	@Test
	public void testGetUserById() {
		User wira = new User("wira.laoli@gmail.com","wira2020","Wira", "Lauli");
		Role salesperson = new Role(2);
		Role roleEditor = new Role(3);
		wira.addRole(salesperson);
		wira.addRole(roleEditor);
		repo.save(wira);
		
		User userNam = repo.findById(1).get();
		assertThat(userNam).isNotNull();
	}
	
	@Test
	public void testUpdateUserDetails() {
		User wira = new User("wira.laoli@gmail.com","wira2020","Wira", "Lauli");
		Role salesperson = new Role(2);
		Role roleEditor = new Role(3);
		wira.addRole(salesperson);
		wira.addRole(roleEditor);
		repo.save(wira);
		
		User userNam = repo.findById(1).get();
		userNam.setEnabled(true);
		User userNamUpdated = repo.save(userNam);
		assertThat(userNamUpdated.isEnabled()).isEqualTo(true);		
	}
	
	@Test
	public void testUpdateUserRoles() {
		User wira = new User("wira.laoli@gmail.com","wira2020","Wira", "Lauli");
		Role salesperson = entityManager.find(Role.class, 2);
		Role roleEditor = entityManager.find(Role.class, 3);
		wira.addRole(salesperson);
		wira.addRole(roleEditor);
		repo.save(wira);
		
		Integer idBaru = wira.getId();
		Role roleEditor2 = entityManager.find(Role.class, 3) ;
		User wiraFromDatabase = repo.findById(idBaru).get();		
		wiraFromDatabase.getRoles().remove(roleEditor2);
		assertThat(wiraFromDatabase.getRoles().size()).isLessThanOrEqualTo(1);
	}
	
	
	@Test
	public void testRemoveUserById() {
		User wira = new User("wira.laoli@gmail.com","wira2020","Wira", "Lauli");
		Role salesperson = new Role(2);
		Role roleEditor = new Role(3);
		wira.addRole(salesperson);
		wira.addRole(roleEditor);
		repo.save(wira);
		
		Integer userId=1;
		repo.deleteById(userId);
		assertThatNoException();
	}
	
	@AfterEach
	public  void tearDown() {
		entityManager.getEntityManager().createNativeQuery("DELETE from user_role").executeUpdate();
		entityManager.getEntityManager().createNativeQuery("DELETE from Users").executeUpdate();
		entityManager.getEntityManager().createNativeQuery("DELETE from Roles").executeUpdate();

        
        // Panggil flush
		//entityManager.flush();
		
        
        // Atur ulang nilai auto-increment di tabel roles
		entityManager.getEntityManager().createNativeQuery("ALTER TABLE roles AUTO_INCREMENT = 1").executeUpdate();
		entityManager.getEntityManager().createNativeQuery("ALTER TABLE users AUTO_INCREMENT = 1").executeUpdate();
	}
}
