package com.jujaga.e2e.model.export.template;

import java.util.Arrays;
import java.util.Date;

import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.TS;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.AssignedAuthor;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ContextControl;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.model.export.body.BodyUtils;
import com.jujaga.e2e.model.export.header.AuthorModel;

public class AuthorParticipationModel extends AuthorModel {
	public AuthorParticipationModel(String providerNo) {
		super(providerNo);
	}

	public Author getAuthor(Date date) {
		TS time = BodyUtils.buildTSFromDate(date);
		if(time == null) {
			time = new TS();
			time.setNullFlavor(NullFlavor.Unknown);
		}

		Author author = new Author();
		AssignedAuthor assignedAuthor = new AssignedAuthor();

		author.setContextControlCode(ContextControl.OverridingPropagating);
		author.setTemplateId(Arrays.asList(new II(Constants.TemplateOids.AUTHOR_PARTICIPATION_TEMPLATE_ID)));
		author.setTime(time);
		author.setAssignedAuthor(assignedAuthor);

		assignedAuthor.setId(ids);
		assignedAuthor.setAssignedAuthorChoice(person);

		return author;
	}
}
