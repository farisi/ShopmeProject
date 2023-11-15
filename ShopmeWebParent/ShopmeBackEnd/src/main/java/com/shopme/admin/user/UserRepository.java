package com.shopme.admin.user;


import org.springframework.data.repository.CrudRepository;

import com.shopme.common.entities.User;

public interface UserRepository extends CrudRepository<User, Integer> {
}
