package com.jujaga.e2e.model.export.template.observation;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.marc.everest.datatypes.TS;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActMoodDocumentObservation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntryRelationship;

import com.jujaga.e2e.constant.Constants;

public class DateObservationModel extends AbstractObservationModel {
	public EntryRelationship getEntryRelationship(Date date) {
		entryRelationship.setTypeCode(x_ActRelationshipEntryRelationship.HasComponent);
		entryRelationship.setContextConductionInd(true);

		observation.setMoodCode(x_ActMoodDocumentObservation.Eventoccurrence);
		observation.getCode().setCodeEx(Constants.ObservationType.DATEOBS.toString());
		if(date != null) {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			observation.setEffectiveTime(new TS(calendar, TS.DAY), null);
		}

		return entryRelationship;
	}
}
