package com.jujaga.e2e.populator;

import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.generic.CE;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;

import com.jujaga.e2e.populator.body.DocumentBodyPopulator;
import com.jujaga.e2e.populator.header.HeaderPopulator;
import com.jujaga.emr.PatientExport;

public class EmrExportPopulator extends AbstractPopulator {
	public EmrExportPopulator(PatientExport patientExport, CE<String> code, II templateId) {
		if(patientExport.isLoaded()) {
			HeaderPopulator emrExportHeaderPopulator = new HeaderPopulator(patientExport, code, templateId);
			this.populators.add(emrExportHeaderPopulator);

			DocumentBodyPopulator bodyPopulator = new DocumentBodyPopulator(patientExport);
			this.populators.add(bodyPopulator);

			this.clinicalDocument = new ClinicalDocument();
			AbstractPopulator.setClinicalDocument(clinicalDocument, this.populators);
		}
		else {
			this.clinicalDocument = null;
		}
	}

	@Override
	public void populate() {
		AbstractPopulator.doPopulate(this);
	}
}
