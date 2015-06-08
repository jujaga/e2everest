package com.jujaga.emr.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.emr.model.PreventionExt;
import com.jujaga.emr.util.EntityModelUtils;

public class PreventionExtDaoTest {
	private static ApplicationContext context;
	private static PreventionExtDao preventionExtDao;

	@BeforeClass
	public static void beforeClass() {
		context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		preventionExtDao = context.getBean(PreventionExtDao.class);
	}

	@Test
	public void preventionExtTest() {
		EntityModelUtils.invokeMethodsForModelClass(new PreventionExt());
		PreventionExt entity = (PreventionExt) EntityModelUtils.generateTestDataForModelClass(new PreventionExt());
		preventionExtDao.persist(entity);
		assertNotNull(entity.getId());
	}

	@Test
	public void findByPreventionIdTest() {
		List<PreventionExt> preventionExts = preventionExtDao.findByPreventionId(Constants.Runtime.VALID_PREVENTION);
		assertNotNull(preventionExts);
		assertEquals(8, preventionExts.size());
	}
}
