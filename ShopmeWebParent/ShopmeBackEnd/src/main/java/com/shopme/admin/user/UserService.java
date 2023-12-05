package com.shopme.admin.user;



import java.util.List;

import com.shopme.common.entities.User;

public interface UserService {
	public List<User> all();
	public User findById(Integer id);
	public User save(User user);
	public void remove(User user);
}
