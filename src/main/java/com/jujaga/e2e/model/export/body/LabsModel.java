package com.jujaga.e2e.model.export.body;

import java.util.ArrayList;

import org.marc.everest.datatypes.ED;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.model.export.template.AuthorParticipationModel;
import com.jujaga.e2e.util.EverestUtils;
import com.jujaga.emr.PatientExport.Lab;
import com.jujaga.emr.model.Hl7TextInfo;

public class LabsModel {
	private Lab lab;
	private Hl7TextInfo hl7TextInfo;

	private SET<II> ids;
	private CD<String> code;
	private ED text;
	private ArrayList<Author> authors;
	private ArrayList<EntryRelationship> resultOrganizers;

	public LabsModel(Lab lab) {
		if(lab == null) {
			this.lab = new Lab(new Hl7TextInfo());
		} else {
			this.lab = lab;
		}
		this.hl7TextInfo = this.lab.getHl7TextInfo();

		setIds();
		setCode();
		setText();
		setAuthor();
		setResultOrganizers();
	}

	public String getTextSummary() {
		StringBuilder sb = new StringBuilder();

		if(!EverestUtils.isNullorEmptyorWhitespace(hl7TextInfo.getDiscipline())) {
			sb.append(hl7TextInfo.getDiscipline());
		}

		return sb.toString();
	}

	public SET<II> getIds() {
		return ids;
	}

	private void setIds() {
		this.ids = BodyUtils.buildUniqueId(Constants.IdPrefixes.Lab, hl7TextInfo.getId());
	}

	public CD<String> getCode() {
		return code;
	}

	private void setCode() {
		this.code = new CD<String>();
		this.code.setNullFlavor(NullFlavor.NoInformation);
	}

	public ED getText() {
		return text;
	}

	private void setText() {
		if(!EverestUtils.isNullorEmptyorWhitespace(hl7TextInfo.getDiscipline())) {
			this.text = new ED(lab.getHl7TextInfo().getDiscipline());
		} else {
			this.text = null;
		}
	}

	public ArrayList<Author> getAuthor() {
		return authors;
	}

	private void setAuthor() {
		authors = new ArrayList<Author>();
		authors.add(new AuthorParticipationModel().getAuthor(BodyUtils.stringToDate(hl7TextInfo.getObrDate()), hl7TextInfo.getRequestingProvider()));
	}

	public ArrayList<EntryRelationship> getResultOrganizers() {
		return resultOrganizers;
	}

	private void setResultOrganizers() {
		this.resultOrganizers = new ArrayList<EntryRelationship>();
		// TODO Loop through ResultOrganizers via LabOrganizerModel
	}
}
