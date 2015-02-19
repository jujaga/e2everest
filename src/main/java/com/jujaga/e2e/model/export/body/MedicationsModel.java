package com.jujaga.e2e.model.export.body;

import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.generic.SET;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.emr.model.Drug;

public class MedicationsModel {
	private Drug drug;
	private SET<II> ids;

	public MedicationsModel(Drug drug) {
		this.drug = drug;

		setIds();
	}

	public SET<II> getIds() {
		return ids;
	}

	private void setIds() {
		this.ids = BodyUtils.buildUniqueId(Constants.IdPrefixes.Medications, drug.getId());
	}
}
