package com.jujaga.e2e.populator.body;

import java.util.List;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalStatement;

import com.jujaga.e2e.constant.BodyConstants.Alerts;
import com.jujaga.emr.PatientExport;
import com.jujaga.emr.model.CaseManagementNote;

public class AlertsPopulator extends AbstractBodyPopulator<CaseManagementNote> {
	private List<CaseManagementNote> alerts = null;

	AlertsPopulator(PatientExport patientExport) {
		bodyConstants = Alerts.getConstants();
		if(patientExport.isLoaded()) {
			alerts = patientExport.getAlerts();
		}
	}

	@Override
	public void populate() {
		populateClinicalStatement(alerts);
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
