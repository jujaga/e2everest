package com.jujaga.emr.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.emr.model.Allergy;
import com.jujaga.emr.util.EntityModelUtils;

public class AllergyDaoTest {
	private static ApplicationContext context;
	private static AllergyDao allergyDao;

	@BeforeClass
	public static void beforeClass() {
		context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		allergyDao = context.getBean(AllergyDao.class);
	}

	@Test
	public void drugTest() {
		EntityModelUtils.invokeMethodsForModelClass(new Allergy());
		Allergy entity = (Allergy) EntityModelUtils.generateTestDataForModelClass(new Allergy());
		allergyDao.persist(entity);
		assertNotNull(entity.getId());
	}

	@Test
	public void findAllergiesTest() {
		List<Allergy> allergies = allergyDao.findAllergies(Constants.Runtime.VALID_DEMOGRAPHIC);
		assertNotNull(allergies);
		assertEquals(1, allergies.size());
	}
}
