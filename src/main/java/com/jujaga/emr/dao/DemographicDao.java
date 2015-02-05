package com.jujaga.emr.dao;

import com.jujaga.emr.model.Demographic;

public class DemographicDao extends AbstractDao<Demographic> {
	public DemographicDao() {
		super(Demographic.class);
	}

	public Demographic getDemographic(Integer demographicNo) {
		Demographic demographic = entityManager.find(Demographic.class, demographicNo);
		return demographic;
	}
}
