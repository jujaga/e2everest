package com.jujaga.e2e.model.export.body;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.marc.everest.datatypes.ANY;
import org.marc.everest.datatypes.BL;
import org.marc.everest.datatypes.ED;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.PQ;
import org.marc.everest.datatypes.ST;
import org.marc.everest.datatypes.TS;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.datatypes.generic.IVL;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Component4;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Observation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ActRelationshipHasComponent;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ActStatus;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActMoodDocumentObservation;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.constant.Mappings;
import com.jujaga.e2e.model.export.template.AuthorParticipationModel;
import com.jujaga.e2e.util.EverestUtils;
import com.jujaga.emr.model.Measurement;

public class ClinicallyMeasuredObservationsModel {
	private Measurement measurement;

	private SET<II> ids;
	private CD<String> code;
	private ActStatus statusCode;
	private ArrayList<Author> authors;

	public ClinicallyMeasuredObservationsModel(Measurement measurement) {
		if(measurement == null) {
			this.measurement = new Measurement();
		} else {
			this.measurement = measurement;
		}

		setIds();
		setCode();
		setStatusCode();
		setAuthor();
	}

	public String getTextSummary() {
		StringBuilder sb = new StringBuilder();

		if(!EverestUtils.isNullorEmptyorWhitespace(EverestUtils.getTypeDescription(measurement.getType()))) {
			sb.append(EverestUtils.getTypeDescription(measurement.getType()));
		}
		if(!EverestUtils.isNullorEmptyorWhitespace(measurement.getDataField())) {
			sb.append(": ".concat(measurement.getDataField()));
		}
		if(!EverestUtils.isNullorEmptyorWhitespace(Mappings.measurementUnitMap.get(measurement.getType()))) {
			sb.append(" ".concat(Mappings.measurementUnitMap.get(measurement.getType())));
		}
		if(!EverestUtils.isNullorEmptyorWhitespace(measurement.getMeasuringInstruction())) {
			sb.append(" (".concat(measurement.getMeasuringInstruction()).concat(")"));
		}
		if(measurement.getDateObserved() != null) {
			sb.append(" ".concat(measurement.getDateObserved().toString()));
		}

		return sb.toString();
	}

	public SET<II> getIds() {
		return ids;
	}

	private void setIds() {
		this.ids = EverestUtils.buildUniqueId(Constants.IdPrefixes.ClinicalMeasuredObservations, measurement.getId());
	}

	public CD<String> getCode() {
		return code;
	}

	private void setCode() {
		this.code = new CD<String>();
		this.code.setNullFlavor(NullFlavor.NoInformation);
	}

	public ActStatus getStatusCode() {
		return statusCode;
	}

	private void setStatusCode() {
		this.statusCode = ActStatus.Completed;
	}

	public ArrayList<Author> getAuthor() {
		return authors;
	}

	private void setAuthor() {
		this.authors = new ArrayList<Author>();
		this.authors.add(new AuthorParticipationModel(measurement.getProviderNo()).getAuthor(measurement.getCreateDate()));
	}

	public ArrayList<Component4> getComponents() {
		ArrayList<Component4> components = new ArrayList<Component4>();
		Component4 component = new Component4(ActRelationshipHasComponent.HasComponent, new BL(true));
		Observation observation = new Observation();

		observation.setMoodCode(x_ActMoodDocumentObservation.Eventoccurrence);
		observation.setId(getComponentIds());
		observation.setCode(getComponentCode());
		observation.setText(getComponentText());
		observation.setEffectiveTime(getComponentTime());
		observation.setValue(getComponentValue());

		component.setClinicalStatement(observation);
		components.add(component);
		return components;
	}

	private SET<II> getComponentIds() {
		return EverestUtils.buildUniqueId(Constants.IdPrefixes.ClinicalMeasuredObservations, measurement.getId());
	}

	public CD<String> getComponentCode() {
		CD<String> code = new CD<String>();
		if(Mappings.measurementCodeMap.get(measurement.getType()) != null) {
			code.setCodeEx(Mappings.measurementCodeMap.get(measurement.getType()));
			code.setCodeSystem(Constants.CodeSystems.LOINC_OID);
			code.setCodeSystemName(Constants.CodeSystems.LOINC_NAME);
			code.setCodeSystemVersion(Constants.CodeSystems.LOINC_VERSION);
		} else {
			code.setNullFlavor(NullFlavor.Unknown);
		}
		return code;
	}

	private ED getComponentText() {
		String text = new String();
		if(!EverestUtils.isNullorEmptyorWhitespace(EverestUtils.getTypeDescription(measurement.getType()))) {
			text = EverestUtils.getTypeDescription(measurement.getType());
		}
		if(!EverestUtils.isNullorEmptyorWhitespace(measurement.getMeasuringInstruction())) {
			text = text.concat(" (").concat(measurement.getMeasuringInstruction().concat(")"));
		}

		if(!text.isEmpty()) {
			return new ED(text);
		}
		return null;
	}

	private IVL<TS> getComponentTime() {
		IVL<TS> ivl = null;
		TS startTime = EverestUtils.buildTSFromDate(measurement.getDateObserved(), TS.SECONDNOTIMEZONE);
		if(startTime != null) {
			ivl = new IVL<TS>(startTime, null);
		}

		return ivl;
	}

	private ANY getComponentValue() {
		String dataField = measurement.getDataField();
		String unit = Mappings.measurementUnitMap.get(measurement.getType());
		ANY value = null;
		if(!EverestUtils.isNullorEmptyorWhitespace(dataField)) {
			if(!EverestUtils.isNullorEmptyorWhitespace(unit)) {
				try {
					value = new PQ(new BigDecimal(dataField), unit.replaceAll("\\s","_"));
				} catch (NumberFormatException e) {
					value = new ST(dataField.concat(" ").concat(unit));
				}
			} else {
				value = new ST(dataField);
			}
		}

		return value;
	}
}
