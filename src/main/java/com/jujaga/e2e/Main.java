package com.jujaga.e2e;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.director.E2ECreator;
import com.jujaga.e2e.util.EverestUtils;

public class Main {
	public static void main(String[] args) {
		Boolean validate = true;
		Integer demographicNo = Constants.Runtime.VALID_DEMOGRAPHIC;

		// Populate Clinical Document
		ClinicalDocument clinicalDocument = E2ECreator.createEmrConversionDocument(demographicNo);

		// Output Clinical Document as String
		String output = EverestUtils.generateDocumentToString(clinicalDocument, validate);
		if(output != null) {
			System.out.println(output);
		}
	}
}
