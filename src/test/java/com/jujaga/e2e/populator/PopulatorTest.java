package com.jujaga.e2e.populator;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.generic.CE;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.emr.PatientExport;

public class PopulatorTest {
	private static CE<String> code = Constants.EMRConversionDocument.CODE;
	private static II templateId = new II(Constants.EMRConversionDocument.TEMPLATE_ID);

	private static final Integer VALID_DEMOGRAPHIC = 1;

	@Test
	public void validPopulatorTest() {
		PatientExport patientExport = new PatientExport(VALID_DEMOGRAPHIC);

		AbstractPopulator populator = new EmrExportPopulator(patientExport, code, templateId);
		populator.populate();
		assertNotNull(populator);
		assertNotNull(populator.getClinicalDocument());
	}
}
