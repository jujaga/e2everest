package com.jujaga.e2e.model.export.template.observation;

import org.marc.everest.datatypes.ED;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActMoodDocumentObservation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntryRelationship;

import com.jujaga.e2e.constant.BodyConstants;
import com.jujaga.e2e.util.EverestUtils;

public class UnboundObservationModel extends AbstractObservationModel {
	public EntryRelationship getEntryRelationship(String value) {
		entryRelationship.setTypeCode(x_ActRelationshipEntryRelationship.HasComponent);
		entryRelationship.setContextConductionInd(true);

		observation.setMoodCode(x_ActMoodDocumentObservation.Eventoccurrence);
		observation.getCode().setCodeEx(BodyConstants.ObservationCodes.UNBOUND_OBSERVATION_CODE);

		if(!EverestUtils.isNullorEmptyorWhitespace(value)) {
			observation.setText(new ED(value));
		}

		return entryRelationship;
	}
}
