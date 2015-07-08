package com.jujaga.e2e.populator.body;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.marc.everest.datatypes.BL;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalStatement;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Entry;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Observation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActMoodDocumentObservation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntry;

import com.jujaga.e2e.constant.BodyConstants.Alerts;
import com.jujaga.e2e.model.export.body.AlertsModel;
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
		if(alerts != null) {
			for(CaseManagementNote alert : alerts) {
				Entry entry = new Entry(x_ActRelationshipEntry.DRIV, new BL(true));
				entry.setTemplateId(Arrays.asList(new II(bodyConstants.ENTRY_TEMPLATE_ID)));
				entry.setClinicalStatement(populateClinicalStatement(Arrays.asList(alert)));
				entries.add(entry);
			}
		}

		super.populate();
	}

	@Override
	public ClinicalStatement populateClinicalStatement(List<CaseManagementNote> list) {
		AlertsModel alertsModel = new AlertsModel(list.get(0));
		Observation observation = new Observation(x_ActMoodDocumentObservation.Eventoccurrence);

		observation.setId(alertsModel.getIds());
		observation.setCode(new CD<String>());

		return observation;
	}

	@Override
	public ClinicalStatement populateNullFlavorClinicalStatement() {
		return null;
	}

	@Override
	public List<String> populateText() {
		List<String> list = new ArrayList<String>();
		if(alerts != null) {
			for(CaseManagementNote alert : alerts) {
				AlertsModel alertsModel = new AlertsModel(alert);
				list.add(alertsModel.getTextSummary());
			}
		}

		return list;
	}
}
