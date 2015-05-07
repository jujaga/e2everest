package com.jujaga.e2e.model.export.template.observation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;
import org.marc.everest.datatypes.ED;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Observation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActMoodDocumentObservation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntryRelationship;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.util.EverestUtils;

// TODO Complete tests
public class CommentObservationModelTest {
	@Test
	public void validCommentObservationTest() {
		String test = "test";
		EntryRelationship entryRelationship = commentObservationStructureTestHelper(test, null, null);
		assertNotNull(entryRelationship);

		Observation observation = entryRelationship.getClinicalStatementIfObservation();
		assertNotNull(observation);
		assertEquals(x_ActMoodDocumentObservation.Eventoccurrence, observation.getMoodCode().getCode());

		CD<String> code = observation.getCode();
		assertNotNull(code);
		assertEquals(Constants.CodeSystems.OBSERVATIONTYPE_CA_PENDING_OID, code.getCodeSystem());
		assertEquals(Constants.CodeSystems.OBSERVATIONTYPE_CA_PENDING_NAME, code.getCodeSystemName());
		assertEquals(Constants.ObservationType.COMMENT.toString(), code.getCode());

		ED text = observation.getText();
		assertNotNull(text);

		assertEquals(test, new String(text.getData()));
	}

	@Test
	public void nullFlavorCommentObservationTest() {
		EntryRelationship entryRelationship = commentObservationStructureTestHelper(null, null, null);
		assertNull(entryRelationship);
	}

	private EntryRelationship commentObservationStructureTestHelper(String comment, Date time, String author) {
		EntryRelationship entryRelationship = new CommentObservationModel().getEntryRelationship(comment, time, author);
		if(!EverestUtils.isNullorEmptyorWhitespace(comment)) {
			assertNotNull(entryRelationship);
			assertEquals(x_ActRelationshipEntryRelationship.SUBJ, entryRelationship.getTypeCode().getCode());
			assertTrue(entryRelationship.getContextConductionInd().toBoolean());
			assertEquals(Constants.ObservationOids.COMMENT_OBSERVATION_TEMPLATE_ID, entryRelationship.getTemplateId().get(0).getRoot());
		}

		return entryRelationship;
	}

	/*private Observation commonCommentObservationHelper(String comment, Date time, String author) {
		EntryRelationship entryRelationship = new CommentObservationModel().getEntryRelationship(comment, time, author);
		assertNotNull(entryRelationship);
		assertEquals(x_ActRelationshipEntryRelationship.SUBJ, entryRelationship.getTypeCode().getCode());
		assertTrue(entryRelationship.getContextConductionInd().toBoolean());
		assertEquals(Constants.ObservationOids.COMMENT_OBSERVATION_TEMPLATE_ID, entryRelationship.getTemplateId().get(0).getRoot());

		Observation observation = entryRelationship.getClinicalStatementIfObservation();
		if(!EverestUtils.isNullorEmptyorWhitespace(comment)) {
			assertNotNull(observation);
			assertEquals(x_ActMoodDocumentObservation.Eventoccurrence, observation.getMoodCode().getCode());

			CD<String> code = observation.getCode();
			assertNotNull(code);
			assertEquals(Constants.CodeSystems.OBSERVATIONTYPE_CA_PENDING_OID, code.getCodeSystem());
			assertEquals(Constants.CodeSystems.OBSERVATIONTYPE_CA_PENDING_NAME, code.getCodeSystemName());
			assertEquals(Constants.ObservationType.COMMENT.toString(), code.getCode());

			ED text = observation.getText();
			assertNotNull(text);
		}
		return observation;
	}*/
}
