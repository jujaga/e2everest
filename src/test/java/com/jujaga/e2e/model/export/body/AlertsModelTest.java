package com.jujaga.e2e.model.export.body;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.datatypes.generic.SET;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.util.EverestUtils;
import com.jujaga.emr.dao.CaseManagementNoteDao;
import com.jujaga.emr.model.CaseManagementNote;

public class AlertsModelTest {
	private static ApplicationContext context;
	private static CaseManagementNoteDao dao;
	private static CaseManagementNote alert;
	private static AlertsModel alertsModel;

	private static CaseManagementNote nullAlert;
	private static AlertsModel nullAlertsModel;

	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);
		context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		dao = context.getBean(CaseManagementNoteDao.class);
		alert = dao.getNotesByDemographic(Constants.Runtime.VALID_DEMOGRAPHIC.toString()).get(5);
		alertsModel = new AlertsModel(alert);

		nullAlert = new CaseManagementNote();
		nullAlertsModel = new AlertsModel(nullAlert);
	}

	@Test
	public void alertsModelNullTest() {
		assertNotNull(new AlertsModel(null));
	}

	@Test
	public void textSummaryTest() {
		String text = alertsModel.getTextSummary();
		assertNotNull(text);
	}

	@Test
	public void textSummaryNullTest() {
		String text = nullAlertsModel.getTextSummary();
		assertNotNull(text);
	}

	@Test
	public void idTest() {
		SET<II> ids = alertsModel.getIds();
		assertNotNull(ids);

		II id = ids.get(0);
		assertNotNull(id);
		assertEquals(Constants.EMR.EMR_OID, id.getRoot());
		assertEquals(Constants.EMR.EMR_VERSION, id.getAssigningAuthorityName());
		assertFalse(EverestUtils.isNullorEmptyorWhitespace(id.getExtension()));
		assertTrue(id.getExtension().contains(Constants.IdPrefixes.Alerts.toString()));
		assertTrue(id.getExtension().contains(alert.getId().toString()));
	}

	@Test
	public void idNullTest() {
		SET<II> ids = nullAlertsModel.getIds();
		assertNotNull(ids);
	}

	@Test
	public void codeTest() {
		CD<String> code = alertsModel.getCode();
		assertNotNull(code);
		assertTrue(code.isNull());
		assertEquals(NullFlavor.NoInformation, code.getNullFlavor().getCode());
	}

	@Test
	public void codeNullTest() {
		CD<String> code = nullAlertsModel.getCode();
		assertNotNull(code);
		assertTrue(code.isNull());
		assertEquals(NullFlavor.NoInformation, code.getNullFlavor().getCode());
	}
}
