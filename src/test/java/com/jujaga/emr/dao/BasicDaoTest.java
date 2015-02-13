package com.jujaga.emr.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.emr.model.Clinic;
import com.jujaga.emr.model.Demographic;
import com.jujaga.emr.model.Provider;
import com.jujaga.emr.util.EntityModelUtils;

public class BasicDaoTest {
	private static ApplicationContext context;
	private static ClinicDao clinicDao;
	private static DemographicDao demographicDao;
	private static ProviderDao providerDao;

	@BeforeClass
	public static void beforeClass() {
		context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		clinicDao = context.getBean(ClinicDao.class);
		demographicDao = context.getBean(DemographicDao.class);
		providerDao = context.getBean(ProviderDao.class);
	}

	@Test
	public void clinicTest() {
		EntityModelUtils.invokeMethodsForModelClass(new Clinic());
		Clinic entity = (Clinic) EntityModelUtils.generateTestDataForModelClass(new Clinic());
		clinicDao.persist(entity);
		assertNotNull(entity.getId());
	}

	@Test
	public void demographicTest() {
		EntityModelUtils.invokeMethodsForModelClass(new Demographic());
		Demographic entity = (Demographic) EntityModelUtils.generateTestDataForModelClass(new Demographic());
		demographicDao.persist(entity);
		assertNotNull(entity.getDemographicNo());
	}

	@Test
	public void providerTest() {
		EntityModelUtils.invokeMethodsForModelClass(new Provider());
		Provider entity = (Provider) EntityModelUtils.generateTestDataForModelClass(new Provider());
		providerDao.persist(entity);
		assertNotNull(entity.getProviderNo());
	}
}
