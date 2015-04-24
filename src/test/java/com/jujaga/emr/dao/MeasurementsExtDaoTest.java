package com.jujaga.emr.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.emr.model.MeasurementsExt;
import com.jujaga.emr.util.EntityModelUtils;

public class MeasurementsExtDaoTest {
	private static ApplicationContext context;
	private static MeasurementsExtDao measurementsExtDao;

	@BeforeClass
	public static void beforeClass() {
		context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		measurementsExtDao = context.getBean(MeasurementsExtDao.class);
	}

	@Test
	public void measurementsExtTest() {
		EntityModelUtils.invokeMethodsForModelClass(new MeasurementsExt());
		MeasurementsExt entity = (MeasurementsExt) EntityModelUtils.generateTestDataForModelClass(new MeasurementsExt());
		measurementsExtDao.persist(entity);
		assertNotNull(entity.getId());
	}
}
