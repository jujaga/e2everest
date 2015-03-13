package com.jujaga.e2e.model.export.body.template.observation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.datatypes.ED;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.TS;
import org.marc.everest.datatypes.generic.IVL;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Consumable;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.SubstanceAdministration;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntryRelationship;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_DocumentSubstanceMood;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.model.export.template.observation.DoseObservationModel;
import com.jujaga.emr.dao.DrugDao;
import com.jujaga.emr.model.Drug;

public class DoseObservationModelTest {
	private static ApplicationContext context;
	private static DrugDao dao;
	private static Drug drug;
	private static Drug nullDrug;
	private static DoseObservationModel doseObservationModel;

	@BeforeClass
	public static void beforeClass() {
		context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		dao = context.getBean(DrugDao.class);
		doseObservationModel = new DoseObservationModel();
	}

	@Before
	public void before() {
		drug = dao.findByDemographicId(Constants.Runtime.VALID_DEMOGRAPHIC).get(0);
		nullDrug = new Drug();
	}

	@Test
	public void doseObservationStructureTest() {
		EntryRelationship entryRelationship = doseObservationModel.getEntryRelationship(drug);
		assertNotNull(entryRelationship);
		assertEquals(x_ActRelationshipEntryRelationship.HasComponent, entryRelationship.getTypeCode().getCode());
		assertTrue(entryRelationship.getContextConductionInd().toBoolean());
		assertEquals(Constants.TemplateOids.DOSE_OBSERVATION_TEMPLATE_ID, entryRelationship.getTemplateId().get(0).getRoot());

		SubstanceAdministration substanceAdministration = entryRelationship.getClinicalStatementIfSubstanceAdministration();
		assertNotNull(substanceAdministration);
		assertEquals(x_DocumentSubstanceMood.RQO, substanceAdministration.getMoodCode().getCode());

		Consumable consumable = substanceAdministration.getConsumable();
		assertNotNull(consumable);
	}

	@Test
	public void doseObservationNullTest() {
		EntryRelationship entryRelationship = doseObservationModel.getEntryRelationship(nullDrug);
		assertNotNull(entryRelationship);

		SubstanceAdministration substanceAdministration = entryRelationship.getClinicalStatementIfSubstanceAdministration();
		assertNotNull(substanceAdministration);

		Consumable consumable = substanceAdministration.getConsumable();
		assertNotNull(consumable);
	}

	@Test
	public void doseInstructionsTest() {
		ED text = substanceAdministrationHelper(drug).getText();
		assertNotNull(text);
	}

	@Test
	public void doseInstructionsNullTest() {
		ED text = substanceAdministrationHelper(nullDrug).getText();
		assertNotNull(text);
	}

	@Test
	public void durationTest() {
		IVL<TS> ivl = (IVL<TS>) substanceAdministrationHelper(drug).getEffectiveTime().get(0);
		assertNotNull(ivl);
		assertTrue(IVL.isValidWidthFlavor(ivl));
		assertEquals(drug.getDuration(), ivl.getWidth().getValue().toString());
		assertEquals(drug.getDurUnit(), ivl.getWidth().getUnit());
	}

	@Test
	public void durationInvalidTest() {
		drug.setDuration("test");

		IVL<TS> ivl = (IVL<TS>) substanceAdministrationHelper(drug).getEffectiveTime().get(0);
		assertNotNull(ivl);
		assertTrue(ivl.isNull());
		assertEquals(NullFlavor.Unknown, ivl.getNullFlavor().getCode());
	}

	@Test
	public void durationNullTest() {
		IVL<TS> ivl = (IVL<TS>) substanceAdministrationHelper(nullDrug).getEffectiveTime().get(0);
		assertNotNull(ivl);
		assertTrue(ivl.isNull());
		assertEquals(NullFlavor.Unknown, ivl.getNullFlavor().getCode());
	}

	private SubstanceAdministration substanceAdministrationHelper(Drug drug) {
		EntryRelationship entryRelationship = doseObservationModel.getEntryRelationship(drug);
		return entryRelationship.getClinicalStatementIfSubstanceAdministration();
	}
}
