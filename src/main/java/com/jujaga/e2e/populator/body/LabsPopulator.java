package com.jujaga.e2e.populator.body;

import java.util.List;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalStatement;

import com.jujaga.e2e.constant.BodyConstants.Labs;
import com.jujaga.emr.PatientExport;
import com.jujaga.emr.PatientExport.Lab;

public class LabsPopulator extends AbstractBodyPopulator<Lab> {
	LabsPopulator(PatientExport patientExport) {
		bodyConstants = Labs.getConstants();
		patientExport.isLoaded();
		populateClinicalStatement(patientExport.getLabs());
	}

	@Override
	public ClinicalStatement populateClinicalStatement(List<Lab> list) {
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
