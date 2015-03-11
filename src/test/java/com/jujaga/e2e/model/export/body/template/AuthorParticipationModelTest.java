package com.jujaga.e2e.model.export.body.template;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.TS;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.AssignedAuthor;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ContextControl;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.model.export.template.AuthorParticipationModel;

public class AuthorParticipationModelTest {
	private static Date date;

	@BeforeClass
	public static void beforeClass() {
		date = new Date();
	}

	@Test
	public void authorParticipationStructureTest() {
		Author author = new AuthorParticipationModel(Constants.Runtime.VALID_PROVIDER.toString()).getAuthor(date);
		assertNotNull(author);
		assertEquals(ContextControl.OverridingPropagating, author.getContextControlCode().getCode());
		assertTrue(author.getTemplateId().contains(new II(Constants.TemplateOids.AUTHOR_PARTICIPATION_TEMPLATE_ID)));

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		TS now = new TS(calendar, TS.DAY);
		assertFalse(author.getTime().isInvalidDate());
		assertTrue(author.getTime().toString().contains(now.toString()));

		AssignedAuthor assignedAuthor = author.getAssignedAuthor();
		assertNotNull(assignedAuthor);
		assertNotNull(assignedAuthor.getAssignedAuthorChoiceIfAssignedPerson());
	}

	@Test
	public void authorParticipationNullTest() {
		Author author = new AuthorParticipationModel(null).getAuthor(null);
		assertNotNull(author);
		assertEquals(ContextControl.OverridingPropagating, author.getContextControlCode().getCode());
		assertTrue(author.getTemplateId().contains(new II(Constants.TemplateOids.AUTHOR_PARTICIPATION_TEMPLATE_ID)));
		assertTrue(author.getTime().isNull());
		assertEquals(NullFlavor.Unknown, author.getTime().getNullFlavor().getCode());

		AssignedAuthor assignedAuthor = author.getAssignedAuthor();
		assertNotNull(assignedAuthor);
		assertNotNull(assignedAuthor.getAssignedAuthorChoiceIfAssignedPerson());
	}
}
