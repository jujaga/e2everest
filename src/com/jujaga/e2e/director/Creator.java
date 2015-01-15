package com.jujaga.e2e.director;

import java.util.ArrayList;

import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.PatientRole;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.RecordTarget;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ContextControl;

import com.jujaga.e2e.StubRecord;

public class Creator {
	public ClinicalDocument CreateEmrConversionDocument() {
		ClinicalDocument clinicalDocument = new ClinicalDocument();
		
		//TODO: Refactor to populate/factory design - this must be done (it's a mess right now)
		// Record Target
		RecordTarget recordTarget = new RecordTarget();
		PatientRole patientRole = new PatientRole();
		
		recordTarget.setContextControlCode(ContextControl.OverridingPropagating);
		recordTarget.setPatientRole(patientRole);
		
		II id = new II();
		id.setAssigningAuthorityName("BC-PHN");
		id.setExtension(StubRecord.demographicNo.toString());
		
		patientRole.setId(new SET<II>(id));
		
		ArrayList<RecordTarget> recordTargets = new ArrayList<RecordTarget>();
		clinicalDocument.setRecordTarget(recordTargets);
		
		// Author
		// Custodian
		
		return clinicalDocument;
	}
}
