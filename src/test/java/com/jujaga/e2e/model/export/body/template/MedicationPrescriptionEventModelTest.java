package com.jujaga.e2e.model.export.body.template;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.SubstanceAdministration;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntryRelationship;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_DocumentSubstanceMood;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jujaga.e2e.constant.BodyConstants.Medications;
import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.model.export.body.BodyUtils;
import com.jujaga.e2e.model.export.template.MedicationPrescriptionEventModel;
import com.jujaga.emr.dao.DrugDao;
import com.jujaga.emr.model.Drug;

public class MedicationPrescriptionEventModelTest {
	private static ApplicationContext context;
	private static DrugDao dao;
	private static Drug drug;
	private static MedicationPrescriptionEventModel mpeModel;

	@BeforeClass
	public static void beforeClass() {
		context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		dao = context.getBean(DrugDao.class);
		mpeModel = new MedicationPrescriptionEventModel();
	}

	@Before
	public void before() {
		drug = dao.findByDemographicId(Constants.Runtime.VALID_DEMOGRAPHIC).get(0);
	}

	@Test
	public void medicationPrescriptionEventStructureTest() {
		EntryRelationship entryRelationship = mpeModel.getEntryRelationship(drug);
		assertNotNull(entryRelationship);
		assertEquals(x_ActRelationshipEntryRelationship.HasComponent, entryRelationship.getTypeCode().getCode());
		assertTrue(entryRelationship.getContextConductionInd().toBoolean());
		assertEquals(Medications.MEDICATION_PRESCRIPTION_EVENT_TEMPLATE_ID, entryRelationship.getTemplateId().get(0).getRoot());

		SubstanceAdministration substanceAdministration = entryRelationship.getClinicalStatementIfSubstanceAdministration();
		assertNotNull(substanceAdministration);
		assertEquals(x_DocumentSubstanceMood.RQO, substanceAdministration.getMoodCode().getCode());
		assertEquals(BodyUtils.buildUniqueId(Constants.IdPrefixes.MedicationPrescriptions, drug.getId()), substanceAdministration.getId());

		CD<String> code = substanceAdministration.getCode();
		assertNotNull(code);
		assertEquals(Constants.SubstanceAdministrationType.DRUG.toString(), code.getCode());
		assertEquals(Constants.CodeSystems.ACT_CODE_CODESYSTEM_OID, code.getCodeSystem());
		assertEquals(Constants.CodeSystems.ACT_CODE_CODESYSTEM_NAME, code.getCodeSystemName());
	}

	@SuppressWarnings("unused") // Temporary
	private SubstanceAdministration substanceAdministrationHelper() {
		EntryRelationship entryRelationship = mpeModel.getEntryRelationship(drug);
		return entryRelationship.getClinicalStatementIfSubstanceAdministration();
	}
}
