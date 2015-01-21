package com.jujaga.e2e.populator;

import java.util.ArrayList;
import java.util.List;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;

public abstract class Populator implements IPopulator {
	public List<Populator> populators;
	public ClinicalDocument clinicalDocument;
	// TODO Reconsider public/protected status

	public Populator() {
		populators = new ArrayList<Populator>();
	}

	public abstract void Populate();

	public static void DoPopulate(Populator populator) {
		for(Populator subPopulator : populator.populators) {
			subPopulator.Populate();
			Populator.DoPopulate(subPopulator);
		}
	}

	public ClinicalDocument getClinicalDocument() {
		return clinicalDocument;
	}

	public void setClinicalDocument(ClinicalDocument clinicalDocument) {
		this.clinicalDocument = clinicalDocument;
	}

	public static void SetClinicalDocument(ClinicalDocument clinicalDocument, List<Populator> populators) {
		for(Populator populator : populators) {
			populator.clinicalDocument = clinicalDocument;
			SetClinicalDocument(clinicalDocument, populator.populators);
		}
	}
}
