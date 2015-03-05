package com.jujaga.e2e.model.export.body.template;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.model.export.template.MedicationPrescriptionEventModel;
import com.jujaga.emr.dao.DrugDao;
import com.jujaga.emr.model.Drug;

public class MedicationPrescriptionEventModelTest {
	private static ApplicationContext context;
	private static DrugDao dao;
	private static Drug drug;
	private static MedicationPrescriptionEventModel mpeModel;

	@BeforeClass
	public static void beforeClass() {
		context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		dao = context.getBean(DrugDao.class);
		mpeModel = new MedicationPrescriptionEventModel();
	}

	@Before
	public void before() {
		drug = dao.findByDemographicId(Constants.Runtime.VALID_DEMOGRAPHIC).get(0);
	}

	@Test
	public void medicationPrescriptionEventStructureTest() {
		EntryRelationship entryRelationship = mpeModel.getEntryRelationship(drug);
		assertNotNull(entryRelationship);
	}
}
