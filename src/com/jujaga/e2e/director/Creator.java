package com.jujaga.e2e.director;

import java.util.ArrayList;
import java.util.UUID;

import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.generic.CS;
import org.marc.everest.datatypes.generic.LIST;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.PatientRole;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.RecordTarget;
import org.marc.everest.rmim.uv.cdar2.vocabulary.BindingRealm;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ContextControl;

import com.jujaga.e2e.StubRecord;
import com.jujaga.e2e.constant.Constants;

public class Creator {
	@SuppressWarnings("unchecked")
	public ClinicalDocument CreateEmrConversionDocument() {
		ClinicalDocument clinicalDocument = new ClinicalDocument();

		//TODO: Move to proper header populator
		// realmCode
		CS<BindingRealm> binding = new CS<BindingRealm>();
		binding.setCodeEx(new BindingRealm(Constants.DocumentHeader.PITO_E2E_DTC_CLINICAL_DOCUMENT_TYPE_REALM_CODE, null));
		new SET<CS<BindingRealm>>();
		clinicalDocument.setRealmCode(SET.createSET(binding));

		// typeId
		clinicalDocument.setTypeId(new II(Constants.DocumentHeader.PITO_E2E_DTC_CLINICAL_DOCUMENT_TYPE_ID, Constants.DocumentHeader.PITO_E2E_DTC_CLINICAL_DOCUMENT_TYPE_ID_EXTENSION));

		// templateId
		LIST<II> templateIds = new LIST<II>();
		templateIds.add(new II(Constants.DocumentHeader.TEMPLATE_ID));
		templateIds.add(new II(Constants.EMRConversionDocument.TEMPLATE_ID));
		clinicalDocument.setTemplateId(templateIds);

		// id
		clinicalDocument.setId(StubRecord.demographicNo.toString(), UUID.randomUUID().toString().toUpperCase());

		// code
		//clinicalDocument.setCode(new CE<String>(Constants.DocumentHeader.TEMPLATE_ID));


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
