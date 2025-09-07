package com.proit.dao;

import java.util.List;

import com.proit.model.Product;


public interface ProductDao {

	List<Product> findByName(String name);

	List<Product> findAll(int pageNo, int pageSize);

	List<Product> findAll();

	Product findById(Long id);

	void delete(Long id);

	void update(Long id, Product entity);

	void insert(Product entity);

	void update(Long id, String imageUrl);

	Long countByName(String name);

	List<Product> findByName(String name, int pageNo, int pageSize);
	

}
