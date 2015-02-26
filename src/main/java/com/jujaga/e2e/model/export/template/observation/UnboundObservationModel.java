package com.jujaga.e2e.model.export.template.observation;

import org.marc.everest.datatypes.ED;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActMoodDocumentObservation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntryRelationship;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.util.EverestUtils;

public class UnboundObservationModel extends AbstractObservationModel {
	public EntryRelationship getEntryRelationship(String value) {
		entryRelationship.setTypeCode(x_ActRelationshipEntryRelationship.SUBJ);
		entryRelationship.setContextConductionInd(true);

		observation.setMoodCode(x_ActMoodDocumentObservation.Eventoccurrence);
		observation.getCode().setCodeEx(Constants.ObservationType.UNBOUND.toString());

		ED text = new ED();
		if(!EverestUtils.isNullorEmptyorWhitespace(value)) {
			text.setData(value);
		} else {
			text.setNullFlavor(NullFlavor.NoInformation);
		}
		observation.setText(text);

		return entryRelationship;
	}
}
