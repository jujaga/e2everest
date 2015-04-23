package com.jujaga.emr.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.emr.model.PatientLabRouting;
import com.jujaga.emr.util.EntityModelUtils;

public class PatientLabRoutingDaoTest {
	private static ApplicationContext context;
	private static PatientLabRoutingDao patientLabRoutingDao;

	@BeforeClass
	public static void beforeClass() {
		context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		patientLabRoutingDao = context.getBean(PatientLabRoutingDao.class);
	}

	@Test
	public void patientLabRoutingTest() {
		EntityModelUtils.invokeMethodsForModelClass(new PatientLabRouting());
		PatientLabRouting entity = (PatientLabRouting) EntityModelUtils.generateTestDataForModelClass(new PatientLabRouting());
		patientLabRoutingDao.persist(entity);
		assertNotNull(entity.getId());
	}
}
