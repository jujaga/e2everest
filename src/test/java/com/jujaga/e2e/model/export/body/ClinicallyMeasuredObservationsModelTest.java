package com.jujaga.e2e.model.export.body;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.util.EverestUtils;
import com.jujaga.emr.dao.MeasurementDao;
import com.jujaga.emr.model.Measurement;

public class ClinicallyMeasuredObservationsModelTest {
	private static ApplicationContext context;
	private static MeasurementDao dao;
	private static Measurement measurement;
	private static ClinicallyMeasuredObservationsModel cmoModel;

	private static Measurement nullMeasurement;
	private static ClinicallyMeasuredObservationsModel nullCmoModel;

	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);
		context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		dao = context.getBean(MeasurementDao.class);
		measurement = dao.find(Constants.Runtime.VALID_MEASUREMENT);
		cmoModel = new ClinicallyMeasuredObservationsModel(measurement);

		nullMeasurement = new Measurement();
		nullCmoModel = new ClinicallyMeasuredObservationsModel(nullMeasurement);
	}

	@Test
	public void clinicallyMeasuredObservationsModelNullTest() {
		assertNotNull(new ClinicallyMeasuredObservationsModel(null));
	}

	@Test
	public void textSummaryTest() {
		String text = cmoModel.getTextSummary();
		assertNotNull(text);
	}

	@Test
	public void textSummaryNullTest() {
		String text = nullCmoModel.getTextSummary();
		assertNotNull(text);
	}

	@Test
	public void idTest() {
		SET<II> ids = cmoModel.getIds();
		assertNotNull(ids);

		II id = ids.get(0);
		assertNotNull(id);
		assertEquals(Constants.EMR.EMR_OID, id.getRoot());
		assertEquals(Constants.EMR.EMR_VERSION, id.getAssigningAuthorityName());
		assertFalse(EverestUtils.isNullorEmptyorWhitespace(id.getExtension()));
		assertTrue(id.getExtension().contains(Constants.IdPrefixes.ClinicalMeasuredObservations.toString()));
		assertTrue(id.getExtension().contains(Constants.Runtime.VALID_MEASUREMENT.toString()));
	}

	@Test
	public void idNullTest() {
		SET<II> ids = nullCmoModel.getIds();
		assertNotNull(ids);
	}

	@Test
	public void codeTest() {
		CD<String> code = cmoModel.getCode();
		assertNotNull(code);
		assertTrue(code.isNull());
		assertEquals(NullFlavor.NoInformation, code.getNullFlavor().getCode());
	}

	@Test
	public void codeNullTest() {
		CD<String> code = nullCmoModel.getCode();
		assertNotNull(code);
		assertTrue(code.isNull());
		assertEquals(NullFlavor.NoInformation, code.getNullFlavor().getCode());
	}

	@Test
	public void authorTest() {
		ArrayList<Author> authors = cmoModel.getAuthor();
		assertNotNull(authors);
		assertEquals(1, authors.size());
	}

	@Test
	public void authorNullTest() {
		ArrayList<Author> authors = nullCmoModel.getAuthor();
		assertNotNull(authors);
		assertEquals(1, authors.size());
	}
}
