package com.shopme.admin.user;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.shopme.common.entities.User;

public interface UserRepository extends PagingAndSortingRepository<User,Integer>, ListCrudRepository<User, Integer> {
}
