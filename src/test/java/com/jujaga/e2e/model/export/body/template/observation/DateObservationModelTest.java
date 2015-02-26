package com.jujaga.e2e.model.export.body.template.observation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Observation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActMoodDocumentObservation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntryRelationship;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.model.export.body.BodyUtils;
import com.jujaga.e2e.model.export.template.observation.DateObservationModel;

public class DateObservationModelTest {
	@Test
	public void validDateObservationTest() {
		Date date = new Date();

		EntryRelationship entryRelationship = new DateObservationModel().getEntryRelationship(date);
		assertNotNull(entryRelationship);
		assertEquals(x_ActRelationshipEntryRelationship.SUBJ, entryRelationship.getTypeCode().getCode());
		assertTrue(entryRelationship.getContextConductionInd().toBoolean());

		Observation observation = entryRelationship.getClinicalStatementIfObservation();
		assertNotNull(observation);
		assertEquals(x_ActMoodDocumentObservation.Eventoccurrence, observation.getMoodCode().getCode());
		assertEquals(BodyUtils.buildTSFromDate(date), observation.getEffectiveTime().getLow());

		CD<String> code = observation.getCode();
		assertNotNull(code);
		assertEquals(Constants.CodeSystems.OBSERVATIONTYPE_CA_PENDING_OID, code.getCodeSystem());
		assertEquals(Constants.CodeSystems.OBSERVATIONTYPE_CA_PENDING_DISPLAY_NAME, code.getCodeSystemName());
		assertEquals(Constants.ObservationType.DATEOBS.toString(), code.getCode());
	}

	@Test
	public void nullFlavorDateObservationTest() {
		EntryRelationship entryRelationship = new DateObservationModel().getEntryRelationship(null);
		assertNull(entryRelationship);
	}
}
