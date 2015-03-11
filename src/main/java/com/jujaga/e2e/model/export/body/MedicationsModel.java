package com.jujaga.e2e.model.export.body;

import java.util.Date;

import org.apache.log4j.Logger;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Consumable;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ActStatus;

import com.jujaga.e2e.constant.BodyConstants.Medications;
import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.model.export.template.ConsumableModel;
import com.jujaga.e2e.model.export.template.MedicationPrescriptionEventModel;
import com.jujaga.e2e.model.export.template.observation.DateObservationModel;
import com.jujaga.e2e.model.export.template.observation.UnboundObservationModel;
import com.jujaga.e2e.util.EverestUtils;
import com.jujaga.emr.model.Drug;

public class MedicationsModel {
	private static Logger log = Logger.getLogger(MedicationsModel.class.getName());

	private Drug drug;
	private SET<II> ids;
	private CD<String> code;
	private ActStatus statusCode;
	private Consumable consumable;
	private EntryRelationship recordType;
	private EntryRelationship lastReviewDate;
	private EntryRelationship prescriptionInformation;

	public MedicationsModel(Drug drug) {
		if(drug == null) {
			this.drug = new Drug();
		} else {
			this.drug = drug;
		}

		setIds();
		setCode();
		setStatusCode();
		setConsumable();
		setRecordType();
		setLastReviewDate();
		setPrescriptionInformation();
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

	public String getTextSummary() {
		StringBuilder sb = new StringBuilder();

		if(!EverestUtils.isNullorEmptyorWhitespace(drug.getGenericName())) {
			sb.append(drug.getGenericName());
		} else {
			sb.append(drug.getBrandName());
		}

		if(!EverestUtils.isNullorEmptyorWhitespace(drug.getDosage())) {
			sb.append(" " + drug.getDosage());
		}
		if(!EverestUtils.isNullorEmptyorWhitespace(drug.getFreqCode())) {
			sb.append(" " + drug.getFreqCode());
		}
		if(!EverestUtils.isNullorEmptyorWhitespace(drug.getDuration())) {
			sb.append(" " + drug.getDuration());
		}
		if(!EverestUtils.isNullorEmptyorWhitespace(drug.getDurUnit())) {
			sb.append(" " + drug.getDurUnit());
		}

		return sb.toString();
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
		this.code.setCodeSystemName(Constants.CodeSystems.ACT_CODE_CODESYSTEM_NAME);
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

	public Consumable getConsumable() {
		return consumable;
	}

	private void setConsumable() {
		this.consumable = new ConsumableModel().getConsumable(drug);
	}

	public EntryRelationship getRecordType() {
		return recordType;
	}

	private void setRecordType() {
		String value;
		if(drug.getLongTerm()) {
			value = Medications.LONG_TERM;
		} else {
			value = Medications.SHORT_TERM;
		}
		this.recordType = new UnboundObservationModel().getEntryRelationship(value);
	}

	public EntryRelationship getLastReviewDate() {
		return lastReviewDate;
	}

	private void setLastReviewDate() {
		this.lastReviewDate = new DateObservationModel().getEntryRelationship(drug.getLastUpdateDate());
	}

	public EntryRelationship getPrescriptionInformation() {
		return prescriptionInformation;
	}

	private void setPrescriptionInformation() {
		this.prescriptionInformation = new MedicationPrescriptionEventModel().getEntryRelationship(drug);
	}
}
