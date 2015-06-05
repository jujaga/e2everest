package com.jujaga.emr.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.emr.model.Prevention;
import com.jujaga.emr.util.EntityModelUtils;

public class PreventionDaoTest {
	private static ApplicationContext context;
	private static PreventionDao preventionDao;

	@BeforeClass
	public static void beforeClass() {
		context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		preventionDao = context.getBean(PreventionDao.class);
	}

	@Test
	public void measurementTest() {
		EntityModelUtils.invokeMethodsForModelClass(new Prevention());
		Prevention entity = (Prevention) EntityModelUtils.generateTestDataForModelClass(new Prevention());
		preventionDao.persist(entity);
		assertNotNull(entity.getId());
	}

	@Test
	public void findNotDeletedByDemographicIdTest() {
		List<Prevention> preventions = preventionDao.findNotDeletedByDemographicId(Constants.Runtime.VALID_DEMOGRAPHIC);
		assertNotNull(preventions);
		assertEquals(3, preventions.size());
	}
}
