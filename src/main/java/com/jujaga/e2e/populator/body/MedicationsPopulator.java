package com.jujaga.e2e.populator.body;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.marc.everest.datatypes.BL;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalStatement;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Entry;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.SubstanceAdministration;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntry;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_DocumentSubstanceMood;

import com.jujaga.e2e.constant.BodyConstants.Medications;
import com.jujaga.e2e.model.export.body.MedicationsModel;
import com.jujaga.emr.PatientExport;
import com.jujaga.emr.model.Drug;

public class MedicationsPopulator extends AbstractBodyPopulator implements ISectionPopulator<Drug> {
	private List<Drug> allDrugs;
	private Map<Integer, List<Drug>> mapDrugs;

	MedicationsPopulator(PatientExport patientExport) {
		bodyConstants = Medications.getConstants();
		mapDrugs = new HashMap<Integer, List<Drug>>();
		allDrugs = (List<Drug>) patientExport.getMedications();
		Collections.reverse(allDrugs); // Order recent drugs first

		for(Drug drug : allDrugs) {
			Integer din;
			try {
				din = Integer.parseInt(drug.getRegionalIdentifier());
			} catch (NumberFormatException e) {
				din = Medications.NO_DIN_NUMBER;
			}

			if(mapDrugs.containsKey(din)) {
				mapDrugs.get(din).add(drug);
			} else {
				mapDrugs.put(din, Arrays.asList(drug));
			}
		}
	}

	@Override
	public void populate() {
		for(List<Drug> medication : mapDrugs.values()) {
			Entry entry = new Entry(x_ActRelationshipEntry.DRIV, new BL(true));
			entry.setClinicalStatement(populateClinicalStatement(medication));
			entries.add(entry);
		}

		super.populate();
	}

	@Override
	public ClinicalStatement populateClinicalStatement(List<Drug> list) {
		MedicationsModel medicationsModel = new MedicationsModel(list.get(0));
		SubstanceAdministration substanceAdministration = new SubstanceAdministration();
		substanceAdministration.setMoodCode(x_DocumentSubstanceMood.Eventoccurrence);
		substanceAdministration.setId(medicationsModel.getIds());
		substanceAdministration.setCode(medicationsModel.getCode());
		substanceAdministration.setStatusCode(medicationsModel.getStatusCode());
		substanceAdministration.setConsumable(medicationsModel.getConsumable());
		// entryRelationship - RecordType - Long/Short term
		// entryRelationship - Last Review Date
		// Loop through prescriptions entryRelationship

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
