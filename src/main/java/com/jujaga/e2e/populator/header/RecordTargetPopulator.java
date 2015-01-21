package com.jujaga.e2e.populator.header;

import java.util.ArrayList;
import java.util.Arrays;

import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.PatientRole;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.RecordTarget;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ContextControl;

import com.jujaga.e2e.StubRecord;
import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.populator.Populator;
import com.jujaga.e2e.util.EverestUtils;

public class RecordTargetPopulator extends Populator {
	//private final Integer demographicNo;

	public RecordTargetPopulator(Integer demographicNo) {
		//this.demographicNo = demographicNo;
	}

	@Override
	public void populate() {
		RecordTarget recordTarget = new RecordTarget();
		PatientRole patientRole = new PatientRole();

		recordTarget.setContextControlCode(ContextControl.OverridingPropagating);
		recordTarget.setPatientRole(patientRole);

		// id
		II id = new II(Constants.DocumentHeader.BC_PHN_OID);
		id.setAssigningAuthorityName(Constants.DocumentHeader.BC_PHN_OID_ASSIGNING_AUTHORITY_NAME);
		if(!EverestUtils.isNullorEmptyorWhitespace(StubRecord.Demographic.hin)) {
			id.setExtension(StubRecord.Demographic.hin);
		} else {
			id.setNullFlavor(NullFlavor.NoInformation);
		}
		patientRole.setId(new SET<II>(id));

		clinicalDocument.setRecordTarget(new ArrayList<RecordTarget>(Arrays.asList(recordTarget)));
	}
}
