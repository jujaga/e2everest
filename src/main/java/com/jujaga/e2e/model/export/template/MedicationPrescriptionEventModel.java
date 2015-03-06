package com.jujaga.e2e.model.export.template;

import java.util.ArrayList;
import java.util.Arrays;

import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.SubstanceAdministration;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntryRelationship;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_DocumentSubstanceMood;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.constant.BodyConstants.Medications;
import com.jujaga.e2e.model.export.body.BodyUtils;
import com.jujaga.emr.model.Drug;

public class MedicationPrescriptionEventModel {
	private Drug drug;

	public EntryRelationship getEntryRelationship(Drug drug) {
		this.drug = drug;
		EntryRelationship entryRelationship = new EntryRelationship();
		SubstanceAdministration substanceAdministration = new SubstanceAdministration();

		entryRelationship.setTypeCode(x_ActRelationshipEntryRelationship.HasComponent);
		entryRelationship.setContextConductionInd(true);
		entryRelationship.setTemplateId(new ArrayList<II>(Arrays.asList(new II(Medications.MEDICATION_PRESCRIPTION_EVENT_TEMPLATE_ID))));

		substanceAdministration.setMoodCode(x_DocumentSubstanceMood.RQO);
		substanceAdministration.setId(getIds());
		substanceAdministration.setCode(getCode());

		entryRelationship.setClinicalStatement(substanceAdministration);

		return entryRelationship;
	}

	private SET<II> getIds() {
		return BodyUtils.buildUniqueId(Constants.IdPrefixes.MedicationPrescriptions, drug.getId());
	}

	private CD<String> getCode() {
		CD<String> code = new CD<String>(Constants.SubstanceAdministrationType.DRUG.toString(), Constants.CodeSystems.ACT_CODE_CODESYSTEM_OID);
		code.setCodeSystemName(Constants.CodeSystems.ACT_CODE_CODESYSTEM_NAME);
		return code;
	}
}
