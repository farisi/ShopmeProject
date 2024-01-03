package com.shopme.admin.user;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopme.admin.requests.UserRequest;
import com.shopme.common.entities.User;

@Service
public class UserServiceImplement implements UserService {
	
	@Autowired(required = true)
	UserRepository repo;
	
	@Autowired
	RoleRepository roleRepo;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public List<User> all() {
		// TODO Auto-generated method stub
		return repo.findUserAll();
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
	@Transactional
	@Override
	public User save(UserRequest userReq) {
		// TODO Auto-generated method stub
		User user;
		boolean isNew=false;
		String rawPassword=userReq.getPassword();
		if(userReq.getId()!=null) {
			Optional<User> optUser = repo.findById(userReq.getId());
			user = optUser.get();
			if(!"".equals(userReq.getPassword())) {
				
				user.setPassword(passwordEncoder.encode(rawPassword));
			}
		}
		else {
			
			user = new User();
			user.setPassword(passwordEncoder.encode(userReq.getPassword()));
			isNew=true;
			user.setPhotos("1.jpg");
		}
		user.setId(userReq.getId());
		user.setEmail(userReq.getEmail());
		user.setFirstName(userReq.getFirstName());
		user.setLastName(userReq.getLastName());

		user.setRoles(userReq.getRoles());
		
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
		if("".equals(email))
		{
			return false;
		}
		User user = repo.findByEmail(email).orElse(null);
		return user!=null;
	}

	@Override
	public User findUserById(Integer id) {
		// TODO Auto-generated method stub
		return repo.findById(id).get();
	}

	@Override
	public Optional<User> findByEmail(String email) {
		// TODO Auto-generated method stub
		return repo.findByEmail(email);
	}

	@Override
	public User updateUserPassword(String password, User user) {
		// TODO Auto-generated method stub
		user.setPassword(passwordEncoder.encode(password));
		return repo.save(user);
	}

	@Override
	public User save(User user) {
		// TODO Auto-generated method stub
		return repo.save(user);
	}

	@Override
	public Page<User> all(Pageable p) {
		// TODO Auto-generated method stub
		return repo.findAll(p);
	}

}
