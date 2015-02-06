package com.jujaga.emr;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jujaga.emr.dao.DemographicDao;
import com.jujaga.emr.model.Demographic;

public class PatientExport {
	private static Logger log = Logger.getLogger(PatientExport.class.getName());
	private static final String SPRING_APPLICATION_CONTEXT = "spring.xml";
	private static ApplicationContext context = null;

	private DemographicDao demographicDao = null;

	private boolean loaded = false;
	private Demographic demographic = null;

	public PatientExport(Integer demographicNo) {
		if(context == null) {
			context = new ClassPathXmlApplicationContext(SPRING_APPLICATION_CONTEXT);
		}
		demographicDao = context.getBean(DemographicDao.class);

		Demographic demo = new Demographic();
		demo.setFirstName("John");
		demo.setLastName("Cleese");
		demo.setHin("448000001");
		demo.setAddress("1234 Street");
		demo.setCity("city");
		demo.setProvince("BC");
		demo.setPostal("V8T1D6");
		demo.setPhone("1234567");
		demo.setPhone2("765-4321");
		demo.setEmail("test@test.com");
		demo.setSex("M");
		demo.setYearOfBirth("1940");
		demo.setMonthOfBirth("09");
		demo.setDateOfBirth("25");
		demo.setOfficialLanguage("English");
		demographicDao.persist(demo);

		loadPatient(demographicNo);
	}

	public boolean loadPatient(Integer demographicNo) {
		demographic = demographicDao.getDemographic(demographicNo);
		if(demographic == null) {
			log.error("Demographic ".concat(demographicNo.toString()).concat(" can't be loaded"));
			return false;
		}

		return true;
	}

	public boolean isLoaded() {
		return loaded;
	}

	public Demographic getDemographic() {
		return demographic;
	}
}
