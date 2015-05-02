package com.jujaga.e2e.constant;

public abstract class BodyConstants {
	public static enum SectionPriority {
		SHALL, SHOULD, MAY
	}

	public abstract static class AbstractBodyConstants {
		public SectionPriority SECTION_PRIORITY;
		public String WITH_ENTRIES_TITLE;
		public String WITHOUT_ENTRIES_TITLE;
		public String WITH_ENTRIES_TEMPLATE_ID;
		public String WITHOUT_ENTRIES_TEMPLATE_ID;
		public String CODE;
		public String CODE_SYSTEM;
		public String ENTRY_TEMPLATE_ID;
		public String ENTRY_NO_TEXT;
	}

	public static class AdvanceDirectives extends AbstractBodyConstants {
		protected static AbstractBodyConstants bodyConstants = null;

		private AdvanceDirectives() {
			SECTION_PRIORITY = SectionPriority.SHALL;
			WITH_ENTRIES_TITLE = "Advance Directives Section [with entries]";
			WITHOUT_ENTRIES_TITLE = "Advance Directives Section [without entries]";
			WITH_ENTRIES_TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.2.2.1";
			WITHOUT_ENTRIES_TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.2.2";
			CODE = "42348-3";
			CODE_SYSTEM = Constants.CodeSystems.LOINC_OID;
			ENTRY_TEMPLATE_ID = Constants.TemplateOids.ADVANCE_DIRECTIVES_OBSERVATION_TEMPLATE_ID;
			ENTRY_NO_TEXT = Constants.SectionSupport.SECTION_NOT_SUPPORTED_NO_DATA;
		}

		public static AbstractBodyConstants getConstants() {
			if(bodyConstants == null) {
				bodyConstants = new AdvanceDirectives();
			}
			return bodyConstants;
		}
	}

	public static class Labs extends AbstractBodyConstants {
		protected static AbstractBodyConstants bodyConstants = null;

		private Labs() {
			SECTION_PRIORITY = SectionPriority.SHALL;
			WITH_ENTRIES_TITLE = "Laboratory Results and Reports [with entries]";
			WITHOUT_ENTRIES_TITLE = "Laboratory Results and Reports [without entries]";
			WITH_ENTRIES_TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.2.16.1";
			WITHOUT_ENTRIES_TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.2.16";
			CODE = "30954-2";
			CODE_SYSTEM = Constants.CodeSystems.LOINC_OID;
			ENTRY_TEMPLATE_ID = Constants.TemplateOids.LABS_OBSERVATION_TEMPLATE_ID;
			ENTRY_NO_TEXT = Constants.SectionSupport.SECTION_SUPPORTED_NO_DATA;
		}

		public static AbstractBodyConstants getConstants() {
			if(bodyConstants == null) {
				bodyConstants = new Labs();
			}
			return bodyConstants;
		}
	}

	public static class Medications extends AbstractBodyConstants {
		protected static AbstractBodyConstants bodyConstants = null;

		public static final String DRUG_THERAPY_ACT_NAME = "Drug Therapy";
		public static final String LONG_TERM = "Long Term";
		public static final String SHORT_TERM = "Short Term";

		private Medications() {
			SECTION_PRIORITY = SectionPriority.SHALL;
			WITH_ENTRIES_TITLE = "Medications and Prescriptions - Medication List [with entries]";
			WITHOUT_ENTRIES_TITLE = "Medications and Prescriptions - Medication List [without entries]";
			WITH_ENTRIES_TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.2.19.1";
			WITHOUT_ENTRIES_TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.2.19";
			CODE = "10160-0";
			CODE_SYSTEM = Constants.CodeSystems.LOINC_OID;
			ENTRY_TEMPLATE_ID = Constants.TemplateOids.MEDICATION_EVENT_TEMPLATE_ID;
			ENTRY_NO_TEXT = Constants.SectionSupport.SECTION_SUPPORTED_NO_DATA;
		}

		public static AbstractBodyConstants getConstants() {
			if(bodyConstants == null) {
				bodyConstants = new Medications();
			}
			return bodyConstants;
		}
	}

	public static class OrdersAndRequests extends AbstractBodyConstants {
		protected static AbstractBodyConstants bodyConstants = null;

		private OrdersAndRequests() {
			SECTION_PRIORITY = SectionPriority.SHALL;
			WITH_ENTRIES_TITLE = "Orders and Requests [with entries]";
			WITHOUT_ENTRIES_TITLE = "Orders and Requests [without entries]";
			WITH_ENTRIES_TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.2.20.1";
			WITHOUT_ENTRIES_TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.2.20";
			CODE = "REQS";
			CODE_SYSTEM = Constants.CodeSystems.LOINC_OID;
			ENTRY_TEMPLATE_ID = Constants.TemplateOids.ORDER_EVENT_TEMPLATE_ID;
			ENTRY_NO_TEXT = Constants.SectionSupport.SECTION_NOT_SUPPORTED_NO_DATA;
		}

		public static AbstractBodyConstants getConstants() {
			if(bodyConstants == null) {
				bodyConstants = new OrdersAndRequests();
			}
			return bodyConstants;
		}
	}

	public static class Problems extends AbstractBodyConstants {
		protected static AbstractBodyConstants bodyConstants = null;

		private Problems() {
			SECTION_PRIORITY = SectionPriority.SHALL;
			WITH_ENTRIES_TITLE = "Problems and Conditions - Problem List [with entries]";
			WITHOUT_ENTRIES_TITLE = "Problems and Conditions - Problem List [without entries]";
			WITH_ENTRIES_TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.2.21.1";
			WITHOUT_ENTRIES_TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.2.21";
			CODE = "11450-4";
			CODE_SYSTEM = Constants.CodeSystems.LOINC_OID;
			ENTRY_TEMPLATE_ID = Constants.TemplateOids.PROBLEMS_OBSERVATION_TEMPLATE_ID;
			ENTRY_NO_TEXT = Constants.SectionSupport.SECTION_SUPPORTED_NO_DATA;
		}

		public static AbstractBodyConstants getConstants() {
			if(bodyConstants == null) {
				bodyConstants = new Problems();
			}
			return bodyConstants;
		}
	}
}

