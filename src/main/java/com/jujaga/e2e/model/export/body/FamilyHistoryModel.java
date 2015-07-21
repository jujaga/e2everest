package com.jujaga.e2e.model.export.body;

import org.marc.everest.datatypes.ED;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.TS;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.datatypes.generic.IVL;
import org.marc.everest.datatypes.generic.SET;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.util.EverestUtils;
import com.jujaga.emr.PatientExport.FamilyHistoryEntry;

public class FamilyHistoryModel {
	private FamilyHistoryEntry familyHistory;

	private SET<II> ids;
	private CD<String> code;
	private ED text;
	private IVL<TS> effectiveTime;
	private CD<String> value;

	public FamilyHistoryModel(FamilyHistoryEntry familyHistoryEntry) {
		if(familyHistoryEntry == null) {
			this.familyHistory = new FamilyHistoryEntry(null, null);
		} else {
			this.familyHistory = familyHistoryEntry;
		}

		setIds();
		setCode();
		setText();
		setEffectiveTime();
		setValue();
		familyHistory.getExtMap(); // Temporary coverage line
	}

	public String getTextSummary() {
		StringBuilder sb = new StringBuilder();

		if(familyHistory.getFamilyHistory().getObservation_date() != null) {
			sb.append(familyHistory.getFamilyHistory().getObservation_date());
		}
		if(!EverestUtils.isNullorEmptyorWhitespace(familyHistory.getFamilyHistory().getNote())) {
			sb.append(" ".concat(familyHistory.getFamilyHistory().getNote().replaceAll("\\\\n", "\n")));
		}

		return sb.toString();
	}

	public SET<II> getIds() {
		return ids;
	}

	private void setIds() {
		this.ids = EverestUtils.buildUniqueId(Constants.IdPrefixes.FamilyHistory, familyHistory.getFamilyHistory().getId());
	}

	public CD<String> getCode() {
		return code;
	}

	public void setCode() {
		this.code = new CD<String>();
		this.code.setNullFlavor(NullFlavor.NoInformation);
	}

	public ED getText() {
		return text;
	}

	private void setText() {
		ED text = null;
		if(!EverestUtils.isNullorEmptyorWhitespace(familyHistory.getFamilyHistory().getNote())) {
			text = new ED(familyHistory.getFamilyHistory().getNote());
		}
		this.text = text;
	}

	public IVL<TS> getEffectiveTime() {
		return effectiveTime;
	}

	private void setEffectiveTime() {
		IVL<TS> ivl = new IVL<TS>();
		TS startTime = EverestUtils.buildTSFromDate(familyHistory.getFamilyHistory().getObservation_date());
		if(startTime != null) {
			ivl.setLow(startTime);
		} else {
			ivl.setNullFlavor(NullFlavor.NoInformation);
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
}
