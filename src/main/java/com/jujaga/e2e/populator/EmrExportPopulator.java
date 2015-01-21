package com.jujaga.e2e.populator;

import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.generic.CE;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;

import com.jujaga.e2e.populator.header.HeaderPopulator;

public class EmrExportPopulator extends Populator {
	public EmrExportPopulator(int demographicNo, CE<String> code, II templateId) {
		HeaderPopulator emrExportHeaderPopulator = new HeaderPopulator(demographicNo, code, templateId);
		this.populators.add(emrExportHeaderPopulator);
		// TODO Add body populator here

		this.clinicalDocument = new ClinicalDocument();
		Populator.setClinicalDocument(clinicalDocument, this.populators);
	}

	@Override
	public void populate() {
		Populator.doPopulate(this);
	}
}
