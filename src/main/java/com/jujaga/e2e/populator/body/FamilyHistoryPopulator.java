package com.jujaga.e2e.populator.body;

import java.util.List;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalStatement;

import com.jujaga.e2e.constant.BodyConstants.FamilyHistory;
import com.jujaga.emr.PatientExport;
import com.jujaga.emr.model.CaseManagementNote;

public class FamilyHistoryPopulator extends AbstractBodyPopulator<CaseManagementNote> {
	private List<CaseManagementNote> familyHistory = null;

	FamilyHistoryPopulator(PatientExport patientExport) {
		bodyConstants = FamilyHistory.getConstants();
		if(patientExport.isLoaded()) {
			familyHistory = patientExport.getFamilyHistory();
		}
	}

	@Override
	public void populate() {
		populateClinicalStatement(familyHistory);
		super.populate();
	}

	@Override
	public ClinicalStatement populateClinicalStatement(List<CaseManagementNote> list) {
		return null;
	}

	@Override
	public ClinicalStatement populateNullFlavorClinicalStatement() {
		return null;
	}

	@Override
	public List<String> populateText() {
		return null;
	}
}
