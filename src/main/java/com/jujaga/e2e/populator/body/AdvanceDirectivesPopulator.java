package com.jujaga.e2e.populator.body;

import java.util.List;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalStatement;

import com.jujaga.e2e.constant.BodyConstants.AdvanceDirectives;
import com.jujaga.emr.PatientExport;

public class AdvanceDirectivesPopulator extends AbstractBodyPopulator<AdvanceDirectivesPopulator> {
	AdvanceDirectivesPopulator(PatientExport patientExport) {
		bodyConstants = AdvanceDirectives.getConstants();
	}

	@Override
	public ClinicalStatement populateClinicalStatement(List<AdvanceDirectivesPopulator> list) {
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
