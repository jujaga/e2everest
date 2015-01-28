package com.jujaga.e2e.populator.header;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.TS;
import org.marc.everest.datatypes.generic.CE;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.AssignedAuthor;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;

import com.jujaga.e2e.StubRecord;
import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.populator.EmrExportPopulator;
import com.jujaga.e2e.populator.Populator;

public class AuthorPopulatorTest {
	private static ClinicalDocument clinicalDocument;

	@BeforeClass
	public static void beforeClass() {
		Integer demographicNo = StubRecord.Demographic.demographicNo;
		CE<String> code = Constants.EMRConversionDocument.CODE;
		II templateId = new II(Constants.EMRConversionDocument.TEMPLATE_ID);

		Populator populator = new EmrExportPopulator(demographicNo, code, templateId);
		populator.populate();
		clinicalDocument = populator.getClinicalDocument();
	}

	@Test
	public void authorTest() {
		ArrayList<Author> authors = clinicalDocument.getAuthor();
		assertNotNull(authors);
		assertEquals(2, authors.size());
	}

	@Test
	public void authorProviderTest() {
		Author author = clinicalDocument.getAuthor().get(0);
		assertNotNull(author);
		assertEquals(new TS(StubRecord.Demographic.docCreated, TS.DAY), author.getTime());

		AssignedAuthor assignedAuthor = author.getAssignedAuthor();
		assertNotNull(assignedAuthor);
		assertNotNull(assignedAuthor.getAssignedAuthorChoiceIfAssignedPerson());
	}

	@Test
	public void authorSystemTest() {
		Author author = clinicalDocument.getAuthor().get(1);
		assertNotNull(author);
		assertEquals(new TS(StubRecord.Demographic.docCreated, TS.DAY), author.getTime());

		AssignedAuthor assignedAuthor = author.getAssignedAuthor();
		assertNotNull(assignedAuthor);
		assertNotNull(assignedAuthor.getAssignedAuthorChoiceIfAssignedAuthoringDevice());
	}
}
