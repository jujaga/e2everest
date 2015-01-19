package com.jujaga.e2e;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;

import com.jujaga.e2e.director.Creator;
import com.jujaga.e2e.util.EverestUtils;

public class Main {
	public static void main(String[] args) {
		ClinicalDocument clinicalDocument = null;

		// Populate Clinical Document
		clinicalDocument = new Creator().CreateEmrConversionDocument();

		// Output Clinical Document as String
		String output = EverestUtils.GenerateDocumentToString(clinicalDocument, false);
		System.out.println(output);
	}
}
