package com.jujaga.e2e.model.export.body;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.generic.SET;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.util.EverestUtils;
import com.jujaga.emr.dao.DrugDao;
import com.jujaga.emr.model.Drug;

public class MedicationsModelTest {
	private static ApplicationContext context;
	private static DrugDao dao;
	private static Drug drug;
	private static MedicationsModel medicationsModel;

	private static Drug nullDrug;
	private static MedicationsModel nullMedicationsModel;

	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);
		context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		dao = context.getBean(DrugDao.class);
		drug = dao.findByDemographicId(Constants.Runtime.VALID_DEMOGRAPHIC).get(0);
		medicationsModel = new MedicationsModel(drug);

		nullDrug = new Drug();
		nullMedicationsModel = new MedicationsModel(nullDrug);
	}

	@Test
	public void idTest() {
		SET<II> ids = medicationsModel.getIds();
		assertNotNull(ids);

		II id = ids.get(0);
		assertNotNull(id);
		assertEquals(Constants.EMR.EMR_OID, id.getRoot());
		assertEquals(Constants.EMR.EMR_VERSION, id.getAssigningAuthorityName());
		assertFalse(EverestUtils.isNullorEmptyorWhitespace(id.getExtension()));
		assertTrue(id.getExtension().contains(drug.getId().toString()));
	}

	@Test
	public void idNullTest() {
		SET<II> ids = nullMedicationsModel.getIds();
		assertNotNull(ids);
	}

	@Test
	public void codeTest() {
	}

	@Test
	public void codeNullTest() {
	}

	@Test
	public void statusCodeTest() {
	}

	@Test
	public void statusCodeNullTest() {
	}

	@Test
	public void consumableTest() {
	}

	@Test
	public void consumableNullTest() {
	}

	@Test
	public void recordTypeTest() {
	}

	@Test
	public void recordTypeNullTest() {
	}

	@Test
	public void lastReviewDateTest() {
	}

	@Test
	public void lastReviewDateNullTest() {
	}
}
