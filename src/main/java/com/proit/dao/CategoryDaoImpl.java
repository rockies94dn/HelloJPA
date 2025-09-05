package com.proit.dao;

import java.util.List;

import com.proit.jpa.JpaHelper;
import com.proit.model.Category;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class CategoryDaoImpl implements CategoryDao {
	
	@Override
	public void insert(Category entity) {
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
	public void update(int id, Category entity) {
		EntityManager em = JpaHelper.instance();
		EntityTransaction trans = em.getTransaction();

		try {
			trans.begin();
			Category found = findById(id);
			found.setName(entity.getName());
			em.merge(entity);
			trans.commit(); // hoàn tất thay đổi
		} catch (Exception e) {
			e.printStackTrace();
			trans.rollback(); // huỷ bỏ thay đổi
		}
	}

	@Override
	public void delete(int id) {
		EntityManager em = JpaHelper.instance();
		EntityTransaction trans = em.getTransaction();

		try {
			trans.begin();
			Category found = findById(id);
			if (found != null) {
				em.remove(found);
				trans.commit(); // hoàn tất thay đổi
			} else {
				System.out.println("Category not found!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			trans.rollback(); // huỷ bỏ thay đổi
		}
	}

	@Override
	public Category findById(int id) {
		EntityManager em = JpaHelper.instance();
		Category found = em.find(Category.class, id);
		return found;
	}

	@Override
	public List<Category> findAll() {
		EntityManager em = JpaHelper.instance();
		String jpql = "SELECT c FROM Category c";
		TypedQuery<Category> query = em.createQuery(jpql, Category.class);
		return query.getResultList();
	}

	@Override
	public List<Category> findAll(int pageNo, int pageSize) {
		EntityManager em = JpaHelper.instance();
		String jpql = "SELECT c FROM Category c";
		TypedQuery<Category> query = em.createQuery(jpql, Category.class);

		query.setFirstResult(pageNo * pageSize);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}

	@Override
	public List<Category> findByName(String name) {
		EntityManager em = JpaHelper.instance();
		String jpql = "SELECT c FROM Category c WHERE c.name LIKE :name";
		TypedQuery<Category> query = em.createQuery(jpql, Category.class);
		query.setParameter("name", "%" + name + "%");
		return query.getResultList();
	}
	
}
