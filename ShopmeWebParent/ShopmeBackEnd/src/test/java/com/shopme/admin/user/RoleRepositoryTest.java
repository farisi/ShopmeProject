package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.shopme.admin.services.FilesStorageService;
import com.shopme.common.entities.Role;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class RoleRepositoryTest {

	@Autowired
	private RoleRepository repo;
	
	@Autowired
	TestEntityManager entityManager;
	
	@MockBean
	FilesStorageService fss;
	
	@Test
	public void testCreateFirstRole() {
		Role roleAdmin = new Role("admin","Administrator", "Could manage everything");
		Role saveRole = repo.save(roleAdmin);
		assertThat(saveRole.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateMultipleRoles() {
		Role roleSalesperson = new Role("salesperson","Sales Person", " manage product, price,customers, shipping,orders and sales report");
		Role roleEditor = new Role("editor", "Editor", "manage categories, brand, products, articles and menus");
		Role roleAssistant = new Role("roleassistant","Role assistant","manage questions and reviews");
		repo.saveAll(List.of(roleSalesperson,roleEditor,roleAssistant));
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
