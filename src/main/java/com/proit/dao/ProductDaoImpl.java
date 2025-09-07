package com.proit.dao;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.proit.jpa.JpaHelper;
import com.proit.model.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
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
}
