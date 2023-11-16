package com.shopme.admin.user;



import com.shopme.common.entities.User;

public interface UserService {
	public Iterable<User> all();
	public User findById(Integer id);
	public User save(User user);
	public void remove(User user);
}
