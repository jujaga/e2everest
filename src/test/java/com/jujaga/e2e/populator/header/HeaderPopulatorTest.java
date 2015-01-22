package com.jujaga.e2e.populator.header;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.generic.CE;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;

import com.jujaga.e2e.StubRecord;
import com.jujaga.e2e.constant.Constants;

public class HeaderPopulatorTest {
	static HeaderPopulator headerPopulator = null;
	static ClinicalDocument clinicalDocument = null;

	@BeforeClass
	public static void onlyOnce() throws Exception {
		Integer demographicNo = StubRecord.Demographic.demographicNo;
		CE<String> code = Constants.EMRConversionDocument.CODE;
		II templateId = new II(Constants.EMRConversionDocument.TEMPLATE_ID);
		
		headerPopulator = new HeaderPopulator(demographicNo, code, templateId);
		//headerPopulator.populate();
		//clinicalDocument = headerPopulator.getClinicalDocument();
	}
	
	@Test
	public void test() {
		assertNotNull(headerPopulator);
		//assertNotNull(clinicalDocument);
		//clinicalDocument.getRealmCode();
	}
}
