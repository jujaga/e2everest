package com.jujaga.e2e.model.export.body;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.TS;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.datatypes.generic.IVL;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Observation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ActClassObservation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ActStatus;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActMoodDocumentObservation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntryRelationship;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.util.EverestUtils;
import com.jujaga.emr.dao.AllergyDao;
import com.jujaga.emr.model.Allergy;

public class AllergiesModelTest {
	private static ApplicationContext context;
	private static AllergyDao dao;
	private static Allergy allergy;
	private static AllergiesModel allergiesModel;

	private static Allergy nullAllergy;
	private static AllergiesModel nullAllergiesModel;

	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);
		context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		dao = context.getBean(AllergyDao.class);
	}

	@Before
	public void before() {
		allergy = dao.findAllergies(Constants.Runtime.VALID_DEMOGRAPHIC).get(0);
		allergiesModel = new AllergiesModel(allergy);

		nullAllergy = new Allergy();
		nullAllergiesModel = new AllergiesModel(nullAllergy);
	}

	@Test
	public void allergiesModelNullTest() {
		assertNotNull(new AllergiesModel(null));
	}

	@Test
	public void textSummaryTest() {
		String text = allergiesModel.getTextSummary();
		assertNotNull(text);
	}

	@Test
	public void textSummaryNullTest() {
		String text = nullAllergiesModel.getTextSummary();
		assertNotNull(text);
	}

	@Test
	public void idTest() {
		SET<II> ids = allergiesModel.getIds();
		assertNotNull(ids);

		II id = ids.get(0);
		assertNotNull(id);
		assertEquals(Constants.EMR.EMR_OID, id.getRoot());
		assertEquals(Constants.EMR.EMR_VERSION, id.getAssigningAuthorityName());
		assertFalse(EverestUtils.isNullorEmptyorWhitespace(id.getExtension()));
		assertTrue(id.getExtension().contains(Constants.IdPrefixes.Allergies.toString()));
		assertTrue(id.getExtension().contains(allergy.getId().toString()));
	}

	@Test
	public void idNullTest() {
		SET<II> ids = nullAllergiesModel.getIds();
		assertNotNull(ids);
	}

	@Test
	public void codeTest() {
		CD<String> code = allergiesModel.getCode();
		assertNotNull(code);
		assertEquals("48765-2", code.getCode());
		assertEquals(Constants.CodeSystems.LOINC_OID, code.getCodeSystem());
		assertEquals(Constants.CodeSystems.LOINC_NAME, code.getCodeSystemName());
		assertEquals(Constants.CodeSystems.LOINC_VERSION, code.getCodeSystemVersion());
	}

	@Test
	public void codeNullTest() {
		CD<String> code = nullAllergiesModel.getCode();
		assertNotNull(code);
		assertEquals("48765-2", code.getCode());
		assertEquals(Constants.CodeSystems.LOINC_OID, code.getCodeSystem());
		assertEquals(Constants.CodeSystems.LOINC_NAME, code.getCodeSystemName());
		assertEquals(Constants.CodeSystems.LOINC_VERSION, code.getCodeSystemVersion());
	}

	@Test
	public void statusCodeActiveTest() {
		ActStatus status = allergiesModel.getStatusCode();
		assertNotNull(status);
		assertEquals(ActStatus.Active, status);
	}

	@Test
	public void statusCodeCompleteTest() {
		allergy.setArchived(true);
		allergiesModel = new AllergiesModel(allergy);

		ActStatus status = allergiesModel.getStatusCode();
		assertNotNull(status);
		assertEquals(ActStatus.Completed, status);
	}

	@Test
	public void statusCodeNullTest() {
		ActStatus status = nullAllergiesModel.getStatusCode();
		assertNotNull(status);
		assertEquals(ActStatus.Active, status);
	}

	@Test
	public void effectiveTimeTest() {
		IVL<TS> ivl = allergiesModel.getEffectiveTime();
		assertNotNull(ivl);
		assertEquals(EverestUtils.buildTSFromDate(allergy.getEntryDate()), ivl.getLow());
	}

	@Test
	public void effectiveTimeNullTest() {
		IVL<TS> ivl = nullAllergiesModel.getEffectiveTime();
		assertNotNull(ivl);
		assertTrue(ivl.isNull());
		assertEquals(NullFlavor.Unknown, ivl.getNullFlavor().getCode());
	}

	@Test
	public void allergyObservationTest() {
		EntryRelationship entryRelationship = allergiesModel.getAllergyObservation();
		assertNotNull(entryRelationship);
		assertTrue(entryRelationship.getContextConductionInd().toBoolean());
		assertEquals(x_ActRelationshipEntryRelationship.HasComponent, entryRelationship.getTypeCode().getCode());

		Observation observation = entryRelationship.getClinicalStatementIfObservation();
		assertNotNull(observation);
		assertEquals(ActClassObservation.OBS, observation.getClassCode().getCode());
		assertEquals(x_ActMoodDocumentObservation.Eventoccurrence, observation.getMoodCode().getCode());
		assertNotNull(observation.getCode());
		assertNotNull(observation.getEffectiveTime());
		//assertNotNull(observation.getParticipant());
		assertNotNull(observation.getEntryRelationship());
	}

	@Test
	public void allergyObservationNullTest() {
		EntryRelationship entryRelationship = nullAllergiesModel.getAllergyObservation();
		assertNotNull(entryRelationship);
		assertTrue(entryRelationship.getContextConductionInd().toBoolean());
		assertEquals(x_ActRelationshipEntryRelationship.HasComponent, entryRelationship.getTypeCode().getCode());

		Observation observation = entryRelationship.getClinicalStatementIfObservation();
		assertNotNull(observation);
		assertEquals(ActClassObservation.OBS, observation.getClassCode().getCode());
		assertEquals(x_ActMoodDocumentObservation.Eventoccurrence, observation.getMoodCode().getCode());
		assertNotNull(observation.getCode());
		assertNotNull(observation.getEffectiveTime());
		//assertNotNull(observation.getParticipant());
		assertNotNull(observation.getEntryRelationship());
	}

	@Test
	public void adverseEventCodeTest() {
		CD<String> code = allergiesModel.getAdverseEventCode();
		assertNotNull(code);
		assertEquals("TBD", code.getCode());
		assertEquals(Constants.CodeSystems.ACT_CODE_CODESYSTEM_OID, code.getCodeSystem());
		assertEquals(Constants.CodeSystems.ACT_CODE_CODESYSTEM_NAME, code.getCodeSystemName());
	}

	@Test
	public void adverseEventCodeNullTest() {
		CD<String> code = nullAllergiesModel.getAdverseEventCode();
		assertNotNull(code);
		assertEquals("TBD", code.getCode());
		assertEquals(Constants.CodeSystems.ACT_CODE_CODESYSTEM_OID, code.getCodeSystem());
		assertEquals(Constants.CodeSystems.ACT_CODE_CODESYSTEM_NAME, code.getCodeSystemName());
	}

	@Test
	public void onsetDateTest() {
		IVL<TS> ivl = allergiesModel.getOnsetDate();
		assertNotNull(ivl);
		assertEquals(EverestUtils.buildTSFromDate(allergy.getStartDate()), ivl.getLow());
	}

	@Test
	public void onsetDateNullTest() {
		IVL<TS> ivl = nullAllergiesModel.getOnsetDate();
		assertNotNull(ivl);
		assertTrue(ivl.isNull());
		assertEquals(NullFlavor.Unknown, ivl.getNullFlavor().getCode());
	}
}
