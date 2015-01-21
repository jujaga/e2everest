package com.jujaga.e2e.populator;

import java.util.ArrayList;
import java.util.List;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;

public abstract class Populator {
	protected List<Populator> populators;
	protected ClinicalDocument clinicalDocument;

	protected Populator() {
		populators = new ArrayList<Populator>();
	}

	public abstract void populate();

	protected static void doPopulate(Populator populator) {
		for(Populator subPopulator : populator.populators) {
			subPopulator.populate();
			Populator.doPopulate(subPopulator);
		}
	}

	public ClinicalDocument getClinicalDocument() {
		return clinicalDocument;
	}

	public static void setClinicalDocument(ClinicalDocument clinicalDocument, List<Populator> populators) {
		for(Populator populator : populators) {
			populator.clinicalDocument = clinicalDocument;
			setClinicalDocument(clinicalDocument, populator.populators);
		}
	}
}
