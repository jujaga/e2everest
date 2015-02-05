package com.jujaga.emr;

import org.apache.log4j.Logger;

import com.jujaga.emr.model.Demographic;

public class PatientExport {
	private static Logger log = Logger.getLogger(PatientExport.class.getName());
	private GeneralDao dao = new GeneralDao();

	private boolean loaded = false;
	private Demographic demographic = null;

	public PatientExport(Integer demographicNo) {
		loadPatient(demographicNo);
	}

	public boolean loadPatient(Integer demographicNo) {
		demographic = dao.getDemographic(demographicNo);
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
