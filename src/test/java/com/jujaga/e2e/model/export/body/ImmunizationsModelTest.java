package com.jujaga.e2e.model.export.body;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.marc.everest.datatypes.BL;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Consumable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.util.EverestUtils;
import com.jujaga.emr.dao.PreventionDao;
import com.jujaga.emr.model.Prevention;

public class ImmunizationsModelTest {
	private static ApplicationContext context;
	private static PreventionDao dao;
	private static Prevention prevention;
	private static ImmunizationsModel immunizationsModel;

	private static Prevention nullPrevention;
	private static ImmunizationsModel nullImmunizationsModel;

	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);
		context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		dao = context.getBean(PreventionDao.class);
	}

	@Before
	public void before() {
		prevention = dao.findNotDeletedByDemographicId(Constants.Runtime.VALID_DEMOGRAPHIC).get(0);
		immunizationsModel = new ImmunizationsModel(prevention);

		nullPrevention = new Prevention();
		nullImmunizationsModel = new ImmunizationsModel(nullPrevention);
	}

	@Test
	public void immunizationsModelNullTest() {
		assertNotNull(new ImmunizationsModel(null));
	}

	@Test
	public void textSummaryTest() {
		String text = immunizationsModel.getTextSummary();
		assertNotNull(text);
	}

	@Test
	public void textSummaryNullTest() {
		String text = nullImmunizationsModel.getTextSummary();
		assertNotNull(text);
	}

	@Test
	public void negationIndTest() {
		BL negationInd = immunizationsModel.getNegationInd();
		assertNotNull(negationInd);
		assertFalse(negationInd.toBoolean());
	}

	@Test
	public void negationIndNullTest() {
		BL negationInd = nullImmunizationsModel.getNegationInd();
		assertNotNull(negationInd);
		assertFalse(negationInd.toBoolean());
	}

	@Ignore
	@Test
	public void idTest() {
		SET<II> ids = immunizationsModel.getIds();
		assertNotNull(ids);

		II id = ids.get(0);
		assertNotNull(id);
		assertEquals(Constants.EMR.EMR_OID, id.getRoot());
		assertEquals(Constants.EMR.EMR_VERSION, id.getAssigningAuthorityName());
		assertFalse(EverestUtils.isNullorEmptyorWhitespace(id.getExtension()));
		assertTrue(id.getExtension().contains(Constants.IdPrefixes.Immunizations.toString()));
		assertTrue(id.getExtension().contains(prevention.getId().toString()));
	}
	@Ignore
	@Test
	public void idNullTest() {
		SET<II> ids = nullImmunizationsModel.getIds();
		assertNotNull(ids);
	}
	@Ignore
	@Test
	public void codeTest() {
		CD<String> code = immunizationsModel.getCode();
		assertNotNull(code);

		assertEquals(Constants.SubstanceAdministrationType.IMMUNIZ.toString(), code.getCode());
		assertEquals(Constants.CodeSystems.ACT_CODE_CODESYSTEM_OID, code.getCodeSystem());
		assertEquals(Constants.CodeSystems.ACT_CODE_CODESYSTEM_NAME, code.getCodeSystemName());
	}
	@Ignore
	@Test
	public void codeNullTest() {
		CD<String> code = nullImmunizationsModel.getCode();
		assertNotNull(code);

		assertEquals(Constants.SubstanceAdministrationType.IMMUNIZ.toString(), code.getCode());
		assertEquals(Constants.CodeSystems.ACT_CODE_CODESYSTEM_OID, code.getCodeSystem());
		assertEquals(Constants.CodeSystems.ACT_CODE_CODESYSTEM_NAME, code.getCodeSystemName());
	}
	@Ignore
	@Test
	public void consumableTest() {
		Consumable consumable = immunizationsModel.getConsumable();
		assertNotNull(consumable);
	}
	@Ignore
	@Test
	public void consumableNullTest() {
		Consumable consumable = nullImmunizationsModel.getConsumable();
		assertNotNull(consumable);
	}
}
