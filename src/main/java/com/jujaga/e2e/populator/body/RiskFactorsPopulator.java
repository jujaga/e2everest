package com.jujaga.e2e.populator.body;

import java.util.Arrays;
import java.util.List;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalStatement;

import com.jujaga.e2e.constant.BodyConstants.RiskFactors;
import com.jujaga.emr.PatientExport;

public class RiskFactorsPopulator extends AbstractBodyPopulator<RiskFactorsPopulator> {
	RiskFactorsPopulator(PatientExport patientExport) {
		bodyConstants = RiskFactors.getConstants();
		if(patientExport.isLoaded()) {
			populateClinicalStatement(Arrays.asList(this));
		}
	}

	@Override
	public ClinicalStatement populateClinicalStatement(List<RiskFactorsPopulator> list) {
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
