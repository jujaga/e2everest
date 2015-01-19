package com.jujaga.e2e.constant;

// E2E constants and other hard-coded numbers/string will go here
public class Constants {
	public static class EMRConversionDocument
	{
		public static final String TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.1.1";
	}

	public static class DocumentHeader
	{
		public static final String TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.7.1";
		public static final String PITO_E2E_DTC_CLINICAL_DOCUMENT_TYPE_ID = "2.16.840.1.113883.1.3";
		public static final String PITO_E2E_DTC_CLINICAL_DOCUMENT_TYPE_ID_EXTENSION = "POCD_HD000040";
		public static final String PITO_E2E_DTC_CLINICAL_DOCUMENT_TYPE_REALM_CODE = "CA-BC";
	}

	public static class XML
	{
		public static final int INDENT = 2;
		public static final String ENCODING = "UTF-8";
		public static final String VERSION = "1.0";
	}
}
