package org.oscarehr.e2e.model.export.template;

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
import org.oscarehr.common.model.Drug;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.model.export.template.observation.DoseObservationModel;
import org.oscarehr.e2e.model.export.template.observation.InstructionObservationModel;
import org.oscarehr.e2e.model.export.template.observation.OrderIndicatorObservationModel;
import org.oscarehr.e2e.util.EverestUtils;

public class MedicationPrescriptionEventModel {
	private Drug drug;

	public EntryRelationship getEntryRelationship(Drug drug) {
		if(drug == null) {
			this.drug = new Drug();
		} else {
			this.drug = drug;
		}

		EntryRelationship entryRelationship = new EntryRelationship();
		SubstanceAdministration substanceAdministration = new SubstanceAdministration(x_DocumentSubstanceMood.RQO, getConsumable());
		ArrayList<EntryRelationship> entryRelationships = new ArrayList<EntryRelationship>();

		entryRelationship.setTypeCode(x_ActRelationshipEntryRelationship.HasComponent);
		entryRelationship.setContextConductionInd(true);
		entryRelationship.setTemplateId(Arrays.asList(new II(Constants.TemplateOids.MEDICATION_PRESCRIPTION_EVENT_TEMPLATE_ID)));

		substanceAdministration.setId(getIds());
		substanceAdministration.setCode(getCode());
		substanceAdministration.setEffectiveTime(getEffectiveTime());
		substanceAdministration.setAuthor(getAuthor());

		entryRelationships.add(getDoseInstructions());
		entryRelationships.add(getPRN());
		entryRelationships.add(getInstructionsToPatient());

		substanceAdministration.setEntryRelationship(entryRelationships);

		entryRelationship.setClinicalStatement(substanceAdministration);

		return entryRelationship;
	}

	private SET<II> getIds() {
		return EverestUtils.buildUniqueId(Constants.IdPrefixes.MedicationPrescriptions, drug.getId());
	}

	private CD<String> getCode() {
		CD<String> code = new CD<String>(Constants.SubstanceAdministrationType.DRUG.toString(), Constants.CodeSystems.ACT_CODE_CODESYSTEM_OID);
		code.setCodeSystemName(Constants.CodeSystems.ACT_CODE_CODESYSTEM_NAME);
		return code;
	}

	private ArrayList<ISetComponent<TS>> getEffectiveTime() {
		TS startTime = EverestUtils.buildTSFromDate(drug.getRxDate());
		TS endTime = EverestUtils.buildTSFromDate(drug.getEndDate());

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

	private EntryRelationship getDoseInstructions() {
		return new DoseObservationModel().getEntryRelationship(drug);
	}

	private EntryRelationship getPRN() {
		return new OrderIndicatorObservationModel().getEntryRelationship(drug.getPrn());
	}

	private EntryRelationship getInstructionsToPatient() {
		return new InstructionObservationModel().getEntryRelationship(drug.getSpecial_instruction());
	}
}
