package com.jujaga.e2e.model.export.body;

import java.util.ArrayList;

import org.marc.everest.datatypes.BL;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Component4;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Observation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ActRelationshipHasComponent;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ActStatus;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActMoodDocumentObservation;

import com.jujaga.e2e.constant.Constants;
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

	// TODO Complete TextSummary
	public String getTextSummary() {
		StringBuilder sb = new StringBuilder();

		if(!EverestUtils.isNullorEmptyorWhitespace(measurement.getType())) {
			sb.append(measurement.getType());
		}
		if(!EverestUtils.isNullorEmptyorWhitespace(measurement.getDataField())) {
			sb.append(": ".concat(measurement.getDataField()));
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

	public ArrayList<Component4> getComponent() {
		ArrayList<Component4> components = new ArrayList<Component4>();
		Component4 component = new Component4(ActRelationshipHasComponent.HasComponent, new BL(true));
		Observation observation = new Observation();

		observation.setMoodCode(x_ActMoodDocumentObservation.Eventoccurrence);
		observation.setId(getComponentIds());
		observation.setCode("testCode");

		component.setClinicalStatement(observation);
		components.add(component);
		return components;
	}

	private SET<II> getComponentIds() {
		return EverestUtils.buildUniqueId(Constants.IdPrefixes.ClinicalMeasuredObservations, measurement.getId());
	}
}
