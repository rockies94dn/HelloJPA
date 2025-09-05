package com.proit.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaHelper {
	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("HelloJPA");
	private static EntityManager em = null;

	public static EntityManager instance() {
		if (em == null) {
			em = factory.createEntityManager();
		}
		return em;
	}

}
