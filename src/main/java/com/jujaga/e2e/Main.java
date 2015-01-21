package com.jujaga.e2e;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;

import com.jujaga.e2e.director.E2ECreator;
import com.jujaga.e2e.util.E2EXSDValidator;
import com.jujaga.e2e.util.EverestUtils;

public class Main {
	public static void main(String[] args) {
		ClinicalDocument clinicalDocument = null;

		// Populate Clinical Document
		clinicalDocument = new E2ECreator().createEmrConversionDocument(StubRecord.Demographic.demographicNo);

		// Output Clinical Document as String
		String output = EverestUtils.generateDocumentToString(clinicalDocument, false);
		System.out.println(output);
		
		if(E2EXSDValidator.isValidXML(output)) {
			System.out.println("VALIDATION PASSED");
		}
	}
}
