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
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ActStatus;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.util.EverestUtils;
import com.jujaga.emr.dao.CaseManagementNoteDao;
import com.jujaga.emr.model.CaseManagementNote;

public class RiskFactorsModelTest {
	private static ApplicationContext context;
	private static CaseManagementNoteDao dao;
	private static CaseManagementNote riskFactor;
	private static RiskFactorsModel riskFactorsModel;

	private static CaseManagementNote nullRiskFactor;
	private static RiskFactorsModel nullRiskFactorsModel;

	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);
		context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		dao = context.getBean(CaseManagementNoteDao.class);
		riskFactor = dao.getNotesByDemographic(Constants.Runtime.VALID_DEMOGRAPHIC.toString()).get(2);
		riskFactorsModel = new RiskFactorsModel(riskFactor);

		nullRiskFactor = new CaseManagementNote();
		nullRiskFactorsModel = new RiskFactorsModel(nullRiskFactor);
	}

	@Test
	public void riskFactorsModelNullTest() {
		assertNotNull(new RiskFactorsModel(null));
	}

	@Test
	public void textSummaryTest() {
		String text = riskFactorsModel.getTextSummary();
		assertNotNull(text);
	}

	@Test
	public void textSummaryNullTest() {
		String text = nullRiskFactorsModel.getTextSummary();
		assertNotNull(text);
	}

	@Test
	public void idTest() {
		SET<II> ids = riskFactorsModel.getIds();
		assertNotNull(ids);

		II id = ids.get(0);
		assertNotNull(id);
		assertEquals(Constants.EMR.EMR_OID, id.getRoot());
		assertEquals(Constants.EMR.EMR_VERSION, id.getAssigningAuthorityName());
		assertFalse(EverestUtils.isNullorEmptyorWhitespace(id.getExtension()));
		assertTrue(id.getExtension().contains(Constants.IdPrefixes.RiskFactors.toString()));
		assertTrue(id.getExtension().contains(riskFactor.getId().toString()));
	}

	@Test
	public void idNullTest() {
		SET<II> ids = nullRiskFactorsModel.getIds();
		assertNotNull(ids);
	}

	@Test
	public void codeTest() {
		CD<String> code = riskFactorsModel.getCode();
		assertNotNull(code);
		assertEquals("40514009", code.getCode());
		assertEquals(Constants.CodeSystems.SNOMED_CT_OID, code.getCodeSystem());
		assertEquals(Constants.CodeSystems.SNOMED_CT_NAME, code.getCodeSystemName());
	}

	@Test
	public void codeNullTest() {
		CD<String> code = nullRiskFactorsModel.getCode();
		assertNotNull(code);
		assertEquals("40514009", code.getCode());
		assertEquals(Constants.CodeSystems.SNOMED_CT_OID, code.getCodeSystem());
		assertEquals(Constants.CodeSystems.SNOMED_CT_NAME, code.getCodeSystemName());
	}

	@Test
	public void statusCodeTest() {
		ActStatus status = riskFactorsModel.getStatusCode();
		assertNotNull(status);
		assertEquals(ActStatus.Active, status);
	}

	@Test
	public void statusCodeNullTest() {
		ActStatus status = nullRiskFactorsModel.getStatusCode();
		assertNotNull(status);
		assertEquals(ActStatus.Completed, status);
	}
}
