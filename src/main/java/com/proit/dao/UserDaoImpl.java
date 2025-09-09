package com.proit.dao;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.proit.common.CustomBeanUtils;
import com.proit.jpa.JpaHelper;
import com.proit.model.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class UserDaoImpl implements UserDao {
	@Override
	public List<User> findAll() {
		EntityManager em = JpaHelper.instance();
		String jpql = "SELECT u FROM User u";
		TypedQuery<User> query = em.createQuery(jpql, User.class);
		return query.getResultList();
	}

	@Override
	public void insert(User entity) {
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
	public void update(Long id, User entity) {
		EntityManager em = JpaHelper.instance();
		EntityTransaction trans = em.getTransaction();

		try {
			trans.begin();
			User found = findById(id);
			if (found != null) {
				BeanUtils.copyProperties(found, entity);
				em.merge(entity);
				trans.commit(); // hoàn tất thay đổi
			} else {
				System.out.println("User not found!");
			}
			
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
			User found = findById(id);
			if (found != null) {
				em.remove(found);
				trans.commit(); //Xoá thành công
			} else {
				System.out.println("User not found!");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			trans.rollback(); // huỷ bỏ thay đổi
		}
	}

	public User findById(Long id) {
		EntityManager em = JpaHelper.instance();
		String jpql = "SELECT u FROM User u WHERE u.id = :id";
		TypedQuery<User> query = em.createQuery(jpql, User.class);
		query.setParameter("id", id);
		return query.getSingleResult();

	}

}
