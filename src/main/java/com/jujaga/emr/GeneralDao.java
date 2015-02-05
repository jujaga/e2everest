package com.jujaga.emr;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.jujaga.emr.model.Demographic;

/**
 * This class acts as a quick and dirty Data Access Object for the EMR database
 * Obviously you'd want to create a proper DAO model design for non-mockups
 * 
 */
public class GeneralDao {
	private static EntityManagerFactory factory = null;
	private EntityManager manager;

	public GeneralDao() {
		if(factory == null) {
			factory = Persistence.createEntityManagerFactory("sqlite-jpa");
		}
		manager = factory.createEntityManager();
		manager.getTransaction().begin();
	}

	public Demographic getDemographic(Integer demographicNo) {
		return manager.find(Demographic.class, demographicNo);
	}
}
