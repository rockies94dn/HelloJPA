package com.proit.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.proit.dto.ProductStatusSummary;
import com.proit.jpa.JpaHelper;
import com.proit.model.Category;
import com.proit.model.Product;
import com.proit.model.ProductStatus;
import com.proit.model.ProductsInCategoryStatistics;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.persistence.TypedQuery;

public class ProductDaoImpl implements ProductDao {
	@Override
	public void insert(Product entity) {
		EntityManager em = JpaHelper.instance();
		EntityTransaction trans = em.getTransaction();

		try {
			trans.begin();
			em.persist(entity);
			trans.commit(); // hoàn tất thay đổi
		} catch (Exception e) {
			e.printStackTrace();
			trans.rollback(); // huỷ bỏ thay đổi
		}

	}

	@Override
	public void update(Long id, Product entity) {
		EntityManager em = JpaHelper.instance();
		EntityTransaction trans = em.getTransaction();

		try {
			trans.begin();
			Product found = findById(id);
			BeanUtils.copyProperties(found, entity);
			em.merge(entity);
			trans.commit(); // hoàn tất thay đổi
		} catch (Exception e) {
			e.printStackTrace();
			trans.rollback(); // huỷ bỏ thay đổi
		}
	}

	@Override
	public void update(Long id, String imageUrl) {
		EntityManager em = JpaHelper.instance();
		String jpql = "UPDATE Product SET imageUrl = :imageUrl WHERE id = :id";
		EntityTransaction trans = em.getTransaction();

		try {
			trans.begin();
			Query query = em.createQuery(jpql);
			query.setParameter("imageUrl", imageUrl);
			query.setParameter("id", id);
			query.executeUpdate();
			trans.commit(); // hoàn tất thay đổi
		} catch (Exception e) {
			e.printStackTrace();
			trans.rollback(); // huỷ bỏ thay đổi
		}
	}

	@Override
	public void delete(Long id) {
		EntityManager em = JpaHelper.instance();
		EntityTransaction trans = em.getTransaction();

		try {
			trans.begin();
			Product found = findById(id);
			if (found != null) {
				em.remove(found);
				trans.commit(); // hoàn tất thay đổi
			} else {
				System.out.println("Product not found!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			trans.rollback(); // huỷ bỏ thay đổi
		}
	}

	@Override
	public Product findById(Long id) {
		EntityManager em = JpaHelper.instance();
		Product found = em.find(Product.class, id);
		return found;
	}

	@Override
	public List<Product> findAll() {
		EntityManager em = JpaHelper.instance();
		String jpql = "SELECT c FROM Product c";
		TypedQuery<Product> query = em.createQuery(jpql, Product.class);
		return query.getResultList();
	}

	@Override
	public List<Product> findAll(int pageNo, int pageSize) {
		EntityManager em = JpaHelper.instance();
		String jpql = "SELECT p FROM Product p";
		TypedQuery<Product> query = em.createQuery(jpql, Product.class);

		query.setFirstResult(pageNo * pageSize);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}

	@Override
	public Long countAll() {
		EntityManager em = JpaHelper.instance();
		String jpql = "SELECT COUNT(p) FROM Product p";
		TypedQuery<Long> query = em.createQuery(jpql, Long.class);
		return query.getSingleResult();
	}

	@Override
	public List<Product> findByName(String name) {
		EntityManager em = JpaHelper.instance();
		String jpql = "SELECT p FROM Product p WHERE p.name LIKE :name";
		TypedQuery<Product> query = em.createQuery(jpql, Product.class);
		query.setParameter("name", "%" + name + "%");
		return query.getResultList();
	}

	@Override
	public List<Product> findByName(String name, int pageNo, int pageSize) {
		EntityManager em = JpaHelper.instance();
		String jpql = "SELECT p FROM Product p WHERE p.name LIKE :name";
		TypedQuery<Product> query = em.createQuery(jpql, Product.class);
		query.setParameter("name", "%" + name + "%");
		query.setFirstResult(pageNo * pageSize);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}

	@Override
	public Long countByName(String name) {
		EntityManager em = JpaHelper.instance();
		String jpql = "SELECT COUNT(p) FROM Product p WHERE p.name LIKE :name";
		TypedQuery<Long> query = em.createQuery(jpql, Long.class);
		query.setParameter("name", "%" + name + "%");
		return query.getSingleResult();
	}

	@Override
	public List<Product> findByCategory(Long categoryId) {
		EntityManager em = JpaHelper.instance();
		String jpql = "SELECT p FROM Product p WHERE p.category.categoryId = :categoryId";
		TypedQuery<Product> query = em.createQuery(jpql, Product.class);
		query.setParameter("categoryId", categoryId);
		return query.getResultList();
	}

	@Override
	public List<Product> findByCategory(Long categoryId, int pageNo, int pageSize) {
		EntityManager em = JpaHelper.instance();
		String jpql = "SELECT p FROM Product p WHERE p.category.categoryId = :categoryId";
		TypedQuery<Product> query = em.createQuery(jpql, Product.class);
		query.setParameter("categoryId", categoryId);
		query.setFirstResult(pageNo * pageSize);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}

	@Override
	public List<Product> findByCategory(Category category) {
		return findByCategory((long) category.getCategoryId());
	}

	@Override
	public List<Product> findByCategory(Category category, int pageNo, int pageSize) {
		return findByCategory((long) category.getCategoryId(), pageNo, pageSize);
	}

	@Override
	public Long countByCategory(Long categoryId) {
		EntityManager em = JpaHelper.instance();
		String jpql = "SELECT COUNT(p) FROM Product p WHERE p.category.categoryId = :categoryId";
		TypedQuery<Long> query = em.createQuery(jpql, Long.class);
		query.setParameter("categoryId", categoryId);
		return query.getSingleResult();
	}

	@Override
	public Long countByCategory(Category category) {
		return countByCategory((long) category.getCategoryId());
	}

	@Override
	public List<ProductsInCategoryStatistics> countProductsByCategory() {
		EntityManager em = JpaHelper.instance();
		String jpql = "SELECT new com.proit.model.ProductsInCategoryStatistics(c.name, " + "count(p), "
				+ "sum(p.price), " + "avg(p.price), " + "sum(p.quantity)" + ") "
				+ "FROM Product p JOIN p.category c GROUP BY c.name";
		TypedQuery<ProductsInCategoryStatistics> query = em.createQuery(jpql, ProductsInCategoryStatistics.class);
		return query.getResultList();
	}

	@Override
	public List<Product> findByNameAndPrice(String name, Double price) {
		EntityManager em = JpaHelper.instance();
		String jpql = "SELECT p FROM Product p WHERE p.name LIKE :name AND p.price = :price ORDER BY p.name, p.price";

		TypedQuery<Product> query = em.createQuery(jpql, Product.class);
		query.setParameter("name", "%" + name + "%");
		query.setParameter("price", price);
		return query.getResultList();
	}

	@Override
	public List<Product> findByNameAndPrice(String name, Double price, int pageNo, int pageSize) {
		EntityManager em = JpaHelper.instance();
		String jpql = "SELECT p FROM Product p WHERE p.name LIKE :name AND p.price = :price ORDER BY p.name, p.price";

		TypedQuery<Product> query = em.createQuery(jpql, Product.class);
		query.setParameter("name", "%" + name + "%");
		query.setParameter("price", price);
		query.setFirstResult(pageNo * pageSize);
		query.setMaxResults(pageSize);

		return query.getResultList();
	}

	@Override
	public Long countByNameAndPrice(String name, Double price) {
		EntityManager em = JpaHelper.instance();
		String jpql = "SELECT COUNT(p) FROM Product p WHERE p.name LIKE :name AND p.price = :price";

		TypedQuery<Long> query = em.createQuery(jpql, Long.class);
		query.setParameter("name", "%" + name + "%");
		query.setParameter("price", price);

		return query.getSingleResult();
	}

	@Override
	public List<Product> findByStatusOutOfStockAndDeleted() {
		EntityManager em = JpaHelper.instance();
		String jpql = "SELECT p FROM Product p WHERE p.status IN (:status1, :status2)";

		TypedQuery<Product> query = em.createQuery(jpql, Product.class);
		query.setParameter("status1", ProductStatus.OUT_OF_STOCK);
		query.setParameter("status2", ProductStatus.DELETED);

		return query.getResultList();
	}

	@Override
	public List<Product> findByStatus(ProductStatus status) {
		EntityManager em = JpaHelper.instance();
		TypedQuery<Product> query = em.createQuery("Product.findByStatus", Product.class);
		query.setParameter("status", status);
		return query.getResultList();
	}

	@Override
	public List<Product> findByStatus(ProductStatus status, int pageNo, int pageSize) {
		EntityManager em = JpaHelper.instance();
		TypedQuery<Product> query = em.createNamedQuery("Product.findByStatus", Product.class);
		query.setParameter("status", status);
		query.setFirstResult(pageNo * pageSize);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}

	@Override
	public Long countByStatus(ProductStatus status) {
		EntityManager em = JpaHelper.instance();
		TypedQuery<Long> query = em.createNamedQuery("Product.countByStatus", Long.class);
		query.setParameter("status", status);
		return query.getSingleResult();
	}

	@Override
	public void updateStatusById(Long id, ProductStatus status) {
		EntityManager em = JpaHelper.instance();
		EntityTransaction trans = em.getTransaction();

		try {
			trans.begin();
			Query query = em.createNamedQuery("Product.updateStatus");
			query.setParameter("status", status);
			query.setParameter("id", id);
			query.executeUpdate();
			trans.commit();
			em.clear();
		} catch (Exception e) {
			e.printStackTrace();
			trans.rollback();
		}

	}

	@Override
	public List<ProductStatusSummary> countProductByStatus() {
		EntityManager em = JpaHelper.instance();
		String storedProcedureName = "spSummaryByStatus";
		StoredProcedureQuery query = em.createStoredProcedureQuery(storedProcedureName);
		query.execute();
		List<Object[]> results = query.getResultList();
		List<ProductStatusSummary> summaries = new ArrayList();
		
		for (Object[] result : results) {
			Integer status = (Integer) result[0];
			Integer count = (Integer) result[1];
			
			ProductStatusSummary summary = new ProductStatusSummary(status, count.longValue());
			summaries.add(summary);
		}
		
		return summaries;
	}

}
