package com.jujaga.e2e.populator;

import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.generic.CE;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;

import com.jujaga.e2e.populator.body.DocumentBodyPopulator;
import com.jujaga.e2e.populator.header.HeaderPopulator;
import com.jujaga.emr.PatientExport;

public class EmrExportPopulator extends AbstractPopulator {
	public EmrExportPopulator(Integer demographicNo, CE<String> code, II templateId) {
		this.patientExport = new PatientExport(demographicNo);
		if(patientExport.isLoaded()) {
			HeaderPopulator emrExportHeaderPopulator = new HeaderPopulator(demographicNo, code, templateId);
			this.populators.add(emrExportHeaderPopulator);

			DocumentBodyPopulator bodyPopulator = new DocumentBodyPopulator(demographicNo);
			this.populators.add(bodyPopulator);

			this.clinicalDocument = new ClinicalDocument();
			AbstractPopulator.setClinicalDocument(clinicalDocument, this.populators);
		}
	}

	@Override
	public void populate() {
		AbstractPopulator.doPopulate(this);
	}
}
