package com.jujaga.e2e.model.export.body;

import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ActStatus;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.util.EverestUtils;
import com.jujaga.emr.model.CaseManagementNote;

public class RiskFactorsModel {
	private CaseManagementNote riskFactor;

	private SET<II> ids;
	private CD<String> code;
	private ActStatus statusCode;

	public RiskFactorsModel(CaseManagementNote riskFactor) {
		if(riskFactor == null) {
			this.riskFactor = new CaseManagementNote();
		} else {
			this.riskFactor = riskFactor;
		}

		setIds();
		setCode();
		setStatusCode();
	}

	public String getTextSummary() {
		StringBuilder sb = new StringBuilder();

		if(riskFactor.getObservation_date() != null) {
			sb.append(riskFactor.getObservation_date());
		}
		if(!EverestUtils.isNullorEmptyorWhitespace(riskFactor.getNote())) {
			sb.append(" ".concat(riskFactor.getNote().replaceAll("\\\\n", "\n")));
		}

		return sb.toString();
	}

	public SET<II> getIds() {
		return ids;
	}

	private void setIds() {
		this.ids = EverestUtils.buildUniqueId(Constants.IdPrefixes.RiskFactors, riskFactor.getId());
	}

	public CD<String> getCode() {
		return code;
	}

	private void setCode() {
		CD<String> code = new CD<String>("40514009", Constants.CodeSystems.SNOMED_CT_OID);
		code.setCodeSystemName(Constants.CodeSystems.SNOMED_CT_NAME);
		this.code = code;
	}

	public ActStatus getStatusCode() {
		return statusCode;
	}

	private void setStatusCode() {
		if(riskFactor.isArchived() != null && !riskFactor.isArchived()) {
			this.statusCode = ActStatus.Active;
		} else {
			this.statusCode = ActStatus.Completed;
		}
	}
}
