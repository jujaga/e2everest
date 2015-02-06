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
		loaded = loadPatient(demographicNo);
	}

	public boolean loadPatient(Integer demographicNo) {
		demographic = demographicDao.find(demographicNo);
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
