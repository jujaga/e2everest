package com.jujaga.e2e.model.export.template;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.SubstanceAdministration;

import com.jujaga.emr.model.Drug;

public class MedicationPrescriptionEventModel {
	@SuppressWarnings("unused") // Temporary
	private Drug drug;

	public EntryRelationship getEntryRelationship(Drug drug) {
		this.drug = drug;
		EntryRelationship entryRelationship = new EntryRelationship();
		SubstanceAdministration substanceAdministration = new SubstanceAdministration();

		entryRelationship.setClinicalStatement(substanceAdministration);

		return entryRelationship;
	}
}
