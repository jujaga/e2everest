package com.jujaga.e2e.constant;

import org.marc.everest.datatypes.generic.CE;

// E2E constants and other hard-coded numbers/string will go here
public class Constants {
	public static class EMRConversionDocument {
		public static final String TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.1.1";
		public static final String CODE_DISPLAY_NAME_LOINC = "Medical Records";
		public static final String CODE_SYSTEM_LOINC = CodeSystems.LOINC_OID;
		public static final String CODE_LOINC = "11503-0";

		public static final CE<String> CODE = new CE<String>(
				EMRConversionDocument.CODE_LOINC, CodeSystems.LOINC_OID,
				CodeSystems.LOINC_DISPLAY_NAME, CodeSystems.LOINC_VERSION);
	}

	public static class DocumentHeader {
		public static final String TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.7.1";
		public static final String PITO_E2E_DTC_CLINICAL_DOCUMENT_TYPE_ID = "2.16.840.1.113883.1.3";
		public static final String PITO_E2E_DTC_CLINICAL_DOCUMENT_TYPE_ID_EXTENSION = "POCD_HD000040";
		public static final String PITO_E2E_DTC_CLINICAL_DOCUMENT_TYPE_REALM_CODE = "CA-BC";
		public static final String LANGUAGE_ENGLISH_CANADIAN = "en-CA";

		public static final String BC_PHN_OID_ASSIGNING_AUTHORITY_NAME = "BC-PHN";
		public static final String BC_PHN_OID = "2.16.840.1.113883.4.50";
	}

	public static class CodeSystems {
		public static final String LOINC_OID = "2.16.840.1.113883.6.1";
		public static final String LOINC_DISPLAY_NAME = "LOINC";
		public static final String LOINC_VERSION = "2.44";
	}

	public static class XML {
		public static final int INDENT = 2;
		public static final String ENCODING = "UTF-8";
		public static final String VERSION = "1.0";
	}
}
