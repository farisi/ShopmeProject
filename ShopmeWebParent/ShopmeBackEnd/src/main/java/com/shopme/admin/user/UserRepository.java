package com.shopme.admin.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.shopme.common.entities.User;

public interface UserRepository extends PagingAndSortingRepository<User,Integer>, ListCrudRepository<User, Integer> {
	
	@EntityGraph(attributePaths = "roles")
	@Query("SELECT u FROM User u LEFT JOIN FETCH u.roles")
	List<User> findUserAll();
	
	@EntityGraph(attributePaths = "roles")
	Optional<User> findByEmail(String email);
	
	@EntityGraph(attributePaths = "roles")
	Page<User> findAll(Pageable pageable);
}
