package com.jujaga.emr.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.jujaga.emr.model.Dxresearch;

@Repository
public class DxresearchDao extends AbstractDao<Dxresearch> {
	public DxresearchDao() {
		super(Dxresearch.class);
	}

	@SuppressWarnings("unchecked")
	public List<Dxresearch> getDxResearchItemsByPatient(Integer demographicNo) {
		String sqlCommand = "SELECT x FROM Dxresearch x WHERE x.demographicNo=?1";
		Query query = entityManager.createQuery(sqlCommand);
		query.setParameter(1, demographicNo);
		List<Dxresearch> items = query.getResultList();
		return items;
	}
}