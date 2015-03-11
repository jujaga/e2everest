package com.jujaga.e2e.populator.header;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import org.marc.everest.datatypes.TS;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.AssignedAuthor;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ContextControl;

import com.jujaga.e2e.model.export.header.AuthorModel;
import com.jujaga.e2e.populator.AbstractPopulator;
import com.jujaga.emr.PatientExport;
import com.jujaga.emr.dao.ProviderDao;
import com.jujaga.emr.model.Provider;

class AuthorPopulator extends AbstractPopulator {
	private final AuthorModel authorModel;

	AuthorPopulator(PatientExport patientExport) {
		ProviderDao providerDao = PatientExport.getApplicationContext().getBean(ProviderDao.class);
		Provider provider = providerDao.find(Integer.parseInt(patientExport.getDemographic().getProviderNo()));
		authorModel = new AuthorModel(provider);
	}

	@Override
	public void populate() {
		ArrayList<Author> authors = new ArrayList<Author>();
		Author provider = new Author();
		AssignedAuthor assignedAuthor = new AssignedAuthor();

		provider.setContextControlCode(ContextControl.OverridingPropagating);
		provider.setTime(new GregorianCalendar(), TS.DAY);
		provider.setAssignedAuthor(assignedAuthor);

		assignedAuthor.setId(authorModel.getIds());
		assignedAuthor.setTelecom(authorModel.getTelecoms());
		assignedAuthor.setAssignedAuthorChoice(authorModel.getPerson());
		authors.add(provider);

		Author system = new Author();
		AssignedAuthor assignedSystem = new AssignedAuthor();

		system.setContextControlCode(ContextControl.OverridingPropagating);
		system.setTime(new GregorianCalendar(), TS.DAY);
		system.setAssignedAuthor(assignedSystem);

		assignedSystem.setId(authorModel.getIds());
		assignedSystem.setAssignedAuthorChoice(authorModel.getDevice());
		authors.add(system);

		clinicalDocument.setAuthor(authors);
	}
}
