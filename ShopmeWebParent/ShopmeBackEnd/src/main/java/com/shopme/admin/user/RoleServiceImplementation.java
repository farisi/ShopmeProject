package com.shopme.admin.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.common.entities.Role;

@Service
public class RoleServiceImplementation implements RoleService {

	
	@Autowired
	private RoleRepository roleRepo;
	
	@Override
	public List<Role> all() {
		// TODO Auto-generated method stub
		return roleRepo.findAll();
	}

}
