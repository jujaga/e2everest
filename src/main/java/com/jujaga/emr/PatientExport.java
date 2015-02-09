package com.jujaga.emr;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.emr.dao.DemographicDao;
import com.jujaga.emr.model.Demographic;

public class PatientExport {
	private static ApplicationContext context = null;

	private DemographicDao demographicDao = null;

	private boolean loaded = false;
	private Demographic demographic = null;

	public PatientExport(Integer demographicNo) {
		if(context == null) {
			context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		}
		demographicDao = context.getBean(DemographicDao.class);
		loaded = loadPatient(demographicNo);
	}

	private boolean loadPatient(Integer demographicNo) {
		demographic = demographicDao.find(demographicNo);
		return demographic != null;
	}

	public boolean isLoaded() {
		return loaded;
	}

	public ApplicationContext getApplicationContext() {
		return context;
	}

	public Demographic getDemographic() {
		return demographic;
	}
}
