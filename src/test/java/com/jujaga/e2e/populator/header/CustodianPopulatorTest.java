package com.jujaga.e2e.populator.header;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.generic.CE;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.AssignedCustodian;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Custodian;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.CustodianOrganization;

import com.jujaga.e2e.StubRecord;
import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.populator.EmrExportPopulator;
import com.jujaga.e2e.populator.AbstractPopulator;

public class CustodianPopulatorTest {
	private static ClinicalDocument clinicalDocument;

	@BeforeClass
	public static void beforeClass() {
		Integer demographicNo = StubRecord.Demographic.demographicNo;
		CE<String> code = Constants.EMRConversionDocument.CODE;
		II templateId = new II(Constants.EMRConversionDocument.TEMPLATE_ID);

		AbstractPopulator populator = new EmrExportPopulator(demographicNo, code, templateId);
		populator.populate();
		clinicalDocument = populator.getClinicalDocument();
	}

	@Test
	public void custodianTest() {
		Custodian custodian = clinicalDocument.getCustodian();
		assertNotNull(custodian);
	}

	@Test
	public void assignedCustodianTest() {
		AssignedCustodian assignedCustodian = clinicalDocument.getCustodian().getAssignedCustodian();
		assertNotNull(assignedCustodian);
	}

	@Test
	public void custodianOrganizationTest() {
		AssignedCustodian assignedCustodian = clinicalDocument.getCustodian().getAssignedCustodian();
		CustodianOrganization custodianOrganization = assignedCustodian.getRepresentedCustodianOrganization();
		assertNotNull(custodianOrganization);
		assertNotNull(custodianOrganization.getId());
	}
}
