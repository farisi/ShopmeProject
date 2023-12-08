package com.shopme.admin.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.admin.requests.UserRequest;
import com.shopme.common.entities.User;

@Service
public class UserServiceImplement implements UserService {
	
	@Autowired(required = true)
	UserRepository repo;
	
	@Autowired
	RoleRepository roleRepo;

	@Override
	public List<User> all() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public UserRequest findById(Integer id) {
		// TODO Auto-generated method stub
		User u = repo.findById(id).get();
		UserRequest userReq=new UserRequest();
		userReq.setId(u.getId());
		userReq.setEmail(u.getEmail());
		userReq.setFirstName(u.getFirstName());
		userReq.setLastName(u.getLastName());
		userReq.setRoles(u.getRoles());
		return userReq;
	}

	@Override
	public User save(UserRequest userReq) {
		// TODO Auto-generated method stub
		User user;
		boolean isNew=false;
		
		if(userReq.getId() > 0) {
			Optional<User> optUser = repo.findById(userReq.getId());
			user = optUser.get();
			if(!"".equals(user.getPassword())) {
				user.setPassword(userReq.getPassword());
			}
		}
		else {
			user = new User();
			user.setPassword(userReq.getPassword());
			isNew=true;
		}
		user.setId(userReq.getId());
		user.setEmail(userReq.getEmail());
		user.setFirstName(userReq.getFirstName());
		user.setLastName(userReq.getLastName());

		user.setRoles(userReq.getRoles());
		
		if(isNew) {
			return repo.save(user);
		}
		else {
			return user;
		}

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
