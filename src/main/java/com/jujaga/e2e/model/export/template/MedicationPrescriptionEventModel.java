package com.jujaga.e2e.model.export.template;

import java.util.ArrayList;
import java.util.Arrays;

import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.SetOperator;
import org.marc.everest.datatypes.TS;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.datatypes.generic.IVL;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.datatypes.interfaces.ISetComponent;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Consumable;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.SubstanceAdministration;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntryRelationship;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_DocumentSubstanceMood;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.model.export.body.BodyUtils;
import com.jujaga.emr.model.Drug;

public class MedicationPrescriptionEventModel {
	private Drug drug;

	public EntryRelationship getEntryRelationship(Drug drug) {
		if(drug == null) {
			this.drug = new Drug();
		} else {
			this.drug = drug;
		}

		EntryRelationship entryRelationship = new EntryRelationship();
		SubstanceAdministration substanceAdministration = new SubstanceAdministration();
		ArrayList<EntryRelationship> entryRelationships = new ArrayList<EntryRelationship>();

		entryRelationship.setTypeCode(x_ActRelationshipEntryRelationship.HasComponent);
		entryRelationship.setContextConductionInd(true);
		entryRelationship.setTemplateId(Arrays.asList(new II(Constants.TemplateOids.MEDICATION_PRESCRIPTION_EVENT_TEMPLATE_ID)));

		substanceAdministration.setMoodCode(x_DocumentSubstanceMood.RQO);
		substanceAdministration.setId(getIds());
		substanceAdministration.setCode(getCode());
		substanceAdministration.setEffectiveTime(getEffectiveTime());
		substanceAdministration.setConsumable(getConsumable());
		substanceAdministration.setAuthor(getAuthor());

		entryRelationships.add(getDose());

		substanceAdministration.setEntryRelationship(entryRelationships);

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

	private ArrayList<ISetComponent<TS>> getEffectiveTime() {
		TS startTime = BodyUtils.buildTSFromDate(drug.getRxDate());
		TS endTime = BodyUtils.buildTSFromDate(drug.getEndDate());

		IVL<TS> ivl = new IVL<TS>();
		ivl.setOperator(SetOperator.Inclusive);
		if(startTime == null) {
			startTime = new TS();
			startTime.setNullFlavor(NullFlavor.Unknown);
			ivl.setLow(startTime);
		} else {
			ivl.setLow(startTime);
			ivl.setHigh(endTime);
		}

		ArrayList<ISetComponent<TS>> effectiveTime = new ArrayList<ISetComponent<TS>>();
		effectiveTime.add(ivl);

		return effectiveTime;
	}

	private Consumable getConsumable() {
		return new ConsumableModel().getConsumable(drug);
	}

	private ArrayList<Author> getAuthor() {
		ArrayList<Author> authors = new ArrayList<Author>();
		authors.add(new AuthorParticipationModel(drug.getProviderNo()).getAuthor(drug.getWrittenDate()));
		return authors;
	}

	private EntryRelationship getDose() {
		EntryRelationship entryRelationship = new EntryRelationship();
		SubstanceAdministration substanceAdministration = new SubstanceAdministration();

		entryRelationship.setTypeCode(x_ActRelationshipEntryRelationship.HasComponent);
		entryRelationship.setContextConductionInd(true);
		entryRelationship.setTemplateId(Arrays.asList(new II(Constants.TemplateOids.DOSE_OBSERVATION_TEMPLATE_ID)));

		substanceAdministration.setMoodCode(x_DocumentSubstanceMood.RQO);

		entryRelationship.setClinicalStatement(substanceAdministration);

		return entryRelationship;
	}
}
