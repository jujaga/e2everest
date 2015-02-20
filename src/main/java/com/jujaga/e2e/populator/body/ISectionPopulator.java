package com.jujaga.e2e.populator.body;

import java.util.List;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalStatement;

public interface ISectionPopulator<T> {
	public ClinicalStatement populateClinicalStatement(List<T> list);
	public ClinicalStatement populateNullFlavorClinicalStatement();
	public String populateText();
}
