package com.jujaga.emr.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.jujaga.emr.model.CaseManagementIssueNotes;

@Repository
public class CaseManagementIssueNotesDao extends AbstractDao<CaseManagementIssueNotes> {
	public CaseManagementIssueNotesDao() {
		super(CaseManagementIssueNotes.class);
	}

	@SuppressWarnings("unchecked")
	public List<Integer> getNoteIdsWhichHaveIssues(String[] issueId) {
		if(issueId == null || issueId.length == 0 || (issueId.length == 1 && issueId[0].equals(""))) {
			return null;
		}

		StringBuilder issueIdList = new StringBuilder();
		for(String i : issueId) {
			if(issueIdList.length() > 0) {
				issueIdList.append(",");
			}
			issueIdList.append(i);
		}

		String sqlCommand = "SELECT note_id FROM " + modelClass.getName() + " x WHERE id in (" + issueIdList.toString() + ")";
		Query query = entityManager.createQuery(sqlCommand);
		return query.getResultList();
	}
}
