package com.jujaga.emr.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.jujaga.emr.model.Prevention;

@Repository
public class PreventionDao extends AbstractDao<Prevention> {
	public PreventionDao() {
		super(Prevention.class);
	}

	@SuppressWarnings("unchecked")
	public List<Prevention> findNotDeletedByDemographicId(Integer demographicId) {
		String sqlCommand = "SELECT x FROM " + modelClass.getName() + " x WHERE x.demographicId=?1 AND x.deleted=?2";
		Query query = entityManager.createQuery(sqlCommand);
		query.setParameter(1, demographicId);
		query.setParameter(2, '0');
		return query.getResultList();
	}
}
