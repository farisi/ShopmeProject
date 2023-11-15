package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;


import com.shopme.common.entities.Role;
import com.shopme.common.entities.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryTest {

	@Autowired
	private UserRepository repo;
	
	@Autowired
	TestEntityManager entityManager;
	
	@Test
	public void testCreateUser() {
		User user1 = new User("ravi@codejava.net", "ravi2020", "Ravi", "Kumar");
		Role roleAdmin = new Role(1);
		user1.addRole(roleAdmin);
		User saveUser=repo.save(user1);
		assertThat(saveUser.getId()).isGreaterThan(0);  
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
}
