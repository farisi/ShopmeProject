package com.shopme.admin.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.common.entities.User;

@Service
public class UserServiceImplement implements UserService {
	
	@Autowired
	UserRepository repo;

	@Override
	public List<User> all() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public User findById(Integer id) {
		// TODO Auto-generated method stub
		return repo.findById(id).get();
	}

	@Override
	public User save(User user) {
		// TODO Auto-generated method stub
		return repo.save(user);
	}

	@Override
	public void remove(User user) {
		// TODO Auto-generated method stub
		repo.delete(user);
	}	
}
