package com.proit.dao;

import java.util.List;

import com.proit.model.Category;

public interface CategoryDao {

	List<Category> findByName(String name);

	List<Category> findAll(int pageNo, int pageSize);

	List<Category> findAll();

	Category findById(int id);

	void delete(int id);

	void update(int id, Category entity);

	void insert(Category entity);

}
