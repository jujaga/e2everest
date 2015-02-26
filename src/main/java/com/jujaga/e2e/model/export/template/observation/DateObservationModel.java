package com.jujaga.e2e.model.export.template.observation;

import java.util.Date;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActMoodDocumentObservation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntryRelationship;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.model.export.body.BodyUtils;

public class DateObservationModel extends AbstractObservationModel {
	public EntryRelationship getEntryRelationship(Date date) {
		if(date != null) {
			entryRelationship.setTypeCode(x_ActRelationshipEntryRelationship.SUBJ);
			entryRelationship.setContextConductionInd(true);

			observation.setMoodCode(x_ActMoodDocumentObservation.Eventoccurrence);
			observation.getCode().setCodeEx(Constants.ObservationType.DATEOBS.toString());
			observation.setEffectiveTime(BodyUtils.buildTSFromDate(date), null);
		} else {
			entryRelationship = null;
		}

		return entryRelationship;
	}
}
