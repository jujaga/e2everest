package com.jujaga.e2e.model.export.body;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.generic.SET;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.util.EverestUtils;
import com.jujaga.emr.dao.CaseManagementNoteDao;
import com.jujaga.emr.model.CaseManagementNote;

public class EncountersModelTest {
	private static ApplicationContext context;
	private static CaseManagementNoteDao dao;
	private static CaseManagementNote encounter;
	private static EncountersModel encountersModel;

	private static CaseManagementNote nullEncounter;
	private static EncountersModel nullEncountersModel;

	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);
		context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		dao = context.getBean(CaseManagementNoteDao.class);
		encounter = dao.find(new Long(0));//dao.getNotesByDemographic(Constants.Runtime.VALID_DEMOGRAPHIC.toString()).get(0);
		encounter = new CaseManagementNote(); // Temporary
		encountersModel = new EncountersModel(encounter);

		nullEncounter = new CaseManagementNote();
		nullEncountersModel = new EncountersModel(nullEncounter);
	}

	@Test
	public void encountersModelNullTest() {
		assertNotNull(new EncountersModel(null));
	}

	@Test
	public void textSummaryTest() {
		String text = encountersModel.getTextSummary();
		assertNotNull(text);
	}

	@Test
	public void textSummaryNullTest() {
		String text = nullEncountersModel.getTextSummary();
		assertNotNull(text);
	}

	@Ignore // No test data yet
	@Test
	public void idTest() {
		SET<II> ids = encountersModel.getIds();
		assertNotNull(ids);

		II id = ids.get(0);
		assertNotNull(id);
		assertEquals(Constants.EMR.EMR_OID, id.getRoot());
		assertEquals(Constants.EMR.EMR_VERSION, id.getAssigningAuthorityName());
		assertFalse(EverestUtils.isNullorEmptyorWhitespace(id.getExtension()));
		assertTrue(id.getExtension().contains(Constants.IdPrefixes.Encounters.toString()));
		assertTrue(id.getExtension().contains(encounter.getId().toString()));
	}

	@Test
	public void idNullTest() {
		SET<II> ids = nullEncountersModel.getIds();
		assertNotNull(ids);
	}
}
