package com.jujaga.emr;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.jujaga.e2e.constant.Constants;

public class PatientExportTest {
	@Test
	public void patientExportTest() {
		PatientExport patientExport = new PatientExport(Constants.Runtime.VALID_DEMOGRAPHIC);
		assertTrue(patientExport.isLoaded());
	}
}
