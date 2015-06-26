package com.jujaga.emr.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.jujaga.emr.model.CaseManagementIssue;

@Repository
public class CaseManagementIssueDao extends AbstractDao<CaseManagementIssue> {
	public CaseManagementIssueDao() {
		super(CaseManagementIssue.class);
	}

	@SuppressWarnings("unchecked")
	public List<CaseManagementIssue> getIssuesByDemographic(String demographic_no) {
		String sqlCommand = "SELECT x FROM " + modelClass.getName() + " x WHERE x.demographic_no=?1";
		Query query = entityManager.createQuery(sqlCommand);
		query.setParameter(1, demographic_no);
		return query.getResultList();
	}
}
