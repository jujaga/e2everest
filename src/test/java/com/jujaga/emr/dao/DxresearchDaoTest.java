package com.jujaga.emr.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.emr.model.Dxresearch;
import com.jujaga.emr.util.EntityModelUtils;

public class DxresearchDaoTest {
	private static ApplicationContext context;
	private static DxresearchDao dxResearchDao;

	@BeforeClass
	public static void beforeClass() {
		context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		dxResearchDao = context.getBean(DxresearchDao.class);
	}

	@Test
	public void dxresearchTest() {
		EntityModelUtils.invokeMethodsForModelClass(new Dxresearch());
		Dxresearch entity = (Dxresearch) EntityModelUtils.generateTestDataForModelClass(new Dxresearch());
		dxResearchDao.persist(entity);
		assertNotNull(entity.getDxresearchNo());
	}

	@Test
	public void getDxResearchItemsByPatientTest() {
		List<Dxresearch> problems = dxResearchDao.getDxResearchItemsByPatient(Constants.Runtime.VALID_DEMOGRAPHIC);
		assertNotNull(problems);
		assertEquals(2, problems.size());
	}
}
