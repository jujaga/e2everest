package com.jujaga.emr.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.jujaga.emr.model.CaseManagementNoteExt;

@Repository
public class CaseManagementNoteExtDao extends AbstractDao<CaseManagementNoteExt> {
	public CaseManagementNoteExtDao() {
		super(CaseManagementNoteExt.class);
	}

	@SuppressWarnings("unchecked")
	public List<CaseManagementNoteExt> getExtByNote(Long noteId) {
		String sqlCommand = "SELECT x FROM " + modelClass.getName() + " x WHERE x.noteId=?1 ORDER BY x.id DESC";
		Query query = entityManager.createQuery(sqlCommand);
		query.setParameter(1, noteId);
		return query.getResultList();
	}
}
