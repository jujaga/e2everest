package com.jujaga.emr.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.jujaga.emr.model.Drug;

@Repository
public class DrugDao extends AbstractDao<Drug> {
	public DrugDao() {
		super(Drug.class);
	}

	public List<Drug> findByDemographicId(Integer demographicId) {
		return findByDemographicId(demographicId, null);
	}

	@SuppressWarnings("unchecked")
	public List<Drug> findByDemographicId(Integer demographicId, Boolean archived) {
		String sqlCommand = "SELECT x FROM Drug x WHERE x.demographicId=?1" + (archived == null ? "" : " and x.archived=?2");
		Query query = entityManager.createQuery(sqlCommand);
		query.setParameter(1, demographicId);
		if (archived != null) {
			query.setParameter(2, archived);
		}

		List<Drug> results = query.getResultList();
		return (results);
	}
}
