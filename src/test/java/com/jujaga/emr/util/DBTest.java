package com.jujaga.emr.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.jujaga.emr.model.Demographic;

// TODO Add import.sql for populating test patient 
public class DBTest {
	static private EntityManagerFactory factory;
	private EntityManager manager;

	@BeforeClass
	public static void beforeClass() {
		/* Hotfix for deprecated javax.persistence.spi.PersistenceProvider
		PersistenceProviderResolverHolder.setPersistenceProviderResolver(new PersistenceProviderResolver() {
			private final List<PersistenceProvider> providers_ = Arrays.asList((PersistenceProvider) new HibernatePersistenceProvider());

			@Override
			public List<PersistenceProvider> getPersistenceProviders() {
				return providers_;
			}

			@Override
			public void clearCachedProviders() {
			}
		});*/
		factory = Persistence.createEntityManagerFactory("sqlite-jpa");
	}

	@Before
	public void before() {
		manager = factory.createEntityManager();
		manager.getTransaction().begin();
	}

	@After
	public void closeEntityManager() {
		manager.close();
	}
	@AfterClass
	public static void closeEntityManagerFactory() {
		factory.close();
	}

	@Test
	public void persistTest() {
		String test = "test";

		Demographic demographic = new Demographic();
		demographic.setFirstName(test);
		manager.persist(demographic);
		manager.getTransaction().commit();

		Demographic demoTest = manager.find(Demographic.class, demographic.getDemographicNo());
		assertEquals(test, demoTest.getFirstName());
	}

	@Test
	public void updateTest() {
		String test = "test";

		Demographic demographic = new Demographic();
		demographic.setFirstName("oldtest");
		manager.persist(demographic);

		Demographic demoTest = manager.find(Demographic.class, demographic.getDemographicNo());
		demoTest.setFirstName(test);
		manager.merge(demoTest);
		manager.getTransaction().commit();

		Demographic demoResult = manager.find(Demographic.class, demoTest.getDemographicNo());
		assertEquals(test, demoResult.getFirstName());
	}

	@Test
	public void findByIdTest(){
		Integer demographicNo = 1;

		Demographic demoTest = manager.find(Demographic.class, demographicNo);
		assertEquals(demographicNo, demoTest.getDemographicNo());
	}

	@Ignore
	@Test
	public void testRemove(){
		Integer demographicNo = 1;

		Demographic demographic = manager.find(Demographic.class, demographicNo);
		manager.remove(demographic);
		manager.getTransaction().commit();

		Demographic demoTest = manager.find(Demographic.class, demographicNo);
		assertNull(demoTest);
	}
}
