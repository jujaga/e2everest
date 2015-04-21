package com.jujaga.emr;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.constant.Mappings;
import com.jujaga.e2e.util.EverestUtils;
import com.jujaga.emr.dao.DemographicDao;
import com.jujaga.emr.dao.DrugDao;
import com.jujaga.emr.dao.DxresearchDao;
import com.jujaga.emr.model.Demographic;
import com.jujaga.emr.model.Drug;
import com.jujaga.emr.model.Dxresearch;

public class PatientExport {
	private static ApplicationContext context = null;

	private DemographicDao demographicDao = null;
	private DrugDao drugDao = null;
	private DxresearchDao dxResearchDao = null;

	private Boolean loaded = false;
	private Demographic demographic = null;
	private List<Drug> drugs = null;
	private List<Dxresearch> problems = null;

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
		dxResearchDao = context.getBean(DxresearchDao.class);
		loaded = loadPatient(demographicNo);
	}

	private Boolean loadPatient(Integer demographicNo) {
		demographic = demographicDao.find(demographicNo);
		drugs = drugDao.findByDemographicId(demographicNo);
		problems = dxResearchDao.getDxResearchItemsByPatient(demographicNo);
		return demographic != null;
	}

	public Boolean isLoaded() {
		return loaded;
	}

	public ApplicationContext getApplicationContext() {
		return context;
	}

	// TODO Replace this mock with a fully functional icd9Dao DB variant
	public String getICD9Description(String code) {
		if(!EverestUtils.isNullorEmptyorWhitespace(code) && Mappings.icd9Map.containsKey(code)) {
			return Mappings.icd9Map.get(code);
		} else {
			return null;
		}
	}

	public Demographic getDemographic() {
		return demographic;
	}

	public List<Drug> getMedications() {
		return drugs;
	}

	public List<Dxresearch> getProblems() {
		return problems;
	}
}
