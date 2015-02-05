package com.jujaga.e2e.populator;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.generic.CE;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;

import com.jujaga.e2e.StubRecord;
import com.jujaga.e2e.constant.Constants;

public class PopulatorTest {
	private static AbstractPopulator populator;
	private static ClinicalDocument clinicalDocument;

	@BeforeClass
	public static void beforeClass() {
		Integer demographicNo = StubRecord.Demographic.demographicNo;
		CE<String> code = Constants.EMRConversionDocument.CODE;
		II templateId = new II(Constants.EMRConversionDocument.TEMPLATE_ID);

		populator = new EmrExportPopulator(demographicNo, code, templateId);
		populator.populate();
		clinicalDocument = populator.getClinicalDocument();
	}

	@Test
	public void populatorTest() {
		assertNotNull(populator);
	}

	@Test
	public void clinicalDocumentTest() {
		assertNotNull(clinicalDocument);
	}
}
