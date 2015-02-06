package com.jujaga.emr.dao;

import org.springframework.stereotype.Repository;

import com.jujaga.emr.model.Demographic;

@Repository
public class DemographicDao extends AbstractDao<Demographic> {
	public DemographicDao() {
		super(Demographic.class);
	}

	public Demographic getDemographic(Integer demographicNo) {
		Demographic demographic = entityManager.find(Demographic.class, demographicNo);
		return demographic;
	}
}
