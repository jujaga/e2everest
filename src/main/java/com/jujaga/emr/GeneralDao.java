package com.jujaga.emr;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.jujaga.emr.model.Demographic;

/**
 * This class acts as a quick and dirty Data Access Object for the EMR database
 * Obviously you'd want to create a proper DAO model design for non-mockups
 * 
 */
public class GeneralDao {
	@PersistenceContext(unitName="emr")
	private EntityManager manager;

	public GeneralDao() {
		/*if(factory == null) {
			factory = Persistence.createEntityManagerFactory("emr");
		}
		manager = factory.createEntityManager();*/
		manager.getTransaction().begin();
	}

	public Demographic getDemographic(Integer demographicNo) {
		Demographic demographic = manager.find(Demographic.class, demographicNo);
		return demographic;
	}
}
