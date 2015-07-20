package com.jujaga.e2e.model.export.body;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.datatypes.ED;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.TS;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.datatypes.generic.IVL;
import org.marc.everest.datatypes.generic.SET;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.util.EverestUtils;
import com.jujaga.emr.dao.CaseManagementNoteDao;
import com.jujaga.emr.model.CaseManagementNote;

public class FamilyHistoryModelTest {
	private static ApplicationContext context;
	private static CaseManagementNoteDao dao;
	private static CaseManagementNote familyHistory;
	private static FamilyHistoryModel familyHistoryModel;

	private static CaseManagementNote nullFamilyHistory;
	private static FamilyHistoryModel nullFamilyHistoryModel;

	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);
		context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		dao = context.getBean(CaseManagementNoteDao.class);
		familyHistory = dao.getNotesByDemographic(Constants.Runtime.VALID_DEMOGRAPHIC.toString()).get(2);
		familyHistoryModel = new FamilyHistoryModel(familyHistory);

		nullFamilyHistory = new CaseManagementNote();
		nullFamilyHistoryModel = new FamilyHistoryModel(nullFamilyHistory);
	}

	@Test
	public void familyHistoryModelNullTest() {
		assertNotNull(new FamilyHistoryModel(null));
	}

	@Test
	public void textSummaryTest() {
		String text = familyHistoryModel.getTextSummary();
		assertNotNull(text);
	}

	@Test
	public void textSummaryNullTest() {
		String text = nullFamilyHistoryModel.getTextSummary();
		assertNotNull(text);
	}

	@Test
	public void idTest() {
		SET<II> ids = familyHistoryModel.getIds();
		assertNotNull(ids);

		II id = ids.get(0);
		assertNotNull(id);
		assertEquals(Constants.EMR.EMR_OID, id.getRoot());
		assertEquals(Constants.EMR.EMR_VERSION, id.getAssigningAuthorityName());
		assertFalse(EverestUtils.isNullorEmptyorWhitespace(id.getExtension()));
		assertTrue(id.getExtension().contains(Constants.IdPrefixes.FamilyHistory.toString()));
		assertTrue(id.getExtension().contains(familyHistory.getId().toString()));
	}

	@Test
	public void idNullTest() {
		SET<II> ids = nullFamilyHistoryModel.getIds();
		assertNotNull(ids);
	}

	@Test
	public void codeTest() {
		CD<String> code = familyHistoryModel.getCode();
		assertNotNull(code);
		assertTrue(code.isNull());
		assertEquals(NullFlavor.NoInformation, code.getNullFlavor().getCode());
	}

	@Test
	public void codeNullTest() {
		CD<String> code = nullFamilyHistoryModel.getCode();
		assertNotNull(code);
		assertTrue(code.isNull());
		assertEquals(NullFlavor.NoInformation, code.getNullFlavor().getCode());
	}

	@Test
	public void textTest() {
		ED text = familyHistoryModel.getText();
		assertNotNull(text);
		assertEquals(familyHistory.getNote(), new String(text.getData()));
	}

	@Test
	public void textNullTest() {
		ED text = nullFamilyHistoryModel.getText();
		assertNull(text);
	}

	@Test
	public void effectiveTimeTest() {
		IVL<TS> ivl = familyHistoryModel.getEffectiveTime();
		assertNotNull(ivl);
		assertEquals(EverestUtils.buildTSFromDate(familyHistory.getObservation_date()), ivl.getLow());
	}

	@Test
	public void effectiveTimeNullTest() {
		IVL<TS> ivl = nullFamilyHistoryModel.getEffectiveTime();
		assertNotNull(ivl);
		assertTrue(ivl.isNull());
		assertEquals(NullFlavor.NoInformation, ivl.getNullFlavor().getCode());
	}

	@Test
	public void valueTest() {
		CD<String> value = familyHistoryModel.getValue();
		assertNotNull(value);
		assertTrue(value.isNull());
		assertEquals(NullFlavor.Unknown, value.getNullFlavor().getCode());
	}

	@Test
	public void valueNullTest() {
		CD<String> value = nullFamilyHistoryModel.getValue();
		assertNotNull(value);
		assertTrue(value.isNull());
		assertEquals(NullFlavor.Unknown, value.getNullFlavor().getCode());
	}
}
