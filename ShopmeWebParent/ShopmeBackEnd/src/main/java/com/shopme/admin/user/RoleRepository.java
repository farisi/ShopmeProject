package com.shopme.admin.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;

import com.shopme.common.entities.Role;

public interface RoleRepository extends CrudRepository<Role, Integer>, ListCrudRepository<Role, Integer>{

}
