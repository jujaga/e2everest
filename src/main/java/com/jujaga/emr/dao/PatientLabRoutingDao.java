package com.jujaga.emr.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.jujaga.emr.model.PatientLabRouting;

@Repository
public class PatientLabRoutingDao extends AbstractDao<PatientLabRouting> {
	public PatientLabRoutingDao() {
		super(PatientLabRouting.class);
	}

	@SuppressWarnings("unchecked")
	public List<PatientLabRouting> findByDemographicAndLabType(Integer demoNo, String labType) {
		String sqlCommand = "SELECT x FROM " + modelClass.getName() + " x WHERE x.demographicNo=?1 AND x.labType=?2";
		Query query = entityManager.createQuery(sqlCommand);
		query.setParameter(1, demoNo);
		query.setParameter(2, labType);
		return query.getResultList();
	}
}
