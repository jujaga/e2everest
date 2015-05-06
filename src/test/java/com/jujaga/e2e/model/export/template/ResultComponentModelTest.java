package com.jujaga.e2e.model.export.template;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.datatypes.ANY;
import org.marc.everest.datatypes.ED;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.PQ;
import org.marc.everest.datatypes.ST;
import org.marc.everest.datatypes.TS;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.datatypes.generic.CE;
import org.marc.everest.datatypes.generic.CS;
import org.marc.everest.datatypes.generic.IVL;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Component4;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Observation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ActClassObservation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ActRelationshipHasComponent;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ActStatus;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ObservationInterpretation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActMoodDocumentObservation;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jujaga.e2e.constant.BodyConstants.Labs;
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
	public void textTest() {
		String name = labComponent.getMeasurementsMap().get(Constants.MeasurementsExtKeys.name.toString());
		ED text = observationHelper(labComponent).getText();
		assertNotNull(text);
		assertEquals(name, new String(text.getData()));
	}

	@Test
	public void textNullTest() {
		ED text = observationHelper(nullLabComponent).getText();
		assertNull(text);
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

	@Test
	public void effectiveTimeTest() {
		String datetime = labComponent.getMeasurementsMap().get(Constants.MeasurementsExtKeys.datetime.toString());
		IVL<TS> ivl = observationHelper(labComponent).getEffectiveTime();
		assertNotNull(ivl);
		assertEquals(EverestUtils.buildTSFromDate(EverestUtils.stringToDate(datetime)), ivl.getLow());
	}

	@Test
	public void effectiveTimeNullTest() {
		IVL<TS> ivl = observationHelper(nullLabComponent).getEffectiveTime();
		assertNull(ivl);
	}

	@Test
	public void valuePQTest() {
		String dataField = labComponent.getMeasurement().getDataField();
		String unit = "un it";
		labComponent.getMeasurementsMap().replace(Constants.MeasurementsExtKeys.unit.toString(), unit);

		ANY value = observationHelper(labComponent).getValue();
		assertNotNull(value);
		assertEquals(PQ.class, value.getDataType());

		PQ pq = (PQ) value;
		assertEquals(new BigDecimal(dataField), pq.getValue());
		assertEquals(unit.replaceAll("\\s","_"), pq.getUnit());
	}

	@Test
	public void valueSTValueUnitTest() {
		String dataField = "test";
		String unit = labComponent.getMeasurementsMap().get(Constants.MeasurementsExtKeys.unit.toString());
		labComponent.getMeasurement().setDataField(dataField);

		ANY value = observationHelper(labComponent).getValue();
		assertNotNull(value);
		assertEquals(ST.class, value.getDataType());

		ST st = (ST) value;
		assertEquals(dataField.concat(" ").concat(unit), st.getValue());
	}

	@Test
	public void valueSTValueOnlyTest() {
		String dataField = labComponent.getMeasurement().getDataField();
		labComponent.getMeasurementsMap().remove(Constants.MeasurementsExtKeys.unit.toString());

		ANY value = observationHelper(labComponent).getValue();
		assertNotNull(value);
		assertEquals(ST.class, value.getDataType());

		ST st = (ST) value;
		assertEquals(dataField, st.getValue());
	}

	@Test
	public void valueNullTest() {
		ANY value = observationHelper(nullLabComponent).getValue();
		assertNull(value);
	}

	@Test
	public void interpretationCodeAbnormalTest() {
		String abnormal = "A";
		labComponent.getMeasurementsMap().replace(Constants.MeasurementsExtKeys.abnormal.toString(), abnormal);

		SET<CE<ObservationInterpretation>> interpretationCodes = observationHelper(labComponent).getInterpretationCode();
		assertNotNull(interpretationCodes);

		CE<ObservationInterpretation> interpretation = interpretationCodes.get(0);
		assertNotNull(interpretation);
		assertEquals(ObservationInterpretation.Abnormal, interpretation.getCode());
		assertEquals(Labs.ABNORMAL, interpretation.getDisplayName());
	}

	@Test
	public void interpretationCodeNormalTest() {
		SET<CE<ObservationInterpretation>> interpretationCodes = observationHelper(labComponent).getInterpretationCode();
		assertNotNull(interpretationCodes);

		CE<ObservationInterpretation> interpretation = interpretationCodes.get(0);
		assertNotNull(interpretation);
		assertEquals(ObservationInterpretation.Normal, interpretation.getCode());
		assertEquals(Labs.NORMAL, interpretation.getDisplayName());
	}

	@Test
	public void interpretationCodeNullTest() {
		SET<CE<ObservationInterpretation>> interpretationCodes = observationHelper(nullLabComponent).getInterpretationCode();
		assertNull(interpretationCodes);
	}
}
