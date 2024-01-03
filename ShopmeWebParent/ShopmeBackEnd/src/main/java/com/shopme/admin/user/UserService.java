package com.shopme.admin.user;



import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.shopme.admin.requests.UserRequest;
import com.shopme.common.entities.User;

public interface UserService {
	public List<User> all();
	public Page<User> all(Pageable p);
	public UserRequest findById(Integer id);
	public User save(UserRequest usrReq);
	public User save(User usrReq);
	public void remove(User user);
	public boolean isEmailExist(String email);
	public User findUserById(Integer id);
	public Optional<User> findByEmail(String email);
	public User updateUserPassword(String password, User user);
	Page<User> findByFirstnameContainingIgnoreCase(String keyword, Pageable pageable);
}
