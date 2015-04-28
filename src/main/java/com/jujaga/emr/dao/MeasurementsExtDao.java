package com.jujaga.emr.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.jujaga.emr.model.MeasurementsExt;

@Repository
public class MeasurementsExtDao extends AbstractDao<MeasurementsExt> {
	public MeasurementsExtDao() {
		super(MeasurementsExt.class);
	}

	public MeasurementsExt getMeasurementsExtByMeasurementIdAndKeyVal(Integer measurementId, String keyVal) {
		String sqlCommand = "SELECT x FROM " + modelClass.getName() + " x WHERE x.measurementId = ?1 AND x.keyVal = ?2";
		Query query = entityManager.createQuery(sqlCommand);
		query.setParameter(1, measurementId);
		query.setParameter(2, keyVal);
		return getSingleResultOrNull(query);
	}

	@SuppressWarnings("unchecked")
	public List<MeasurementsExt> getMeasurementsExtByMeasurementId(Integer measurementId) {
		String sqlCommand = "SELECT x FROM " + modelClass.getName() + " x WHERE x.measurementId = ?1";
		Query query = entityManager.createQuery(sqlCommand);
		query.setParameter(1, measurementId);
		return query.getResultList();
	}
}
