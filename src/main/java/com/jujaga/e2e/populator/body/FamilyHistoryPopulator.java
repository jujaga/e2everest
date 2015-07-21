package com.jujaga.e2e.populator.body;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.marc.everest.datatypes.BL;
import org.marc.everest.datatypes.II;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalStatement;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Entry;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Observation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActMoodDocumentObservation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntry;

import com.jujaga.e2e.constant.BodyConstants.FamilyHistory;
import com.jujaga.e2e.model.export.body.FamilyHistoryModel;
import com.jujaga.emr.PatientExport;
import com.jujaga.emr.PatientExport.FamilyHistoryEntry;

public class FamilyHistoryPopulator extends AbstractBodyPopulator<FamilyHistoryEntry> {
	private List<FamilyHistoryEntry> familyHistory = null;

	FamilyHistoryPopulator(PatientExport patientExport) {
		bodyConstants = FamilyHistory.getConstants();
		if(patientExport.isLoaded()) {
			familyHistory = patientExport.getFamilyHistory();
		}
	}

	@Override
	public void populate() {
		if(familyHistory != null) {
			for(FamilyHistoryEntry familyEntry : familyHistory) {
				Entry entry = new Entry(x_ActRelationshipEntry.DRIV, new BL(true));
				entry.setTemplateId(Arrays.asList(new II(bodyConstants.ENTRY_TEMPLATE_ID)));
				entry.setClinicalStatement(populateClinicalStatement(Arrays.asList(familyEntry)));
				entries.add(entry);
			}
		}

		super.populate();
	}

	@Override
	public ClinicalStatement populateClinicalStatement(List<FamilyHistoryEntry> list) {
		FamilyHistoryModel familyHistoryModel = new FamilyHistoryModel(list.get(0));
		Observation observation = new Observation(x_ActMoodDocumentObservation.Eventoccurrence);

		observation.setId(familyHistoryModel.getIds());
		observation.setCode(familyHistoryModel.getCode());
		observation.setText(familyHistoryModel.getText());
		observation.setEffectiveTime(familyHistoryModel.getEffectiveTime());
		observation.setValue(familyHistoryModel.getValue());

		return observation;
	}

	@Override
	public ClinicalStatement populateNullFlavorClinicalStatement() {
		return null;
	}

	@Override
	public List<String> populateText() {
		List<String> list = new ArrayList<String>();
		if(familyHistory != null) {
			for(FamilyHistoryEntry familyEntry : familyHistory) {
				FamilyHistoryModel familyHistoryModel = new FamilyHistoryModel(familyEntry);
				list.add(familyHistoryModel.getTextSummary());
			}
		}

		return list;
	}
}
