package com.jujaga.emr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		if(demographic == null) {
			log.error("Demographic ".concat(demographicNo.toString()).concat(" can't be loaded"));
			return false;
		}

		try {
			labs = assembleLabs(demographicNo);
		} catch (Exception e) {
			log.error("loadPatient - Failed to load Labs", e);
			labs = null;
		}

		try {
			drugs = drugDao.findByDemographicId(demographicNo);
		} catch (Exception e) {
			log.error("loadPatient - Failed to load Medications", e);
			drugs = null;
		}

		try {
			problems = dxResearchDao.getDxResearchItemsByPatient(demographicNo);
		} catch (Exception e) {
			log.error("loadPatient - Failed to load Problems", e);
			problems = null;
		}

		return true;
	}

	private List<Lab> assembleLabs(Integer demographicNo) {
		// Gather Hl7TextInfo labs
		List<PatientLabRouting> tempRouting = patientLabRoutingDao.findByDemographicAndLabType(demographicNo, "HL7");
		List<Hl7TextInfo> allHl7TextInfo = new ArrayList<Hl7TextInfo>();
		for(PatientLabRouting routing : tempRouting) {
			Hl7TextInfo temp = hl7TextInfoDao.findLabId(routing.getLabNo());
			if(temp != null) {
				allHl7TextInfo.add(temp);
			}
		}

		// Short circuit if no labs
		if(allHl7TextInfo.isEmpty()) {
			return null;
		}

		// Gather and filter Measurements based on existence of lab_no field
		List<Measurement> rawMeasurements = measurementDao.findByDemographicNo(demographicNo);
		List<LabComponent> allLabComponents = new ArrayList<LabComponent>();
		for(Measurement entry : rawMeasurements) {
			MeasurementsExt labNo = measurementsExtDao.getMeasurementsExtByMeasurementIdAndKeyVal(entry.getId(), Constants.MeasurementsExtKeys.lab_no.toString());

			if(isValidLabMeasurement(tempRouting, labNo)) {
				// Gather MeasurementsExt and pair with Measurements into LabComponents
				List<MeasurementsExt> tempMeasurementsExts = measurementsExtDao.getMeasurementsExtByMeasurementId(entry.getId());
				Map<String, String> map = new HashMap<String, String>();
				for(MeasurementsExt extElement : tempMeasurementsExts) {
					map.put(extElement.getKeyVal(), extElement.getVal());
				}
				allLabComponents.add(new LabComponent(entry, Collections.unmodifiableMap(map)));
			}
		}

		// Create Lab Observations
		List<Lab> allLabs = new ArrayList<Lab>();
		for(Hl7TextInfo labReport : allHl7TextInfo) {
			Lab labObservation = new Lab(labReport);

			// Get LabComponents in this Lab Observation
			Integer labNumber = labReport.getLabNumber();
			List<LabComponent> tempLabComponents = new ArrayList<LabComponent>();
			for(LabComponent labComponent : allLabComponents) {
				String componentLabNumber = labComponent.getMeasurementsMap().get(Constants.MeasurementsExtKeys.lab_no.toString());
				if(Integer.valueOf(componentLabNumber) == labNumber) {
					tempLabComponents.add(labComponent);
				}
			}

			// Cluster LabComponents into LabOrganizers
			Integer prevOrganizer = 0;
			LabOrganizer tempLabOrganizer = new LabOrganizer(prevOrganizer);
			for(LabComponent labComponent : tempLabComponents) {
				String rawOtherId = labComponent.getMeasurementsMap().get(Constants.MeasurementsExtKeys.other_id.toString());
				if(!EverestUtils.isNullorEmptyorWhitespace(rawOtherId)) {
					Integer currOrganizer = parseOtherID(rawOtherId)[0];

					// Create New LabOrganizer Group
					if(prevOrganizer != currOrganizer) {
						labObservation.getLabOrganizer().add(tempLabOrganizer);
						prevOrganizer = currOrganizer;
						tempLabOrganizer = new LabOrganizer(prevOrganizer);
					}
				}

				// Add current LabComponent to LabOrganizer
				tempLabOrganizer.getLabComponent().add(labComponent);
			}

			// Save final LabOrganizer and Lab Observation
			labObservation.getLabOrganizer().add(tempLabOrganizer);
			allLabs.add(labObservation);
		}

		return allLabs;
	}

	private Boolean isValidLabMeasurement(List<PatientLabRouting> routing, MeasurementsExt lab_no) {
		if(lab_no != null) {
			Integer labNo = Integer.parseInt(lab_no.getVal());
			for(PatientLabRouting entry : routing) {
				if(entry.getLabNo() == labNo) {
					return true;
				}
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

	// Supporting Lab Grouping Subclasses
	public class Lab {
		private Hl7TextInfo hl7TextInfo;
		private List<LabOrganizer> labOrganizer = new ArrayList<LabOrganizer>();

		public Lab(Hl7TextInfo hl7TextInfo) {
			this.hl7TextInfo = hl7TextInfo;
		}

		public Hl7TextInfo getHl7TextInfo() {
			return hl7TextInfo;
		}

		public List<LabOrganizer> getLabOrganizer() {
			return labOrganizer;
		}
	}

	public class LabOrganizer {
		private Integer id;
		private List<LabComponent> labComponent = new ArrayList<LabComponent>();

		public LabOrganizer(Integer id) {
			this.id = id;
		}

		public Integer getGroupId() {
			return id;
		}

		public List<LabComponent> getLabComponent() {
			return labComponent;
		}
	}

	public class LabComponent {
		private Measurement measurement = null;
		private Map<String, String> measurementsMap = null;

		public LabComponent(Measurement measurement, Map<String, String> measurementsMap) {
			this.measurement = measurement;
			this.measurementsMap = measurementsMap;
		}

		public Measurement getMeasurement() {
			return measurement;
		}

		public Map<String, String> getMeasurementsMap() {
			return measurementsMap;
		}
	}
}
