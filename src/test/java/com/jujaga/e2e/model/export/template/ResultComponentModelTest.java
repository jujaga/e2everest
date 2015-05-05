package com.jujaga.e2e.model.export.template;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.datatypes.generic.CS;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Component4;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Observation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ActClassObservation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ActRelationshipHasComponent;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ActStatus;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActMoodDocumentObservation;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.util.EverestUtils;
import com.jujaga.emr.PatientExport.LabComponent;
import com.jujaga.emr.dao.MeasurementDao;
import com.jujaga.emr.dao.MeasurementsExtDao;
import com.jujaga.emr.model.Measurement;
import com.jujaga.emr.model.MeasurementsExt;

public class ResultComponentModelTest {
	private static ApplicationContext context;
	private static MeasurementDao measurementDao;
	private static MeasurementsExtDao measurementsExtDao;
	private static Measurement measurement;
	private static List<MeasurementsExt> measurementsExt;

	private static LabComponent labComponent;
	private static LabComponent nullLabComponent;
	private static ResultComponentModel resultComponentModel;

	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);
		context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		measurementDao = context.getBean(MeasurementDao.class);
		measurementsExtDao = context.getBean(MeasurementsExtDao.class);
		measurement = measurementDao.find(Constants.Runtime.VALID_LAB_MEASUREMENT);
		measurementsExt = measurementsExtDao.getMeasurementsExtByMeasurementId(Constants.Runtime.VALID_LAB_MEASUREMENT);
		resultComponentModel = new ResultComponentModel();
	}

	@Before
	public void before() {
		labComponent = new LabComponent(measurement, measurementsExt);
		nullLabComponent = new LabComponent(null, null);
	}

	@Test
	public void resultComponentModelNullTest() {
		assertNotNull(new ResultComponentModel());
	}

	@Test
	public void resultComponentStructureTest() {
		resultComponentStructureTestHelper(labComponent);
	}

	@Test
	public void resultComponentStructureNullTest() {
		resultComponentStructureTestHelper(nullLabComponent);
	}

	private void resultComponentStructureTestHelper(LabComponent labComponent) {
		Component4 component = resultComponentModel.getComponent(labComponent);
		assertNotNull(component);
		assertEquals(ActRelationshipHasComponent.HasComponent, component.getTypeCode().getCode());
		assertTrue(component.getContextConductionInd().toBoolean());
		assertEquals(Constants.TemplateOids.RESULT_COMPONENT_TEMPLATE_ID, component.getTemplateId().get(0).getRoot());

		Observation observation = component.getClinicalStatementIfObservation();
		assertNotNull(observation);
		assertEquals(ActClassObservation.OBS, observation.getClassCode().getCode());
		assertEquals(x_ActMoodDocumentObservation.Eventoccurrence, observation.getMoodCode().getCode());

		assertNotNull(observation.getId());
		assertNotNull(observation.getCode());
		assertNotNull(observation.getStatusCode());
	}

	private Observation observationHelper(LabComponent labComponent) {
		Component4 component = resultComponentModel.getComponent(labComponent);
		return component.getClinicalStatementIfObservation();
	}

	@Test
	public void idTest() {
		SET<II> ids = observationHelper(labComponent).getId();
		assertNotNull(ids);

		II id = ids.get(0);
		assertNotNull(id);
		assertEquals(Constants.EMR.EMR_OID, id.getRoot());
		assertFalse(EverestUtils.isNullorEmptyorWhitespace(id.getExtension()));
		assertEquals(labComponent.getMeasurementsMap().get(Constants.MeasurementsExtKeys.accession.toString()), id.getExtension());
	}

	@Test
	public void idNullTest() {
		SET<II> ids = observationHelper(nullLabComponent).getId();
		assertNotNull(ids);

		II id = ids.get(0);
		assertNotNull(id);
		assertTrue(id.isNull());
		assertEquals(NullFlavor.NoInformation, id.getNullFlavor().getCode());
	}

	@Test
	public void codeTest() {
		CD<String> code = observationHelper(labComponent).getCode();
		assertNotNull(code);
		assertEquals(labComponent.getMeasurementsMap().get(Constants.MeasurementsExtKeys.identifier.toString()), code.getCode());
		assertEquals(Constants.CodeSystems.PCLOCD_OID, code.getCodeSystem());
		assertEquals(Constants.CodeSystems.PCLOCD_NAME, code.getCodeSystemName());
	}

	@Test
	public void codeNullTest() {
		CD<String> code = observationHelper(nullLabComponent).getCode();
		assertNotNull(code);
		assertTrue(code.isNull());
		assertEquals(NullFlavor.NoInformation, code.getNullFlavor().getCode());
	}

	@Test
	public void statusCodeActiveTest() {
		labComponent.getMeasurementsMap().replace(Constants.MeasurementsExtKeys.olis_status.toString(), "P");

		CS<ActStatus> status = observationHelper(labComponent).getStatusCode();
		assertNotNull(status);
		assertEquals(ActStatus.Active, status.getCode());
	}

	@Test
	public void statusCodeCompleteTest() {
		CS<ActStatus> status = observationHelper(labComponent).getStatusCode();
		assertNotNull(status);
		assertEquals(ActStatus.Completed, status.getCode());
	}

	@Test
	public void statusCodeNullTest() {
		CS<ActStatus> status = observationHelper(nullLabComponent).getStatusCode();
		assertNotNull(status);
		assertTrue(status.isNull());
		assertEquals(NullFlavor.NoInformation, status.getNullFlavor().getCode());
	}
}
