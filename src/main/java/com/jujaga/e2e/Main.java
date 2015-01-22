package com.jujaga.e2e;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;

import com.jujaga.e2e.director.E2ECreator;
import com.jujaga.e2e.util.EverestUtils;

public class Main {
	public static void main(String[] args) {
		boolean validate = true;

		// Populate Clinical Document
		ClinicalDocument clinicalDocument = new E2ECreator().createEmrConversionDocument(StubRecord.Demographic.demographicNo);

		// Output Clinical Document as String
		String output = EverestUtils.generateDocumentToString(clinicalDocument, validate);

		System.out.println();
		System.out.println(output);
	}
}
