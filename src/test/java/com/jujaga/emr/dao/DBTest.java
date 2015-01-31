package com.jujaga.emr.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import com.jujaga.emr.model.Demographic;

public class DBTest {
	@Test
	public void test() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();

		Demographic demographic = new Demographic();
		demographic.setHin("test");
		session.persist(demographic);

		tx.commit();
	}
}
