package com.jujaga.e2e.model.export.body;

import org.marc.everest.datatypes.BL;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Consumable;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.model.export.template.ConsumableModel;
import com.jujaga.e2e.util.EverestUtils;
import com.jujaga.emr.PatientExport.Immunization;

public class ImmunizationsModel {
	private Immunization immunization;

	private BL negationInd;
	private SET<II> ids;
	private CD<String> code;
	private Consumable consumable;

	public ImmunizationsModel(Immunization immunization) {
		if(immunization == null) {
			this.immunization = new Immunization(null, null);
		} else {
			this.immunization = immunization;
		}

		setNegationInd();
		setIds();
		setCode();
		setConsumable();
	}

	public String getTextSummary() {
		StringBuilder sb = new StringBuilder();

		if(!EverestUtils.isNullorEmptyorWhitespace(immunization.getPrevention().getPreventionType())) {
			sb.append(immunization.getPrevention().getPreventionType());
		}
		if(immunization.getPrevention().getPreventionDate() != null) {
			sb.append(" " + immunization.getPrevention().getPreventionDate());
		}
		if(immunization.getPrevention().getRefused().equals('1')) {
			sb.append(" Refused");
		} else if(immunization.getPrevention().getRefused().equals('2')) {
			sb.append(" Ineligible");
		} else {
			sb.append(" Completed");
		}

		return sb.toString();
	}

	public BL getNegationInd() {
		return negationInd;
	}

	private void setNegationInd() {
		Boolean isNegated = !(immunization.getPrevention().getRefused().equals('0'));
		this.negationInd = new BL(isNegated);
	}

	public SET<II> getIds() {
		return ids;
	}

	private void setIds() {
		this.ids = EverestUtils.buildUniqueId(Constants.IdPrefixes.Immunizations, immunization.getPrevention().getId());
	}

	public CD<String> getCode() {
		return code;
	}

	private void setCode() {
		this.code = new CD<String>(Constants.SubstanceAdministrationType.IMMUNIZ.toString(), Constants.CodeSystems.ACT_CODE_CODESYSTEM_OID);
		this.code.setCodeSystemName(Constants.CodeSystems.ACT_CODE_CODESYSTEM_NAME);
	}

	public Consumable getConsumable() {
		return consumable;
	}

	private void setConsumable() {
		this.consumable = new ConsumableModel().getConsumable(immunization); //TODO
	}
}
