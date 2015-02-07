package com.jujaga.e2e.populator;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.generic.CE;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.director.E2ECreator;
import com.jujaga.emr.PatientExport;

public abstract class AbstractPopulatorTest {
	protected static ClinicalDocument clinicalDocument;

	@BeforeClass
	public static void beforeClass() {
		PatientExport patientExport = new PatientExport(Constants.Runtime.VALID_DEMOGRAPHIC);
		CE<String> code = Constants.EMRConversionDocument.CODE;
		II templateId = new II(Constants.EMRConversionDocument.TEMPLATE_ID);

		AbstractPopulator populator = new EmrExportPopulator(patientExport, code, templateId);
		populator.populate();
		clinicalDocument = E2ECreator.createEmrConversionDocument(Constants.Runtime.VALID_DEMOGRAPHIC);
		assertNotNull(clinicalDocument);
	}
}
