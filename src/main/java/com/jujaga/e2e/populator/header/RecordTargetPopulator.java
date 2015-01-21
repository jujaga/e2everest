package com.jujaga.e2e.populator.header;

import java.util.ArrayList;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.PatientRole;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.RecordTarget;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ContextControl;

import com.jujaga.e2e.populator.Populator;

public class RecordTargetPopulator extends Populator {
	private final Integer demographicNo;

	public RecordTargetPopulator(Integer demographicNo) {
		this.demographicNo = demographicNo;
	}

	@Override
	public void Populate() {
		RecordTarget recordTarget = new RecordTarget();
		PatientRole patientRole = new PatientRole();

		recordTarget.setContextControlCode(ContextControl.OverridingPropagating);
		recordTarget.setPatientRole(patientRole);

		ArrayList<RecordTarget> recordTargets = new ArrayList<RecordTarget>();
		recordTargets.add(recordTarget);

		clinicalDocument.setRecordTarget(recordTargets);
	}

}
