package com.jujaga.e2e.constant;

import org.marc.everest.datatypes.generic.CE;

// E2E constants and other hard-coded numbers/string will go here
public class Constants {
	private Constants() {}

	/**
	 * Enumerations
	 */
	public static enum IdPrefixes {
		AdvanceDirectives, Alerts, Allergies, ClinicalMeasuredObservations, Encounters,
		FamilyHistory, Immunizations, Medications, Lab, LabOBR, MedicationPrescriptions,
		ProblemList, RiskFactors, Referrals, SocialHistory, SubstanceUse
	}

	public static enum TelecomType {
		EMAIL, TELEPHONE
	}

	public static enum SubstanceAdministrationType {
		DRUG, IMMUNIZ, ANTIGEN
	}

	public static enum ObservationType {
		UNBOUND, ICD9CODE, INSTRUCT, REASON, PRNIND, COMMENT, DATEOBS, SEV, ALRGRP,
		CLINSTAT, RECLINK, TRTNOTE, REACTOBS, NXTENCREAS, OUTCOBS
	}

	/**
	 * Header Constants
	 */
	public static class EMR {
		public static final String EMR_OID = "2.16.840.1.113883.3.3331";
		public static final String EMR_VERSION = "OSCAR EMR";
	}

	public static class EMRConversionDocument {
		public static final String TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.1.1";
		public static final String CODE_DISPLAY_NAME_LOINC = "Medical Records";
		public static final String CODE_SYSTEM_LOINC = Constants.CodeSystems.LOINC_OID;
		public static final String CODE_LOINC = "11503-0";

		public static final CE<String> CODE = new CE<String>(
				Constants.EMRConversionDocument.CODE_LOINC, Constants.CodeSystems.LOINC_OID,
				Constants.CodeSystems.LOINC_NAME, Constants.CodeSystems.LOINC_VERSION);
	}

	public static class DocumentHeader {
		public static final String TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.7.1";
		public static final String PITO_E2E_DTC_CLINICAL_DOCUMENT_TYPE_ID = "2.16.840.1.113883.1.3";
		public static final String PITO_E2E_DTC_CLINICAL_DOCUMENT_TYPE_ID_EXTENSION = "POCD_HD000040";
		public static final String PITO_E2E_DTC_CLINICAL_DOCUMENT_TYPE_REALM_CODE = "CA-BC";
		public static final String LANGUAGE_ENGLISH_CANADIAN = "en-CA";

		public static final String EMAIL_PREFIX = "mailto:";
		public static final String TEL_PREFIX = "tel:";

		public static final String MALE_ADMINISTRATIVE_GENDER_CODE = "M";
		public static final String FEMALE_ADMINISTRATIVE_GENDER_CODE = "F";
		public static final String UNDIFFERENTIATED_ADMINISTRATIVE_GENDER_CODE = "UN";
		public static final String MALE_ADMINISTRATIVE_GENDER_DESCRIPTION = "Male";
		public static final String FEMALE_ADMINISTRATIVE_GENDER_DESCRIPTION = "Female";
		public static final String UNDIFFERENTIATED_ADMINISTRATIVE_GENDER_DESCRIPTION = "Undifferentiated";

		public static final String BC_PHN_OID_ASSIGNING_AUTHORITY_NAME = "BC-PHN";
		public static final String BC_PHN_OID = "2.16.840.1.113883.4.50";

		public static final String BC_MINISTRY_OF_HEALTH_PRACTITIONER_ID_OID = "2.16.840.1.113883.3.40.2.11";
		public static final String BC_MINISTRY_OF_HEALTH_PRACTITIONER_NAME = "BCMOH";

		public static final String HUMANLANGUAGE_ENGLISH_CODE = "EN";
		public static final String HUMANLANGUAGE_FRENCH_CODE = "FR";
		public static final String HUMANLANGUAGE_ENGLISH_DESCRIPTION = "English";
		public static final String HUMANLANGUAGE_FRENCH_DESCRIPTION = "French";
	}

	/**
	 * Body Constants
	 */
	public static class CodeSystems {
		public static final String ACT_CODE_CODESYSTEM_OID = "2.16.840.1.113883.5.4";
		public static final String ACT_CODE_CODESYSTEM_NAME = "ActCode";
		public static final String ADMINISTRATIVE_GENDER_OID = "2.16.840.1.113883.5.1";
		public static final String ATC_OID = "2.16.840.1.113883.6.73";
		public static final String ATC_NAME = "whoATC";
		public static final String DIN_OID = "2.16.840.1.113883.5.1105";
		public static final String DIN_NAME = "HC-DIN";
		public static final String LOINC_OID = "2.16.840.1.113883.6.1";
		public static final String LOINC_NAME = "LOINC";
		public static final String LOINC_VERSION = "2.44";
		public static final String OBSERVATIONTYPE_CA_PENDING_OID = "2.16.840.1.113883.3.3068.10.6.3";
		public static final String OBSERVATIONTYPE_CA_PENDING_NAME = "ObservationType-CA-Pending";
	}

	public static class SectionSupport {
		public static final String SECTION_SUPPORTED_NO_DATA = "No information for this section for this patient";
		public static final String SECTION_NOT_SUPPORTED_NO_DATA = "This section is not supported by the Originating Application";
	}

	public static class TemplateOids {
		public static final String AUTHOR_PARTICIPATION_TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.4.2";
		public static final String DOSE_OBSERVATION_TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.4.8";
		public static final String INSTRUCTION_OBSERVATION_TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.4.35";
		public static final String MEDICATION_IDENTIFICATION_TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.4.16";
		public static final String MEDICATION_PRESCRIPTION_EVENT_TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.4.20";
	}

	/**
	 * Runtime Constants
	 */
	public static class XML {
		public static final Integer INDENT = 2;
		public static final String ENCODING = "UTF-8";
		public static final String VERSION = "1.0";
	}

	public static class Runtime {
		public static final String SPRING_APPLICATION_CONTEXT = "spring-config.xml";
		public static final Integer VALID_CLINIC = 123456;
		public static final Integer VALID_DEMOGRAPHIC = 1;
		public static final Integer VALID_PROVIDER = 999998;
	}
}
