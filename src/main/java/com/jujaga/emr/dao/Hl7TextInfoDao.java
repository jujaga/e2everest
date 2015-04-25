package com.jujaga.emr.dao;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.jujaga.emr.model.Hl7TextInfo;

@Repository
public class Hl7TextInfoDao extends AbstractDao<Hl7TextInfo> {
	public Hl7TextInfoDao() {
		super(Hl7TextInfo.class);
	}

	public Hl7TextInfo findLabId(Integer labId) {
		String sqlCommand="SELECT x FROM " + modelClass.getName() + " x WHERE x.labNumber=?1";
		Query query = entityManager.createQuery(sqlCommand);
		query.setParameter(1, labId);
		return getSingleResultOrNull(query);
	}
}
