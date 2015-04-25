package com.jujaga.emr.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.emr.model.Hl7TextInfo;
import com.jujaga.emr.util.EntityModelUtils;

public class Hl7TextInfoDaoTest {
	private static ApplicationContext context;
	private static Hl7TextInfoDao hl7TextInfoDao;

	@BeforeClass
	public static void beforeClass() {
		context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		hl7TextInfoDao = context.getBean(Hl7TextInfoDao.class);
	}

	@Test
	public void hl7TextInfoTest() {
		EntityModelUtils.invokeMethodsForModelClass(new Hl7TextInfo());
		Hl7TextInfo entity = (Hl7TextInfo) EntityModelUtils.generateTestDataForModelClass(new Hl7TextInfo());
		hl7TextInfoDao.persist(entity);
		assertNotNull(entity.getId());
	}

	@Test
	public void findLabIdTest() {
		Hl7TextInfo hl7TextInfo = hl7TextInfoDao.findLabId(Constants.Runtime.VALID_LAB_NO);
		assertNotNull(hl7TextInfo);

		Integer invalidLabNo = 0;
		Hl7TextInfo hl7TextInfo2 = hl7TextInfoDao.findLabId(invalidLabNo);
		assertNull(hl7TextInfo2);
	}
}
