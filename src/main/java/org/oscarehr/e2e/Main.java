package org.oscarehr.e2e;

import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.util.EverestUtils;
import org.oscarehr.exports.e2e.E2EPatientExport;
import org.oscarehr.exports.e2e.E2EVelocityTemplate;

public class Main {
	Main() {
		throw new UnsupportedOperationException();
	}

	public static void main(String[] args) {
		// E2Everest
		/*for(int i = 0; i < 10000; i++) {
			Integer demographicNo = Constants.Runtime.VALID_DEMOGRAPHIC;

			// Populate Clinical Document
			ClinicalDocument clinicalDocument = E2ECreator.createEmrConversionDocument(demographicNo);

			// Output Clinical Document as String
			String output = EverestUtils.generateDocumentToString(clinicalDocument, true);
		}*/

		// Velocity
		//for(int i = 0; i < 10000; i++) {
			Integer demographicNo = Constants.Runtime.VALID_DEMOGRAPHIC;
	
			E2EVelocityTemplate t = new E2EVelocityTemplate();
	
			// Create and load Patient data
			E2EPatientExport patient = new E2EPatientExport();
			patient.setExProblemList(true);
	
			// Load patient data and merge to template
			String output = null;
			if(patient.loadPatient(demographicNo.toString())) {
				//t.disableValidation();
				output = t.export(patient);
			}
		//}

		if(!EverestUtils.isNullorEmptyorWhitespace(output)) {
			System.out.println(output);
		}
	}
}
