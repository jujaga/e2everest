package com.jujaga.e2e.populator.body;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.marc.everest.datatypes.BL;
import org.marc.everest.datatypes.II;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalStatement;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Entry;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Observation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActMoodDocumentObservation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntry;

import com.jujaga.e2e.constant.BodyConstants.Problems;
import com.jujaga.emr.PatientExport;
import com.jujaga.emr.model.Dxresearch;

public class ProblemsPopulator extends AbstractBodyPopulator<Dxresearch> {
	private List<Dxresearch> allProblems;
	private List<Dxresearch> problems;

	ProblemsPopulator(PatientExport patientExport) {
		bodyConstants = Problems.getConstants();
		allProblems = patientExport.getProblems();
		problems = new ArrayList<Dxresearch>();

		for(Dxresearch problem : allProblems) {
			if(problem.getStatus() != 'D' && problem.getCodingSystem().equalsIgnoreCase("icd9")) {
				this.problems.add(problem);
			}
		}
	}

	@Override
	public void populate() {
		for(Dxresearch problem : problems) {
			Entry entry = new Entry(x_ActRelationshipEntry.DRIV, new BL(true));
			entry.setTemplateId(Arrays.asList(new II(bodyConstants.ENTRY_TEMPLATE_ID)));
			entry.setClinicalStatement(populateClinicalStatement(Arrays.asList(problem)));
			entries.add(entry);
		}

		super.populate();
	}

	@Override
	public ClinicalStatement populateClinicalStatement(List<Dxresearch> problem) {
		Observation observation = new Observation(x_ActMoodDocumentObservation.Eventoccurrence);
		return observation;
	}

	@Override
	public ClinicalStatement populateNullFlavorClinicalStatement() {
		return null;
	}

	@Override
	public List<String> populateText() {
		List<String> list = new ArrayList<String>();
		/*for(Dxresearch problem : problems) {
			MedicationsModel medicationsModel = new MedicationsModel(medication.get(0));
			list.add(medicationsModel.getTextSummary());
		}*/

		return list;
	}
}
