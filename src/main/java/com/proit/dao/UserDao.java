package com.proit.dao;

import java.util.List;

import com.proit.model.User;

public interface UserDao {

	List<User> findAll();
	
	void insert(User user);
	
	void update(Long id, User user);
	
	void delete(Long id);

}
