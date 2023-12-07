package com.shopme.admin.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.admin.requests.UserRequest;
import com.shopme.common.entities.User;

@Service
public class UserServiceImplement implements UserService {
	
	@Autowired(required = true)
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
	public User save(UserRequest userReq) {
		// TODO Auto-generated method stub
		User user = new User();
		user.setEmail(userReq.getEmail());
		user.setFirstName(userReq.getFirstName());
		user.setLastName(userReq.getLastName());
		user.setPassword(userReq.getPassword());
	
		return repo.save(user);
	}

	@Override
	public void remove(User user) {
		// TODO Auto-generated method stub
		repo.delete(user);
	}

	@Override
	public boolean isEmailExist(String email) {
		// TODO Auto-generated method stub
		User user = repo.findByEmail(email);
		return user!=null;
	}	
}
