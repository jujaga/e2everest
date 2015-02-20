package com.jujaga.e2e.model.export.body;

import java.util.Date;

import org.apache.log4j.Logger;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ActStatus;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.emr.model.Drug;

public class MedicationsModel {
	private static Logger log = Logger.getLogger(MedicationsModel.class.getName());

	private Drug drug;
	private SET<II> ids;
	private CD<String> code;
	private ActStatus statusCode;

	public MedicationsModel(Drug drug) {
		this.drug = drug;

		setIds();
		setCode();
		setStatusCode();
	}

	private boolean isActiveDrug(Date date) {
		try {
			if(new Date().before(date)) {
				return true;
			}
		} catch (NullPointerException e) {
			log.warn("Date is null");
		}
		return false;
	}

	public SET<II> getIds() {
		return ids;
	}

	private void setIds() {
		this.ids = BodyUtils.buildUniqueId(Constants.IdPrefixes.Medications, drug.getId());
	}

	public CD<String> getCode() {
		return code;
	}

	private void setCode() {
		this.code = new CD<String>(Constants.SubstanceAdministrationType.DRUG.toString(), Constants.CodeSystems.ACT_CODE_CODESYSTEM_OID);
		this.code.setCodeSystemName(Constants.CodeSystems.ACT_CODE_DISPLAY_NAME);
	}

	public ActStatus getStatusCode() {
		return statusCode;
	}

	private void setStatusCode() {
		if(drug.getLongTerm() || isActiveDrug(drug.getEndDate())) {
			this.statusCode = ActStatus.Active;
		} else {
			this.statusCode = ActStatus.Completed;
		}
	}
}
