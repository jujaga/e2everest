package com.jujaga.e2e.populator.body;

import java.util.Collections;
import java.util.List;

import org.marc.everest.datatypes.BL;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalStatement;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Entry;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.SubstanceAdministration;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntry;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_DocumentSubstanceMood;

import com.jujaga.e2e.model.export.body.MedicationsModel;
import com.jujaga.e2e.util.EverestUtils;
import com.jujaga.emr.PatientExport;
import com.jujaga.emr.model.Drug;

public class MedicationsPopulator extends AbstractBodyPopulator {
	private final MedicationsModel medicationsModel;
	private List<Drug> drugs;

	MedicationsPopulator(PatientExport patientExport) {
		drugs = patientExport.getMedications();
		Collections.reverse(drugs);
		Collections.sort(drugs, new EverestUtils.SortByDin());

		medicationsModel = new MedicationsModel();
	}

	@SuppressWarnings("unused") // Temporary
	@Override
	public void populate() {
		// TODO Figure out way of mapping many drugs to one object
		// Consider subset lists?
		for(Drug drug : drugs) {
			Entry entry = new Entry(x_ActRelationshipEntry.DRIV, new BL(true), populateClinicalStatement());
			entries.add(entry);
		}

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
