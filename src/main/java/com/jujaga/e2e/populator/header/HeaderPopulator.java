package com.jujaga.e2e.populator.header;

import java.util.UUID;

import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.TS;
import org.marc.everest.datatypes.generic.CE;
import org.marc.everest.datatypes.generic.CS;
import org.marc.everest.datatypes.generic.LIST;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.vocabulary.BindingRealm;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_BasicConfidentialityKind;

import com.jujaga.e2e.StubRecord;
import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.populator.Populator;

public class HeaderPopulator extends Populator {
	private final Integer demographicNo;
	private final CE<String> code;
	private final II templateId;

	public HeaderPopulator(Integer demographicNo, CE<String> code, II templateId) {
		this.demographicNo = demographicNo;
		this.code = code;
		this.templateId = templateId;

		// Record Target
		RecordTargetPopulator recordTargetPopulator = new RecordTargetPopulator(demographicNo);
		populators.add(recordTargetPopulator);

		// Author
		// Custodian
		// Information Recipient
	}

	@Override
	public void populate() {
		// realmCode
		CS<BindingRealm> binding = new CS<BindingRealm>();
		binding.setCodeEx(new BindingRealm(Constants.DocumentHeader.PITO_E2E_DTC_CLINICAL_DOCUMENT_TYPE_REALM_CODE, null));
		clinicalDocument.setRealmCode(new SET<CS<BindingRealm>>(binding));

		// typeId
		clinicalDocument.setTypeId(new II(
				Constants.DocumentHeader.PITO_E2E_DTC_CLINICAL_DOCUMENT_TYPE_ID,
				Constants.DocumentHeader.PITO_E2E_DTC_CLINICAL_DOCUMENT_TYPE_ID_EXTENSION));

		// templateId
		LIST<II> templateIds = new LIST<II>();
		templateIds.add(new II(Constants.DocumentHeader.TEMPLATE_ID));
		templateIds.add(templateId);
		clinicalDocument.setTemplateId(templateIds);

		// id
		clinicalDocument.setId(demographicNo.toString(), UUID.randomUUID().toString().toUpperCase());

		// code
		clinicalDocument.setCode(code);

		// title
		clinicalDocument.setTitle("PITO E2E-DTC Record of " + StubRecord.Demographic.firstName + " " + StubRecord.Demographic.lastName);

		// effectiveTime
		clinicalDocument.setEffectiveTime(StubRecord.Demographic.docCreated, TS.MINUTE);

		// confidentialityCode
		clinicalDocument.setConfidentialityCode(x_BasicConfidentialityKind.Normal);

		// languageCode
		clinicalDocument.setLanguageCode(Constants.DocumentHeader.LANGUAGE_ENGLISH_CANADIAN);
	}
}
