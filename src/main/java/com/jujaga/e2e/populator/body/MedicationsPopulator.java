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

import com.jujaga.e2e.model.export.body.MedicationsModel;
import com.jujaga.emr.PatientExport;
import com.jujaga.emr.model.Drug;

public class MedicationsPopulator extends AbstractBodyPopulator {
	private List<Drug> allDrugs;
	private Map<Integer, List<Drug>> mapDrugs;

	MedicationsPopulator(PatientExport patientExport) {
		mapDrugs = new HashMap<Integer, List<Drug>>();
		allDrugs = patientExport.getMedications();
		Collections.reverse(allDrugs); // Order recent drugs first

		for(Drug drug : allDrugs) {
			Integer din;
			try {
				din = Integer.parseInt(drug.getRegionalIdentifier());
			} catch (NumberFormatException e) {
				din = -1; // TODO Convert to constant
			}

			if(mapDrugs.containsKey(din)) {
				mapDrugs.get(din).add(drug);
			} else {
				mapDrugs.put(din, Arrays.asList(drug));
			}
		}

		// Sort drugs based off of DIN or drugName
		/*Collections.sort(drugs, new Comparator<Drug>() {
			@Override
			public int compare(Drug one, Drug two) {
				int answer;
				try {
					answer = Integer.parseInt(one.getRegionalIdentifier()) - Integer.parseInt(two.getRegionalIdentifier());
				} catch (Exception e) {
					answer = getDrugName(one).compareTo(getDrugName(two));
				}
				return answer;
			}

			private String getDrugName(Drug drug) {
				if(drug.getBrandName() != null) {
					return drug.getBrandName();
				} else if(drug.getGenericName() != null) {
					return drug.getGenericName();
				} else {
					return "";
				}
			}
		});*/
	}



	@Override
	public void populate() {
		for(Map.Entry<Integer, List<Drug>> medication : mapDrugs.entrySet()) {
			Entry entry = new Entry(x_ActRelationshipEntry.DRIV, new BL(true));
			entry.setClinicalStatement(populateClinicalStatement(medication.getValue()));
			entries.add(entry);
		}

		super.populate();
	}

	public ClinicalStatement populateClinicalStatement(List<Drug> medication) {
		MedicationsModel medicationsModel = new MedicationsModel(medication.get(0));
		SubstanceAdministration substanceAdministration = new SubstanceAdministration();
		substanceAdministration.setMoodCode(x_DocumentSubstanceMood.Eventoccurrence);
		substanceAdministration.setId(medicationsModel.getIds());

		return substanceAdministration;
	}

	@Override
	public ClinicalStatement populateClinicalStatement(Object list) {
		// TODO Resolve polymorphic argument list
		return null;
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
