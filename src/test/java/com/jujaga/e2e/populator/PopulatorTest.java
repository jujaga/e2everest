package com.jujaga.e2e.populator;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.generic.CE;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.emr.PatientExport;

public class PopulatorTest {
	private static final Integer INVALID_DEMOGRAPHIC = 0;

	@Test
	public void invalidEmrExportPopulatorTest() {
		PatientExport patientExport = new PatientExport(INVALID_DEMOGRAPHIC);
		CE<String> code = Constants.EMRConversionDocument.CODE;
		II templateId = new II(Constants.EMRConversionDocument.TEMPLATE_ID);

		AbstractPopulator populator = new EmrExportPopulator(patientExport, code, templateId);
		populator.populate();
		assertNotNull(populator);

		ClinicalDocument clinicalDocument = populator.getClinicalDocument();
		assertNull(clinicalDocument);
	}
}
