package com.jujaga.e2e.model.export.body;

import java.util.ArrayList;

import org.marc.everest.datatypes.BL;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.TS;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.datatypes.generic.IVL;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Observation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ActStatus;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActMoodDocumentObservation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntryRelationship;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.util.EverestUtils;
import com.jujaga.emr.model.Allergy;

public class AllergiesModel {
	private Allergy allergy;

	private SET<II> ids;
	private CD<String> code;
	private ActStatus statusCode;
	private IVL<TS> effectiveTime;
	private EntryRelationship allergyObservation;

	public AllergiesModel(Allergy allergy) {
		if(allergy == null) {
			this.allergy = new Allergy();
		} else {
			this.allergy = allergy;
		}

		setIds();
		setCode();
		setStatusCode();
		setEffectiveTime();
		setAllergyObservation();
	}

	public String getTextSummary() {
		StringBuilder sb = new StringBuilder();

		if(!EverestUtils.isNullorEmptyorWhitespace(allergy.getDescription())) {
			sb.append(allergy.getDescription());
		}
		if(allergy.getEntryDate() != null) {
			sb.append(" " + allergy.getEntryDate().toString());
		}

		return sb.toString();
	}

	public SET<II> getIds() {
		return ids;
	}

	private void setIds() {
		this.ids = EverestUtils.buildUniqueId(Constants.IdPrefixes.Allergies, allergy.getId());
	}

	public CD<String> getCode() {
		return code;
	}

	private void setCode() {
		CD<String> code = new CD<String>("48765-2", Constants.CodeSystems.LOINC_OID,
				Constants.CodeSystems.LOINC_NAME, Constants.CodeSystems.LOINC_VERSION);
		this.code = code;
	}

	public ActStatus getStatusCode() {
		return statusCode;
	}

	private void setStatusCode() {
		if(allergy.getArchived()) {
			this.statusCode = ActStatus.Completed;
		} else {
			this.statusCode = ActStatus.Active;
		}
	}

	public IVL<TS> getEffectiveTime() {
		return effectiveTime;
	}

	private void setEffectiveTime() {
		IVL<TS> ivl = new IVL<TS>();
		TS startTime = EverestUtils.buildTSFromDate(allergy.getEntryDate());
		if(startTime != null) {
			ivl.setLow(startTime);
		} else {
			ivl.setNullFlavor(NullFlavor.Unknown);
		}

		this.effectiveTime = ivl;
	}

	public EntryRelationship getAllergyObservation() {
		return allergyObservation;
	}

	private void setAllergyObservation() {
		EntryRelationship entryRelationship = new EntryRelationship(x_ActRelationshipEntryRelationship.HasComponent, new BL(true));
		Observation observation = new Observation(x_ActMoodDocumentObservation.Eventoccurrence);
		ArrayList<EntryRelationship> entryRelationships = new ArrayList<EntryRelationship>();

		observation.setCode(getAdverseEventCode());
		observation.setEffectiveTime(getOnsetDate());

		observation.setEntryRelationship(entryRelationships);
		entryRelationship.setClinicalStatement(observation);

		this.allergyObservation = entryRelationship;
	}

	protected CD<String> getAdverseEventCode() {
		CD<String> code = new CD<String>();
		code.setCodeEx("TBD"); //TODO Add Enum for ReactionTypeCode (page 395)
		code.setCodeSystem(Constants.CodeSystems.ACT_CODE_CODESYSTEM_OID);
		code.setCodeSystemName(Constants.CodeSystems.ACT_CODE_CODESYSTEM_NAME);
		return code;
	}

	protected IVL<TS> getOnsetDate() {
		IVL<TS> ivl = new IVL<TS>();
		TS startTime = EverestUtils.buildTSFromDate(allergy.getStartDate());
		if(startTime != null) {
			ivl.setLow(startTime);
		} else {
			ivl.setNullFlavor(NullFlavor.Unknown);
		}

		return ivl;
	}
}
