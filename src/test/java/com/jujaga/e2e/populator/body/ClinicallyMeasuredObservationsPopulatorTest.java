package com.jujaga.e2e.populator.body;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalStatement;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Observation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActMoodDocumentObservation;

import com.jujaga.e2e.constant.BodyConstants.ClinicallyMeasuredObservations;

public class ClinicallyMeasuredObservationsPopulatorTest extends AbstractBodyPopulatorTest {
	@BeforeClass
	public static void beforeClass() {
		setupClass(ClinicallyMeasuredObservations.getConstants());
	}

	@Ignore
	@Test
	public void clinicallyMeasuredObservationsComponentSectionTest() {
		componentSectionTest();
	}

	@Ignore
	@Test
	public void clinicallyMeasuredObservationsEntryCountTest() {
		entryCountTest(0);
	}

	@Ignore
	@Test
	public void clinicallyMeasuredObservationsEntryStructureTest() {
		entryStructureTest();
	}

	@Ignore
	@Test
	public void clinicallyMeasuredObservationsClinicalStatementTest() {
		ClinicalStatement clinicalStatement = component.getSection().getEntry().get(0).getClinicalStatement();
		assertNotNull(clinicalStatement);
		assertTrue(clinicalStatement.isPOCD_MT000040UVObservation());

		Observation observation = (Observation) clinicalStatement;
		assertEquals(x_ActMoodDocumentObservation.Eventoccurrence, observation.getMoodCode().getCode());
		assertNotNull(observation.getId());
		assertNotNull(observation.getCode());
		assertNotNull(observation.getStatusCode());
	}
}
