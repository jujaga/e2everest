package com.jujaga.e2e.director;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;

public class Creator {
	public ClinicalDocument CreateEmrConversionDocument() {
		return new ClinicalDocument();
	}
}
