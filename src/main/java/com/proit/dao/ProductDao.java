package com.proit.dao;

import java.util.List;

import com.proit.dto.ProductStatusSummary;
import com.proit.model.Category;
import com.proit.model.Product;
import com.proit.model.ProductStatus;
import com.proit.model.ProductsInCategoryStatistics;

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

	List<Product> findByCategory(Category category);

	List<Product> findByCategory(Category category, int pageNo, int pageSize);

	List<Product> findByCategory(Long categoryId);

	List<Product> findByCategory(Long categoryId, int pageNo, int pageSize);

	Long countByCategory(Long categoryId);

	Long countByCategory(Category category);

	Long countAll();

	List<ProductsInCategoryStatistics> countProductsByCategory();

	List<Product> findByNameAndPrice(String name, Double price);

	Long countByNameAndPrice(String name, Double price);

	List<Product> findByNameAndPrice(String name, Double price, int pageNo, int pageSize);

	List<Product> findByStatusOutOfStockAndDeleted();

	List<Product> findByStatus(ProductStatus status);

	List<Product> findByStatus(ProductStatus status, int pageNo, int pageSize);
	
	Long countByStatus(ProductStatus status);
	
	void updateStatusById(Long id, ProductStatus status);
	
	List<ProductStatusSummary> countProductByStatus();

}
