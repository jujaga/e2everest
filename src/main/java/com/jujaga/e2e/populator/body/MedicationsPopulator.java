package com.jujaga.e2e.populator.body;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalStatement;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Entry;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.SubstanceAdministration;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_DocumentSubstanceMood;

import com.jujaga.e2e.model.export.body.MedicationsModel;
import com.jujaga.emr.PatientExport;

public class MedicationsPopulator extends AbstractBodyPopulator {
	private final MedicationsModel medicationsModel;

	MedicationsPopulator(PatientExport patientExport) {
		patientExport.isLoaded();
		medicationsModel = new MedicationsModel();
		// TODO Figure out way of mapping many drugs to one object
	}

	@Override
	public void populate() {
		Entry entry = new Entry();
		entry.setClinicalStatement(populateClinicalStatement());
		entries.add(entry);

		super.populate();
	}

	@Override
	public ClinicalStatement populateClinicalStatement() {
		SubstanceAdministration substanceAdministration = new SubstanceAdministration();
		substanceAdministration.setMoodCode(x_DocumentSubstanceMood.Eventoccurrence);
		substanceAdministration.setId(medicationsModel.getIds());

		return substanceAdministration;
	}

	@Override
	public ClinicalStatement populateNullFlavorClinicalStatement() {
		return null;
	}

	@Override
	public String populateText() {
		return null;
	}
}
