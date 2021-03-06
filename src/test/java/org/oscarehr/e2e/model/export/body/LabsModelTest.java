package org.oscarehr.e2e.model.export.body;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.datatypes.ED;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.oscarehr.common.dao.Hl7TextInfoDao;
import org.oscarehr.common.model.Hl7TextInfo;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.model.PatientExport.Lab;
import org.oscarehr.e2e.model.PatientExport.LabOrganizer;
import org.oscarehr.e2e.model.export.body.LabsModel;
import org.oscarehr.e2e.util.EverestUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LabsModelTest {
	private static ApplicationContext context;
	private static Hl7TextInfoDao dao;
	private static Hl7TextInfo hl7TextInfo;
	private static LabsModel labsModel;

	private static Hl7TextInfo nullHl7TextInfo;
	private static LabsModel nullLabsModel;

	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);
		context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		dao = context.getBean(Hl7TextInfoDao.class);
		hl7TextInfo = dao.findLabId(Constants.Runtime.VALID_LAB_NO);
		Lab lab = new Lab(hl7TextInfo);
		lab.getLabOrganizer().add(new LabOrganizer(Constants.Runtime.INVALID_VALUE, null));
		lab.getLabOrganizer().add(new LabOrganizer(Constants.Runtime.INVALID_VALUE, null));
		labsModel = new LabsModel(lab);

		nullHl7TextInfo = new Hl7TextInfo();
		Lab nullLab = new Lab(nullHl7TextInfo);
		nullLabsModel = new LabsModel(nullLab);
	}

	@Test
	public void labsModelNullTest() {
		assertNotNull(new LabsModel(null));
	}

	@Test
	public void textSummaryTest() {
		String text = labsModel.getTextSummary();
		assertNotNull(text);
	}

	@Test
	public void textSummaryNullTest() {
		String text = nullLabsModel.getTextSummary();
		assertNotNull(text);
	}

	@Test
	public void idTest() {
		SET<II> ids = labsModel.getIds();
		assertNotNull(ids);

		II id = ids.get(0);
		assertNotNull(id);
		assertEquals(Constants.EMR.EMR_OID, id.getRoot());
		assertEquals(Constants.EMR.EMR_VERSION, id.getAssigningAuthorityName());
		assertFalse(EverestUtils.isNullorEmptyorWhitespace(id.getExtension()));
		assertTrue(id.getExtension().contains(Constants.IdPrefixes.Lab.toString()));
		assertTrue(id.getExtension().contains(Constants.Runtime.VALID_LAB_NO.toString()));
	}

	@Test
	public void idNullTest() {
		SET<II> ids = nullLabsModel.getIds();
		assertNotNull(ids);
	}

	@Test
	public void codeTest() {
		CD<String> code = labsModel.getCode();
		assertNotNull(code);
		assertTrue(code.isNull());
		assertEquals(NullFlavor.NoInformation, code.getNullFlavor().getCode());
	}

	@Test
	public void codeNullTest() {
		CD<String> code = nullLabsModel.getCode();
		assertNotNull(code);
		assertTrue(code.isNull());
		assertEquals(NullFlavor.NoInformation, code.getNullFlavor().getCode());
	}

	@Test
	public void textTest() {
		ED text = labsModel.getText();
		assertNotNull(text);
		assertEquals(hl7TextInfo.getDiscipline(), new String(text.getData()));
	}

	@Test
	public void textNullTest() {
		ED text = nullLabsModel.getText();
		assertNull(text);
	}

	@Test
	public void authorTest() {
		ArrayList<Author> authors = labsModel.getAuthor();
		assertNotNull(authors);
		assertEquals(1, authors.size());
	}

	@Test
	public void authorNullTest() {
		ArrayList<Author> authors = nullLabsModel.getAuthor();
		assertNotNull(authors);
		assertEquals(1, authors.size());
	}

	@Test
	public void resultOrganizersTest() {
		ArrayList<EntryRelationship> resultOrganizers = labsModel.getResultOrganizers();
		assertNotNull(resultOrganizers);
		assertEquals(2, resultOrganizers.size());
	}

	@Test
	public void resultOrganizersNullTest() {
		ArrayList<EntryRelationship> resultOrganizers = nullLabsModel.getResultOrganizers();
		assertNotNull(resultOrganizers);
		assertEquals(1, resultOrganizers.size());
	}
}
