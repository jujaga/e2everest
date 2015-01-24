package com.jujaga.e2e.director;

import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.generic.CE;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.populator.EmrExportPopulator;

public class E2ECreator {
	public ClinicalDocument createEmrConversionDocument(Integer demographicNo) {
		CE<String> code = Constants.EMRConversionDocument.CODE;
		II templateId = new II(Constants.EMRConversionDocument.TEMPLATE_ID);

		EmrExportPopulator emrExportPopulator = new EmrExportPopulator(demographicNo, code, templateId);
		emrExportPopulator.populate();

		return emrExportPopulator.getClinicalDocument();
	}
}