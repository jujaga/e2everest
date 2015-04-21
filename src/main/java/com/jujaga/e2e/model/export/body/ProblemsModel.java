package com.jujaga.e2e.model.export.body;

import java.util.ArrayList;

import org.marc.everest.datatypes.ED;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.TS;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.datatypes.generic.IVL;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ActStatus;

import com.jujaga.e2e.constant.BodyConstants;
import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.model.export.template.AuthorParticipationModel;
import com.jujaga.e2e.model.export.template.observation.DateObservationModel;
import com.jujaga.e2e.model.export.template.observation.SecondaryCodeICD9ObservationModel;
import com.jujaga.e2e.util.EverestUtils;
import com.jujaga.emr.PatientExport;
import com.jujaga.emr.model.Dxresearch;

public class ProblemsModel {
	//private static Logger log = Logger.getLogger(ProblemsModel.class.getName());

	private Dxresearch problem;
	private SET<II> ids;
	private CD<String> code;
	private ED text;
	private ActStatus statusCode;
	private IVL<TS> effectiveTime;
	private CD<String> value;
	private ArrayList<Author> authors;
	private EntryRelationship secondaryCodeICD9;
	private EntryRelationship diagnosisDate;

	public ProblemsModel(Dxresearch problem) {
		if(problem == null) {
			this.problem = new Dxresearch();
		} else {
			this.problem = problem;
		}

		setIds();
		setCode();
		setText();
		setStatusCode();
		setEffectiveTime();
		setValue();
		setAuthor();
		setSecondaryCodeICD9();
		setDiagnosisDate();
	}

	public String getTextSummary() {
		StringBuilder sb = new StringBuilder();
		String description = new PatientExport().getICD9Description(problem.getDxresearchCode());

		if(!EverestUtils.isNullorEmptyorWhitespace(problem.getDxresearchCode())) {
			sb.append("ICD9: " + problem.getDxresearchCode());
		}
		if(!EverestUtils.isNullorEmptyorWhitespace(description)) {
			sb.append(" - " + description);
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

	public ED getText() {
		return text;
	}

	private void setText() {
		String description = new PatientExport().getICD9Description(problem.getDxresearchCode());
		if(!EverestUtils.isNullorEmptyorWhitespace(description)) {
			this.text = new ED(description);
		} else {
			this.text = null;
		}
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

	public IVL<TS> getEffectiveTime() {
		return effectiveTime;
	}

	private void setEffectiveTime() {
		IVL<TS> ivl = null;
		TS startTime = BodyUtils.buildTSFromDate(problem.getStartDate());
		if(startTime != null) {
			ivl = new IVL<TS>(startTime, null);
		}

		this.effectiveTime = ivl;
	}

	public CD<String> getValue() {
		return value;
	}

	private void setValue() {
		this.value = new CD<String>();
		this.value.setNullFlavor(NullFlavor.Unknown);
	}

	public ArrayList<Author> getAuthor() {
		return authors;
	}

	private void setAuthor() {
		authors = new ArrayList<Author>();
		authors.add(new AuthorParticipationModel(BodyUtils.getDemographicProviderNo(problem.getDemographicNo())).getAuthor(problem.getUpdateDate()));
	}

	public EntryRelationship getSecondaryCodeICD9() {
		return secondaryCodeICD9;
	}

	private void setSecondaryCodeICD9() {
		this.secondaryCodeICD9 = new SecondaryCodeICD9ObservationModel().getEntryRelationship(problem.getDxresearchCode());
	}

	public EntryRelationship getDiagnosisDate() {
		return diagnosisDate;
	}

	private void setDiagnosisDate() {
		this.diagnosisDate = new DateObservationModel().getEntryRelationship(problem.getUpdateDate());
	}
}
