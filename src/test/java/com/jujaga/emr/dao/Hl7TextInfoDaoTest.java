package com.jujaga.emr.dao;

import static org.junit.Assert.assertNotNull;

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
}
