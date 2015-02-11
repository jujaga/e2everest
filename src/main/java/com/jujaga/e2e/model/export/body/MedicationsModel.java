package com.jujaga.e2e.model.export.body;

import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.generic.SET;

import com.jujaga.e2e.constant.Constants;

public class MedicationsModel {
	private SET<II> ids;

	public MedicationsModel() {
		setIds();
	}

	public SET<II> getIds() {
		return ids;
	}

	private void setIds() {
		this.ids = BodyUtils.buildUniqueId(Constants.IdPrefixes.Medications, 1);
	}
}
