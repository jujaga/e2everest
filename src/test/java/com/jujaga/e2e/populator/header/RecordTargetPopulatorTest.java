package com.jujaga.e2e.populator.header;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Patient;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.PatientRole;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.RecordTarget;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.director.E2ECreator;

public class RecordTargetPopulatorTest {
	private static ClinicalDocument clinicalDocument;
	private static PatientRole patientRole;

	@BeforeClass
	public static void beforeClass() {
		clinicalDocument = E2ECreator.createEmrConversionDocument(Constants.Runtime.VALID_DEMOGRAPHIC);
		patientRole = clinicalDocument.getRecordTarget().get(0).getPatientRole();
	}

	@Test
	public void recordTargetTest() {
		ArrayList<RecordTarget> recordTargets = clinicalDocument.getRecordTarget();
		assertNotNull(recordTargets);
		assertEquals(1, recordTargets.size());

		RecordTarget recordTarget = recordTargets.get(0);
		assertNotNull(recordTarget);
	}

	@Test
	public void patientRoleTest() {
		assertNotNull(patientRole);
	}

	@Test
	public void patientTest() {
		Patient patient = patientRole.getPatient();
		assertNotNull(patient);
	}
}
