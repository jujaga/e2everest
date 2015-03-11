package com.jujaga.emr;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.emr.dao.DemographicDao;
import com.jujaga.emr.dao.DrugDao;
import com.jujaga.emr.model.Demographic;
import com.jujaga.emr.model.Drug;

public class PatientExport {
	private static ApplicationContext context = null;

	private DemographicDao demographicDao = null;
	private DrugDao drugDao = null;

	private Boolean loaded = false;
	private Demographic demographic = null;
	private List<Drug> drugs = null;

	public PatientExport() {
		if(context == null) {
			context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		}
	}

	public PatientExport(Integer demographicNo) {
		if(context == null) {
			context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		}
		demographicDao = context.getBean(DemographicDao.class);
		drugDao = context.getBean(DrugDao.class);
		loaded = loadPatient(demographicNo);
	}

	private Boolean loadPatient(Integer demographicNo) {
		demographic = demographicDao.find(demographicNo);
		drugs = drugDao.findByDemographicId(demographicNo);
		return demographic != null;
	}

	public Boolean isLoaded() {
		return loaded;
	}

	public ApplicationContext getApplicationContext() {
		return context;
	}

	public Demographic getDemographic() {
		return demographic;
	}

	public List<Drug> getMedications() {
		return drugs;
	}
}
