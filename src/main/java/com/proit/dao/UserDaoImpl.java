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
	public List<User> findAll(int pageNo, int pageSize) {
		EntityManager em = JpaHelper.instance();
		String jpql = "SELECT u FROM User u";
		TypedQuery<User> query = em.createQuery(jpql, User.class);
		query.setFirstResult(pageNo * pageSize);
		query.setMaxResults(pageSize);
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
		try {
			//Dùng method find sẵn có trong EM để tìm entity qua khoá chính, không cần truyền jpql thủ công
			return em.find(User.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<User> findByName(String name) {
		EntityManager em = JpaHelper.instance();
		String jpql = "SELECT u FROM User u WHERE u.name LIKE :name";
		TypedQuery<User> query = em.createQuery(jpql, User.class);
		query.setParameter("name", "%" + name + "%");
		return query.getResultList();
	}

	@Override
	public User findByEmail(String email) {
		EntityManager em = JpaHelper.instance();
		String jpql = "SELECT u FROM User u WHERE u.email = :email";
		try {
			TypedQuery<User> query = em.createQuery(jpql, User.class);
			query.setParameter("email", email);
			return query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Override
	public User findByEmailAndPassword(String email, String password) {
		EntityManager em = JpaHelper.instance();
		String jpql = "SELECT u FROM User u WHERE u.email = :email AND u.password = :password";
		try {
			TypedQuery<User> query = em.createQuery(jpql, User.class);
			query.setParameter("email", email);
			query.setParameter("password", password);
			return query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Long countAllUsers() {
		EntityManager em = JpaHelper.instance();
		String jpql = "SELECT COUNT(u) FROM User u";
		TypedQuery<Long> query = em.createQuery(jpql, Long.class);
		return query.getSingleResult();
	}

}
