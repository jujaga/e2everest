package com.jujaga.emr.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.emr.model.Measurement;
import com.jujaga.emr.util.EntityModelUtils;

public class MeasurementDaoTest {
	private static ApplicationContext context;
	private static MeasurementDao measurementDao;

	@BeforeClass
	public static void beforeClass() {
		context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		measurementDao = context.getBean(MeasurementDao.class);
	}

	@Test
	public void measurementTest() {
		EntityModelUtils.invokeMethodsForModelClass(new Measurement());
		Measurement entity = (Measurement) EntityModelUtils.generateTestDataForModelClass(new Measurement());
		measurementDao.persist(entity);
		assertNotNull(entity.getId());
	}

	@Test
	public void findByDemographicNoTest() {
		List<Measurement> measurements = measurementDao.findByDemographicNo(Constants.Runtime.VALID_DEMOGRAPHIC);
		assertNotNull(measurements);
		assertEquals(4, measurements.size());
	}
}
