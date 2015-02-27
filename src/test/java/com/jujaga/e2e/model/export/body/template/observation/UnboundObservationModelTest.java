package com.jujaga.e2e.model.export.body.template.observation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Observation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActMoodDocumentObservation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntryRelationship;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.model.export.template.observation.UnboundObservationModel;

public class UnboundObservationModelTest {
	@Test
	public void validUnboundObservationTest() {
		String validString = "test";

		EntryRelationship entryRelationship = new UnboundObservationModel().getEntryRelationship(validString);
		assertNotNull(entryRelationship);
		assertEquals(x_ActRelationshipEntryRelationship.SUBJ, entryRelationship.getTypeCode().getCode());
		assertTrue(entryRelationship.getContextConductionInd().toBoolean());

		Observation observation = entryRelationship.getClinicalStatementIfObservation();
		assertNotNull(observation);
		assertEquals(x_ActMoodDocumentObservation.Eventoccurrence, observation.getMoodCode().getCode());
		assertEquals(validString, new String(observation.getText().getData()));

		CD<String> code = observation.getCode();
		assertNotNull(code);
		assertEquals(Constants.CodeSystems.OBSERVATIONTYPE_CA_PENDING_OID, code.getCodeSystem());
		assertEquals(Constants.CodeSystems.OBSERVATIONTYPE_CA_PENDING_DISPLAY_NAME, code.getCodeSystemName());
		assertEquals(Constants.ObservationType.UNBOUND.toString(), code.getCode());
	}

	@Test
	public void nullFlavorUnboundObservationTest() {
		EntryRelationship entryRelationship = new UnboundObservationModel().getEntryRelationship(null);
		assertNotNull(entryRelationship);
		assertEquals(x_ActRelationshipEntryRelationship.SUBJ, entryRelationship.getTypeCode().getCode());
		assertTrue(entryRelationship.getContextConductionInd().toBoolean());

		Observation observation = entryRelationship.getClinicalStatementIfObservation();
		assertNotNull(observation);
		assertEquals(x_ActMoodDocumentObservation.Eventoccurrence, observation.getMoodCode().getCode());
		assertTrue(observation.getText().isNull());
		assertEquals(NullFlavor.NoInformation, observation.getText().getNullFlavor().getCode());

		CD<String> code = observation.getCode();
		assertNotNull(code);
		assertEquals(Constants.CodeSystems.OBSERVATIONTYPE_CA_PENDING_OID, code.getCodeSystem());
		assertEquals(Constants.CodeSystems.OBSERVATIONTYPE_CA_PENDING_DISPLAY_NAME, code.getCodeSystemName());
		assertEquals(Constants.ObservationType.UNBOUND.toString(), code.getCode());
	}
}
