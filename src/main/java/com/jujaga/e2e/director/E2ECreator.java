package com.jujaga.e2e.director;

import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.generic.CE;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.populator.EmrExportPopulator;
import com.jujaga.emr.PatientExport;

public abstract class E2ECreator {
	public static ClinicalDocument createEmrConversionDocument(Integer demographicNo) {
		PatientExport patientExport = new PatientExport(demographicNo);
		CE<String> code = Constants.EMRConversionDocument.CODE;
		II templateId = new II(Constants.EMRConversionDocument.TEMPLATE_ID);

		EmrExportPopulator emrExportPopulator = new EmrExportPopulator(patientExport, code, templateId);
		emrExportPopulator.populate();

		return emrExportPopulator.getClinicalDocument();
	}
}
