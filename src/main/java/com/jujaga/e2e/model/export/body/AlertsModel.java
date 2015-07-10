package com.jujaga.e2e.model.export.body;

import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.datatypes.generic.SET;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.util.EverestUtils;
import com.jujaga.emr.model.CaseManagementNote;

public class AlertsModel {
	private CaseManagementNote alert;

	private SET<II> ids;
	private CD<String> code;

	public AlertsModel(CaseManagementNote alert) {
		if(alert == null) {
			this.alert = new CaseManagementNote();
		} else {
			this.alert = alert;
		}

		setIds();
		setCode();
	}

	public String getTextSummary() {
		StringBuilder sb = new StringBuilder();

		if(alert.getObservation_date() != null) {
			sb.append(alert.getObservation_date());
		}
		if(!EverestUtils.isNullorEmptyorWhitespace(alert.getNote())) {
			sb.append(" ".concat(alert.getNote().replaceAll("\\\\n", "\n")));
		}

		return sb.toString();
	}

	public SET<II> getIds() {
		return ids;
	}

	private void setIds() {
		this.ids = EverestUtils.buildUniqueId(Constants.IdPrefixes.Alerts, alert.getId());
	}

	public CD<String> getCode() {
		return code;
	}

	public void setCode() {
		CD<String> code = new CD<String>();
		code.setNullFlavor(NullFlavor.NoInformation);
		this.code = code;
	}
}
