package com.jujaga.emr;

import java.util.ArrayList;
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
		for(Measurement measurement : rawMeasurements) {
			MeasurementsExt labNo = measurementsExtDao.getMeasurementsExtByMeasurementIdAndKeyVal(measurement.getId(), Constants.MeasurementsExtKeys.lab_no.toString());

			// Gather MeasurementsExt and pair with Measurements into LabComponents
			if(isValidLabMeasurement(tempRouting, labNo)) {
				List<MeasurementsExt> measurementsExts = measurementsExtDao.getMeasurementsExtByMeasurementId(measurement.getId());
				allLabComponents.add(new LabComponent(measurement, measurementsExts));
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
					if(EverestUtils.isNullorEmptyorWhitespace(labObservation.getRequestDate())) {
						labObservation.setRequestDate(labComponent.getMeasurementsMap().get(Constants.MeasurementsExtKeys.request_datetime.toString()));
					}
					tempLabComponents.add(labComponent);
				}
			}

			// Cluster LabComponents into LabOrganizers
			Integer prevOrganizer = 0;
			LabOrganizer tempLabOrganizer = new LabOrganizer(prevOrganizer, labReport.getReportStatus());
			for(LabComponent labComponent : tempLabComponents) {
				String rawOtherId = labComponent.getMeasurementsMap().get(Constants.MeasurementsExtKeys.other_id.toString());
				if(!EverestUtils.isNullorEmptyorWhitespace(rawOtherId)) {
					Integer currOrganizer = parseOtherID(rawOtherId)[0];

					// Create New LabOrganizer Group
					if(prevOrganizer != currOrganizer) {
						labObservation.getLabOrganizer().add(tempLabOrganizer);
						prevOrganizer = currOrganizer;
						tempLabOrganizer = new LabOrganizer(prevOrganizer, labReport.getReportStatus());
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
	public static class Lab {
		private Hl7TextInfo hl7TextInfo = new Hl7TextInfo();
		private List<LabOrganizer> labOrganizer = new ArrayList<LabOrganizer>();
		private String requestDate = null;

		public Lab(Hl7TextInfo hl7TextInfo) {
			if(hl7TextInfo != null) {
				this.hl7TextInfo = hl7TextInfo;
			}
		}

		public Hl7TextInfo getHl7TextInfo() {
			return hl7TextInfo;
		}

		public List<LabOrganizer> getLabOrganizer() {
			return labOrganizer;
		}

		public String getRequestDate() {
			return requestDate;
		}

		public void setRequestDate(String requestDate) {
			this.requestDate = requestDate;
		}
	}

	public static class LabOrganizer {
		private Integer id = Constants.Runtime.INVALID_VALUE;
		private String reportStatus = null;
		private List<LabComponent> labComponent = new ArrayList<LabComponent>();

		public LabOrganizer(Integer id, String reportStatus) {
			this.id = id;
			this.reportStatus = reportStatus;
		}

		public Integer getGroupId() {
			return id;
		}

		public String getReportStatus() {
			return reportStatus;
		}

		public List<LabComponent> getLabComponent() {
			return labComponent;
		}
	}

	public static class LabComponent {
		private Measurement measurement = new Measurement();
		private Map<String, String> measurementsMap = new HashMap<String, String>();

		public LabComponent(Measurement measurement, List<MeasurementsExt> measurementsExt) {
			if(measurement != null) {
				this.measurement = measurement;
			}
			if(measurementsExt != null) {
				mapMeasurementsExt(measurementsExt);
			}
		}

		private void mapMeasurementsExt(List<MeasurementsExt> measurementsExts) {
			if(measurementsExts != null) {
				for(MeasurementsExt extElement : measurementsExts) {
					this.measurementsMap.put(extElement.getKeyVal(), extElement.getVal());
				}
			}
		}

		public Measurement getMeasurement() {
			return measurement;
		}

		public Map<String, String> getMeasurementsMap() {
			return measurementsMap;
		}
	}
}
