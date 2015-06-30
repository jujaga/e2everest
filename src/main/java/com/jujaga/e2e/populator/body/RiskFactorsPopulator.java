package com.jujaga.e2e.populator.body;

import java.util.List;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalStatement;

import com.jujaga.e2e.constant.BodyConstants.RiskFactors;
import com.jujaga.emr.PatientExport;
import com.jujaga.emr.model.CaseManagementNote;

public class RiskFactorsPopulator extends AbstractBodyPopulator<CaseManagementNote> {
	private List<CaseManagementNote> riskFactors = null;

	RiskFactorsPopulator(PatientExport patientExport) {
		bodyConstants = RiskFactors.getConstants();
		if(patientExport.isLoaded()) {
			riskFactors = patientExport.getRiskFactors();
		}
	}

	@Override
	public void populate() {
		populateClinicalStatement(riskFactors);
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
