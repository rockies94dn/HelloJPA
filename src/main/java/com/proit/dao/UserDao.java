package com.proit.dao;

import java.util.List;

import com.proit.model.User;

public interface UserDao {

	List<User> findAll();
	
	User findById(Long id);
	
	List<User> findByName(String name);
	
	User findByEmail(String email);
	
	void insert(User user);
	
	void update(Long id, User user);
	
	void delete(Long id);

	List<User> findAll(int pageNo, int pageSize);
	
	Long countAllUsers();

	User findByEmailAndPassword(String email, String password);

}
