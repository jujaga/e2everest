package com.jujaga.emr;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.constant.Mappings;
import com.jujaga.e2e.util.EverestUtils;
import com.jujaga.emr.dao.DemographicDao;
import com.jujaga.emr.dao.DrugDao;
import com.jujaga.emr.dao.DxresearchDao;
import com.jujaga.emr.dao.Hl7TextInfoDao;
import com.jujaga.emr.dao.MeasurementDao;
import com.jujaga.emr.dao.MeasurementsExtDao;
import com.jujaga.emr.dao.PatientLabRoutingDao;
import com.jujaga.emr.model.Demographic;
import com.jujaga.emr.model.Drug;
import com.jujaga.emr.model.Dxresearch;
import com.jujaga.emr.model.Hl7TextInfo;
import com.jujaga.emr.model.Measurement;
import com.jujaga.emr.model.MeasurementsExt;
import com.jujaga.emr.model.PatientLabRouting;

public class PatientExport {
	private static Logger log = Logger.getLogger(PatientExport.class.getName());
	private static ApplicationContext context = null;

	private DemographicDao demographicDao = null;
	private DrugDao drugDao = null;
	private DxresearchDao dxResearchDao = null;
	private PatientLabRoutingDao patientLabRoutingDao = null;
	private Hl7TextInfoDao hl7TextInfoDao = null;
	private MeasurementDao measurementDao = null;
	private MeasurementsExtDao measurementsExtDao = null;

	private Boolean loaded = false;
	private Demographic demographic = null;
	private List<Drug> drugs = null;
	private List<Dxresearch> problems = null;
	private List<Lab> labs = null;

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
		patientLabRoutingDao = context.getBean(PatientLabRoutingDao.class);
		hl7TextInfoDao = context.getBean(Hl7TextInfoDao.class);
		measurementDao = context.getBean(MeasurementDao.class);
		measurementsExtDao = context.getBean(MeasurementsExtDao.class);

		loaded = loadPatient(demographicNo);
	}

	private Boolean loadPatient(Integer demographicNo) {
		demographic = demographicDao.find(demographicNo);
		drugs = drugDao.findByDemographicId(demographicNo);
		problems = dxResearchDao.getDxResearchItemsByPatient(demographicNo);
		labs = assembleLabs(demographicNo);

		return demographic != null;
	}

	private List<Lab> assembleLabs(Integer demographicNo) {
		// Gather hl7TextInfo labs
		List<PatientLabRouting> tempRouting = patientLabRoutingDao.findByDemographicAndLabType(demographicNo, "HL7");
		List<Hl7TextInfo> tempLabs = new ArrayList<Hl7TextInfo>();
		for(PatientLabRouting routing : tempRouting) {
			Hl7TextInfo temp = hl7TextInfoDao.findLabId(routing.getLabNo());
			if(temp != null) {
				tempLabs.add(temp);
			}
		}

		// Short circuit if no labs
		if(tempLabs.isEmpty()) {
			return null;
		}

		// Gather and filter measurements based on existence of lab_no field
		List<Measurement> rawMeasurements = measurementDao.findByDemographicNo(demographicNo);
		List<Measurement> tempMeasurements = new ArrayList<Measurement>();
		for(Measurement entry : rawMeasurements) {
			MeasurementsExt isFromLab = measurementsExtDao.getMeasurementsExtByMeasurementIdAndKeyVal(entry.getId(), Constants.MeasurementsExtKeys.lab_no.toString());
			if(isFromLab != null && isValidLabMeasurement(tempRouting, isFromLab.getVal())) {
				tempMeasurements.add(entry);
			}
		}

		// Gather measurementsExt
		List<List<MeasurementsExt>> tempMeasurementsExt = new ArrayList<List<MeasurementsExt>>();
		for(Measurement entry : tempMeasurements) {
			List<MeasurementsExt> tempMeasurementsExtElement = measurementsExtDao.getMeasurementsExtByMeasurementId(entry.getId());
			tempMeasurementsExt.add(tempMeasurementsExtElement);
		}

		// Create Lab Objects
		List<Lab> allLabs = new ArrayList<Lab>();

		// Group Measurements into Lab Objects
		for(Hl7TextInfo labReport : tempLabs) {
			Lab labObj = new Lab();
			labObj.hl7TextInfo = labReport;

			// Group Measurements by Lab Number
			int labNumber = labReport.getLabNumber();
			List<Measurement> labMeasurementAll = new ArrayList<Measurement>();
			List<List<MeasurementsExt>> labMeasurementsExtAll = new ArrayList<List<MeasurementsExt>>();

			for(int i=0; i < tempMeasurementsExt.size(); i++) {
				List<MeasurementsExt> entry = tempMeasurementsExt.get(i);
				String entryLabNo = getLabExtValue(entry, Constants.MeasurementsExtKeys.lab_no.toString());

				// Add related entries to correct Lab
				if(labNumber == Integer.valueOf(entryLabNo)) {
					labMeasurementsExtAll.add(entry);
					entry.get(0).getMeasurementId();
					Measurement entryMeasurement = tempMeasurements.get(i);
					labMeasurementAll.add(entryMeasurement);
				}
			}

			// Group Measurements into Organizer Groups
			int prevGroup = 0;
			LabGroup tempGroup = new LabGroup(prevGroup);
			for(int i=0; i < labMeasurementsExtAll.size(); i++) {
				String temp = getLabExtValue(labMeasurementsExtAll.get(i), Constants.MeasurementsExtKeys.other_id.toString());
				if(temp != null && !temp.isEmpty()) {
					int currGroup = parseOtherID(temp)[0];

					// Create New Group
					if(prevGroup != currGroup) {
						labObj.group.add(tempGroup);
						prevGroup = currGroup;
						tempGroup = new LabGroup(prevGroup);
					}
				}

				// Add current measurement to Organizer Group
				tempGroup.measurement.add(labMeasurementAll.get(i));
				tempGroup.measurementsExt.add(labMeasurementsExtAll.get(i));
			}

			// Save final Group
			labObj.group.add(tempGroup);

			// Save Lab Object
			allLabs.add(labObj);
		}

		return allLabs;
	}

	private Boolean isValidLabMeasurement(List<PatientLabRouting> routing, String lab_no) {
		int labNo = Integer.parseInt(lab_no);
		for(PatientLabRouting entry : routing) {
			if(entry.getLabNo() == labNo) {
				return true;
			}
		}
		return false;
	}

	private Integer[] parseOtherID(String rhs) {
		Integer[] lhs = null;
		try {
			String[] temp = rhs.split("-");
			lhs = new Integer[temp.length];
			for(int i=0; i < temp.length; i++) {
				lhs[i] = Integer.parseInt(temp[i]);
			}
		} catch (Exception e) {
			log.error("parseOtherID - other_id field not in expected format");
		}

		return lhs;
	}

	// PatientExport Standard Interface
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

	public List<Dxresearch> getProblems() {
		return problems;
	}

	public List<Lab> getLabs() {
		return labs;
	}

	// PatientExport Supplemental Functions
	// TODO Replace this mock with a fully functional icd9Dao DB variant
	public String getICD9Description(String code) {
		if(!EverestUtils.isNullorEmptyorWhitespace(code) && Mappings.icd9Map.containsKey(code)) {
			return Mappings.icd9Map.get(code);
		} else {
			return null;
		}
	}

	public String getLabExtValue(List<MeasurementsExt> measurementExt, String keyval) {
		for (MeasurementsExt entry : measurementExt) {
			if(entry.getKeyVal().equals(keyval)) {
				return entry.getVal();
			}
		}
		return null;
	}

	// Supporting Lab Grouping Subclasses
	public static class Lab {
		public Hl7TextInfo hl7TextInfo;
		public List<LabGroup> group = new ArrayList<LabGroup>();

		public Hl7TextInfo getHl7TextInfo() {
			return hl7TextInfo;
		}

		public List<LabGroup> getGroup() {
			return group;
		}
	}

	public static class LabGroup {
		public int id;
		public List<Measurement> measurement = new ArrayList<Measurement>();
		public List<List<MeasurementsExt>> measurementsExt = new ArrayList<List<MeasurementsExt>>();

		public LabGroup(int id) {
			this.id = id;
		}

		public int getGroupId() {
			return id;
		}

		public List<Measurement> getMeasurement() {
			return measurement;
		}

		public List<List<MeasurementsExt>> getMeasurementsExt() {
			return measurementsExt;
		}
	}
}
