package com.jujaga.e2e.populator.body;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalStatement;

public interface ISectionPopulator {
	// TODO Figure out generic for this function
	//public ClinicalStatement populateClinicalStatement();
	public ClinicalStatement populateNullFlavorClinicalStatement();
	public String populateText();
}
