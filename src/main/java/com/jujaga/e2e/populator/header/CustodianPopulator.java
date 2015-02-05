package com.jujaga.e2e.populator.header;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.AssignedCustodian;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Custodian;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.CustodianOrganization;

import com.jujaga.e2e.model.export.header.CustodianModel;
import com.jujaga.e2e.populator.AbstractPopulator;

public class CustodianPopulator extends AbstractPopulator {
	private final CustodianModel custodianModel;

	public CustodianPopulator(Integer demographicNo) {
		custodianModel = new CustodianModel(demographicNo);
	}

	@Override
	public void populate() {
		Custodian custodian = new Custodian();
		AssignedCustodian assignedCustodian = new AssignedCustodian();
		CustodianOrganization custodianOrganization = new CustodianOrganization();

		custodian.setAssignedCustodian(assignedCustodian);
		assignedCustodian.setRepresentedCustodianOrganization(custodianOrganization);
		custodianOrganization.setId(custodianModel.getIds());
		custodianOrganization.setName(custodianModel.getName());

		clinicalDocument.setCustodian(custodian);
	}
}
