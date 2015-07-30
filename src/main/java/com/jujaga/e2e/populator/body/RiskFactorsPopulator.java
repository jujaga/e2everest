package com.jujaga.e2e.populator.body;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.marc.everest.datatypes.BL;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.generic.CS;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalStatement;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Entry;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Organizer;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ActStatus;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActClassDocumentEntryOrganizer;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntry;

import com.jujaga.e2e.constant.BodyConstants.RiskFactors;
import com.jujaga.e2e.model.export.body.RiskFactorsModel;
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
		if(riskFactors != null) {
			for(CaseManagementNote riskFactor : riskFactors) {
				Entry entry = new Entry(x_ActRelationshipEntry.DRIV, new BL(true));
				entry.setTemplateId(Arrays.asList(new II(bodyConstants.ENTRY_TEMPLATE_ID)));
				entry.setClinicalStatement(populateClinicalStatement(Arrays.asList(riskFactor)));
				entries.add(entry);
			}
		}

		super.populate();
	}

	@Override
	public ClinicalStatement populateClinicalStatement(List<CaseManagementNote> list) {
		RiskFactorsModel riskFactorsModel = new RiskFactorsModel(list.get(0));
		Organizer organizer = new Organizer(x_ActClassDocumentEntryOrganizer.CLUSTER);

		organizer.setId(riskFactorsModel.getIds());
		organizer.setCode(riskFactorsModel.getCode());
		organizer.setStatusCode(riskFactorsModel.getStatusCode());
		organizer.setAuthor(riskFactorsModel.getAuthor());
		organizer.setComponent(riskFactorsModel.getComponentObservation());

		return organizer;
	}

	@Override
	public ClinicalStatement populateNullFlavorClinicalStatement() {
		RiskFactorsModel riskFactorsModel = new RiskFactorsModel(null);
		Organizer organizer = new Organizer(x_ActClassDocumentEntryOrganizer.CLUSTER);

		organizer.setId(riskFactorsModel.getIds());
		organizer.setCode(riskFactorsModel.getCode());
		organizer.setStatusCode(new CS<ActStatus>() {{setNullFlavor(NullFlavor.NoInformation);}});
		organizer.setComponent(riskFactorsModel.getComponentObservation());

		return organizer;
	}

	@Override
	public List<String> populateText() {
		List<String> list = new ArrayList<String>();
		if(riskFactors != null) {
			for(CaseManagementNote riskFactor : riskFactors) {
				RiskFactorsModel riskFactorsModel = new RiskFactorsModel(riskFactor);
				list.add(riskFactorsModel.getTextSummary());
			}
		}

		return list;
	}
}
