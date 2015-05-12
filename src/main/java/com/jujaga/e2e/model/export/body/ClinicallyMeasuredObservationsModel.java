package com.jujaga.e2e.model.export.body;

import java.util.ArrayList;

import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.model.export.template.AuthorParticipationModel;
import com.jujaga.e2e.util.EverestUtils;
import com.jujaga.emr.model.Measurement;

public class ClinicallyMeasuredObservationsModel {
	private Measurement measurement;

	private SET<II> ids;
	private CD<String> code;
	private ArrayList<Author> authors;

	public ClinicallyMeasuredObservationsModel(Measurement measurement) {
		if(measurement == null) {
			this.measurement = new Measurement();
		} else {
			this.measurement = measurement;
		}

		setIds();
		setCode();
		setAuthor();
	}

	public String getTextSummary() {
		StringBuilder sb = new StringBuilder();

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

	public ArrayList<Author> getAuthor() {
		return authors;
	}

	private void setAuthor() {
		this.authors = new ArrayList<Author>();
		this.authors.add(new AuthorParticipationModel(measurement.getProviderNo()).getAuthor(measurement.getCreateDate()));
	}
}
