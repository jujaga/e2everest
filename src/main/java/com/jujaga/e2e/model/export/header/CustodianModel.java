package com.jujaga.e2e.model.export.header;

import java.util.ArrayList;

import org.marc.everest.datatypes.ENXP;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.ON;
import org.marc.everest.datatypes.generic.SET;

import com.jujaga.e2e.StubRecord;
import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.util.EverestUtils;

public class CustodianModel {
	private SET<II> ids;
	private ON name;

	public CustodianModel(Integer demographicNo) {
		if(demographicNo <= 0) {
			System.out.println("demographicNo should be greater than 0");
		}

		setIds();
		setName();
	}

	public SET<II> getIds() {
		return ids;
	}

	private void setIds() {
		II id = new II();
		if(!EverestUtils.isNullorEmptyorWhitespace(StubRecord.Custodian.custodianId)) {
			id.setRoot(Constants.EMR.EMR_OID);
			id.setAssigningAuthorityName(Constants.EMR.EMR_VERSION);
			id.setExtension(StubRecord.Custodian.custodianId);
		} else {
			id.setNullFlavor(NullFlavor.NoInformation);
		}
		this.ids = new SET<II>(id);
	}

	public ON getName() {
		return name;
	}

	private void setName() {
		ArrayList<ENXP> name = new ArrayList<ENXP>();
		if(!EverestUtils.isNullorEmptyorWhitespace(StubRecord.Custodian.cusstodianClinicName)) {
			name.add(new ENXP(StubRecord.Custodian.cusstodianClinicName));
		}
		if(!name.isEmpty()) {
			ON on = new ON();
			on.setParts(name);
			this.name = on;
		}
		else {
			this.name = null;
		}
	}
}
