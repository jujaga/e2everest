package com.jujaga.e2e.model.export.body;

import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.generic.SET;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.util.EverestUtils;
import com.jujaga.emr.model.CaseManagementNote;

public class EncountersModel {
	private CaseManagementNote encounter;

	private SET<II> ids;

	public EncountersModel(CaseManagementNote encounter) {
		if(encounter == null) {
			this.encounter = new CaseManagementNote();
		} else {
			this.encounter = encounter;
		}

		setIds();
	}

	public String getTextSummary() {
		StringBuilder sb = new StringBuilder();
		return sb.toString();
	}

	public SET<II> getIds() {
		return ids;
	}

	private void setIds() {
		this.ids = EverestUtils.buildUniqueId(Constants.IdPrefixes.Encounters, encounter.getId());
	}
}
