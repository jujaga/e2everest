package com.jujaga.e2e.populator.body;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalStatement;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Entry;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.SubstanceAdministration;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_DocumentSubstanceMood;

import com.jujaga.emr.PatientExport;

public class MedicationsPopulator extends AbstractBodyPopulator {
	public MedicationsPopulator(PatientExport patientExport) {
		patientExport.isLoaded();
		// TODO Figure out way of mapping many drugs to one object
	}

	@Override
	public void populate() {
		super.populate();

		Entry entry = new Entry();
		entry.setClinicalStatement(populateClinicalStatement());
		//entries.add(entry);
	}

	@Override
	public ClinicalStatement populateClinicalStatement() {
		SubstanceAdministration substanceAdministration = new SubstanceAdministration();
		substanceAdministration.setMoodCode(x_DocumentSubstanceMood.Eventoccurrence);

		return substanceAdministration;
	}

	@Override
	public ClinicalStatement populateNullFlavorClinicalStatement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String populateText() {
		// TODO Auto-generated method stub
		return null;
	}
}
