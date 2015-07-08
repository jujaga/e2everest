package com.jujaga.e2e.populator.body;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalStatement;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Observation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ActClassObservation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActMoodDocumentObservation;

import com.jujaga.e2e.constant.BodyConstants.Alerts;

public class AlertsPopulatorTest extends AbstractBodyPopulatorTest {
	@BeforeClass
	public static void beforeClass() {
		setupClass(Alerts.getConstants());
	}

	@Test
	public void alertsComponentSectionTest() {
		componentSectionTest();
	}

	@Test
	public void alertsEntryCountTest() {
		entryCountTest(1);
	}

	@Test
	public void alertsEntryStructureTest() {
		entryStructureTest();
	}

	@Test
	public void alertsClinicalStatementTest() {
		ClinicalStatement clinicalStatement = component.getSection().getEntry().get(0).getClinicalStatement();
		assertNotNull(clinicalStatement);
		assertTrue(clinicalStatement.isPOCD_MT000040UVObservation());

		Observation observation = (Observation) clinicalStatement;
		assertEquals(ActClassObservation.OBS, observation.getClassCode().getCode());
		assertEquals(x_ActMoodDocumentObservation.Eventoccurrence, observation.getMoodCode().getCode());
		assertNotNull(observation.getId());
		assertNotNull(observation.getCode());
	}
}
