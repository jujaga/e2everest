package com.jujaga.e2e.populator;

import java.util.ArrayList;
import java.util.List;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;

import com.jujaga.emr.PatientExport;

public abstract class AbstractPopulator {
	protected List<AbstractPopulator> populators;
	protected ClinicalDocument clinicalDocument = null;
	protected PatientExport patientExport;

	protected AbstractPopulator() {
		populators = new ArrayList<AbstractPopulator>();
	}

	public abstract void populate();

	protected static void doPopulate(AbstractPopulator populator) {
		for(AbstractPopulator subPopulator : populator.populators) {
			subPopulator.populate();
			AbstractPopulator.doPopulate(subPopulator);
		}
	}

	public ClinicalDocument getClinicalDocument() {
		return clinicalDocument;
	}

	public static void setClinicalDocument(ClinicalDocument clinicalDocument, List<AbstractPopulator> populators) {
		for(AbstractPopulator populator : populators) {
			populator.clinicalDocument = clinicalDocument;
			setClinicalDocument(clinicalDocument, populator.populators);
		}
	}

	public PatientExport getPatientExport() {
		return patientExport;
	}

	public void setPatientExport(PatientExport patientExport) {
		this.patientExport = patientExport;
	}
}
