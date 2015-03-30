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

import com.jujaga.e2e.constant.BodyConstants;
import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.util.EverestUtils;
import com.jujaga.emr.dao.DxresearchDao;
import com.jujaga.emr.model.Dxresearch;

public class ProblemsModelTest {
	private static ApplicationContext context;
	private static DxresearchDao dao;
	private static Dxresearch problem;
	private static ProblemsModel problemsModel;

	private static Dxresearch nullProblem;
	private static ProblemsModel nullproblemsModel;

	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);
		context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		dao = context.getBean(DxresearchDao.class);
		problem = dao.getDxResearchItemsByPatient(Constants.Runtime.VALID_DEMOGRAPHIC).get(0);
		problemsModel = new ProblemsModel(problem);

		nullProblem = new Dxresearch();
		nullproblemsModel = new ProblemsModel(nullProblem);
	}

	@Test
	public void problemsModelNullTest() {
		assertNotNull(new ProblemsModel(null));
	}

	@Test
	public void textSummaryTest() {
		String text = problemsModel.getTextSummary();
		assertNotNull(text);
	}

	@Test
	public void textSummaryNullTest() {
		String text = nullproblemsModel.getTextSummary();
		assertNotNull(text);
	}

	@Test
	public void idTest() {
		SET<II> ids = problemsModel.getIds();
		assertNotNull(ids);

		II id = ids.get(0);
		assertNotNull(id);
		assertEquals(Constants.EMR.EMR_OID, id.getRoot());
		assertEquals(Constants.EMR.EMR_VERSION, id.getAssigningAuthorityName());
		assertFalse(EverestUtils.isNullorEmptyorWhitespace(id.getExtension()));
		assertTrue(id.getExtension().contains(problem.getDxresearchNo().toString()));
	}

	@Test
	public void idNullTest() {
		SET<II> ids = nullproblemsModel.getIds();
		assertNotNull(ids);
	}

	@Test
	public void codeTest() {
		CD<String> code = problemsModel.getCode();
		assertNotNull(code);

		assertEquals(BodyConstants.Problems.SNOMED_CT_DIAGNOSIS_CODE, code.getCode());
		assertEquals(Constants.CodeSystems.SNOMED_CT_OID, code.getCodeSystem());
		assertEquals(Constants.CodeSystems.SNOMED_CT_NAME, code.getCodeSystemName());
		assertEquals(BodyConstants.Problems.SNOMED_CT_DIAGNOSIS_NAME, code.getDisplayName());
	}

	@Test
	public void codeNullTest() {
		CD<String> code = nullproblemsModel.getCode();
		assertNotNull(code);

		assertEquals(BodyConstants.Problems.SNOMED_CT_DIAGNOSIS_CODE, code.getCode());
		assertEquals(Constants.CodeSystems.SNOMED_CT_OID, code.getCodeSystem());
		assertEquals(Constants.CodeSystems.SNOMED_CT_NAME, code.getCodeSystemName());
		assertEquals(BodyConstants.Problems.SNOMED_CT_DIAGNOSIS_NAME, code.getDisplayName());
	}

	@Test
	public void statusCodeActiveTest() {
		ActStatus status = problemsModel.getStatusCode();
		assertNotNull(status);
		assertEquals(ActStatus.Active, status);
	}

	@Test
	public void statusCodeCompleteTest() {
		Dxresearch problem2 = dao.getDxResearchItemsByPatient(Constants.Runtime.VALID_DEMOGRAPHIC).get(0);
		problem2.setStatus('C');
		ProblemsModel problemsModel2 = new ProblemsModel(problem2);

		ActStatus status = problemsModel2.getStatusCode();
		assertNotNull(status);
		assertEquals(ActStatus.Completed, status);
	}

	@Test
	public void statusCodeNullTest() {
		ActStatus status = nullproblemsModel.getStatusCode();
		assertNotNull(status);
		assertEquals(ActStatus.Completed, status);
	}
}
