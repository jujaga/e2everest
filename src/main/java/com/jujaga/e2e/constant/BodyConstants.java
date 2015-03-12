package com.jujaga.e2e.constant;

public class BodyConstants {
	private BodyConstants() {}

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
		public String ENTRY_NO_TEXT = Constants.SectionSupport.SECTION_SUPPORTED_NO_DATA;
	}

	public static class Medications extends AbstractBodyConstants {
		protected static AbstractBodyConstants bodyConstants = null;

		public static final Integer NO_DIN_NUMBER = -1;
		public static final String LONG_TERM = "Long Term";
		public static final String SHORT_TERM = "Short Term";

		private Medications() {
			SECTION_PRIORITY = SectionPriority.SHALL;
			WITH_ENTRIES_TITLE = "Medications & Prescriptions - Medication List [with entries]";
			WITHOUT_ENTRIES_TITLE = "Medications & Prescriptions - Medication List [without entries]";
			WITH_ENTRIES_TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.2.19.1";
			WITHOUT_ENTRIES_TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.2.19";
			CODE = "10160-0";
			CODE_SYSTEM = Constants.CodeSystems.LOINC_OID;
			ENTRY_TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.3.18";
		}

		public static AbstractBodyConstants getConstants() {
			if(bodyConstants == null) {
				bodyConstants = new Medications();
			}
			return bodyConstants;
		}
	}
}

