package com.jujaga.e2e.populator.body;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.marc.everest.datatypes.BL;
import org.marc.everest.datatypes.II;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalStatement;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Entry;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.SubstanceAdministration;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntry;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_DocumentSubstanceMood;

import com.jujaga.e2e.constant.BodyConstants.Immunizations;
import com.jujaga.e2e.model.export.body.ImmunizationsModel;
import com.jujaga.emr.PatientExport;
import com.jujaga.emr.model.Prevention;

public class ImmunizationsPopulator extends AbstractBodyPopulator<Prevention> {
	private List<Prevention> preventions = null;

	ImmunizationsPopulator(PatientExport patientExport) {
		bodyConstants = Immunizations.getConstants();
		if(patientExport.isLoaded()) {
			preventions = patientExport.getImmunizations();
		}
	}

	@Override
	public void populate() {
		if(preventions != null) {
			for(Prevention prevention : preventions) {
				Entry entry = new Entry(x_ActRelationshipEntry.DRIV, new BL(true));
				entry.setTemplateId(Arrays.asList(new II(bodyConstants.ENTRY_TEMPLATE_ID)));
				entry.setClinicalStatement(populateClinicalStatement(Arrays.asList(prevention)));
				entries.add(entry);
			}
		}

		super.populate();
	}

	@Override
	public ClinicalStatement populateClinicalStatement(List<Prevention> list) {
		ImmunizationsModel immunizationsModel = new ImmunizationsModel(list.get(0));
		SubstanceAdministration substanceAdministration = new SubstanceAdministration(x_DocumentSubstanceMood.Eventoccurrence);
		ArrayList<EntryRelationship> entryRelationships = new ArrayList<EntryRelationship>();

		substanceAdministration.setNegationInd(immunizationsModel.getNegationInd());
		substanceAdministration.setId(immunizationsModel.getIds());
		substanceAdministration.setCode(immunizationsModel.getCode());
		substanceAdministration.setConsumable(immunizationsModel.getConsumable());

		substanceAdministration.setEntryRelationship(entryRelationships);
		return substanceAdministration;
	}

	@Override
	public ClinicalStatement populateNullFlavorClinicalStatement() {
		return null;
	}

	@Override
	public List<String> populateText() {
		List<String> list = new ArrayList<String>();
		if(preventions != null) {
			for(Prevention prevention : preventions) {
				ImmunizationsModel immunizationsModel = new ImmunizationsModel(prevention);
				list.add(immunizationsModel.getTextSummary());
			}
		}

		return list;
	}
}
