package com.jujaga.e2e.model.export.body;

import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ActStatus;

import com.jujaga.e2e.constant.BodyConstants;
import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.util.EverestUtils;
import com.jujaga.emr.model.Dxresearch;

public class ProblemsModel {
	//private static Logger log = Logger.getLogger(ProblemsModel.class.getName());

	private Dxresearch problem;
	private SET<II> ids;
	private CD<String> code;
	private ActStatus statusCode;

	public ProblemsModel(Dxresearch problem) {
		if(problem == null) {
			this.problem = new Dxresearch();
		} else {
			this.problem = problem;
		}

		setIds();
		setCode();
		setStatusCode();
	}

	public String getTextSummary() {
		StringBuilder sb = new StringBuilder();

		// TODO Add ICD9Description field
		if(!EverestUtils.isNullorEmptyorWhitespace(problem.getDxresearchCode())) {
			sb.append(" " + problem.getDxresearchCode());
		}

		return sb.toString();
	}

	public SET<II> getIds() {
		return ids;
	}

	private void setIds() {
		this.ids = BodyUtils.buildUniqueId(Constants.IdPrefixes.ProblemList, problem.getDxresearchNo());
	}

	public CD<String> getCode() {
		return code;
	}

	private void setCode() {
		this.code = new CD<String>(BodyConstants.Problems.SNOMED_CT_DIAGNOSIS_CODE, Constants.CodeSystems.SNOMED_CT_OID);
		this.code.setCodeSystemName(Constants.CodeSystems.SNOMED_CT_NAME);
		this.code.setDisplayName(BodyConstants.Problems.SNOMED_CT_DIAGNOSIS_NAME);
	}

	public ActStatus getStatusCode() {
		return statusCode;
	}

	private void setStatusCode() {
		if(problem.getStatus() != null && problem.getStatus() == 'A') {
			this.statusCode = ActStatus.Active;
		} else {
			this.statusCode = ActStatus.Completed;
		}
	}
}
