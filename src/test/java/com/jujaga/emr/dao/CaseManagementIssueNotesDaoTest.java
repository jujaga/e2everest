package com.jujaga.emr.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.emr.model.CaseManagementIssueNotes;
import com.jujaga.emr.util.EntityModelUtils;

public class CaseManagementIssueNotesDaoTest {
	private static ApplicationContext context;
	private static CaseManagementIssueNotesDao caseManagementIssueNotesDao;

	@BeforeClass
	public static void beforeClass() {
		context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		caseManagementIssueNotesDao = context.getBean(CaseManagementIssueNotesDao.class);
	}

	@Test
	public void caseManagementNoteTest() {
		EntityModelUtils.invokeMethodsForModelClass(new CaseManagementIssueNotes());
		CaseManagementIssueNotes entity = (CaseManagementIssueNotes) EntityModelUtils.generateTestDataForModelClass(new CaseManagementIssueNotes());
		caseManagementIssueNotesDao.persist(entity);
		assertNotNull(entity.getId());
	}

	@Test
	public void getNoteIdsWhichHaveIssuesTest() {
		String[] issueId = {"1", "3"}; // TODO replace with runtime numbers
		List<Integer> ids = caseManagementIssueNotesDao.getNoteIdsWhichHaveIssues(issueId);
		assertNotNull(ids);
		assertEquals(2, ids.size());
	}

	@Test
	public void getNoteIdsWhichHaveIssuesNullTest() {
		List<Integer> ids = caseManagementIssueNotesDao.getNoteIdsWhichHaveIssues(null);
		assertNull(ids);
	}
}
