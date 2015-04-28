package com.jujaga.emr.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.jujaga.emr.model.Measurement;

@Repository
public class MeasurementDao extends AbstractDao<Measurement> {
	public MeasurementDao() {
		super(Measurement.class);
	}

	@SuppressWarnings("unchecked")
	public List<Measurement> findByDemographicNo(Integer demographicNo) {
		String sqlCommand = "SELECT x FROM " + modelClass.getName() + " x WHERE x.demographicId=?1";
		Query query = entityManager.createQuery(sqlCommand);
		query.setParameter(1, demographicNo);
		return query.getResultList();
	}
}
