package com.jujaga.e2e;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;

// A temporary stub object holding data values necessary to populate a demographic only portion of E2E
public final class StubRecord {
	public static final class Demographic {
		public static Integer demographicNo = 1;
		public static String randomDocGUID = UUID.randomUUID().toString();
		public static String firstName = "John";
		public static String lastName = "Cleese";
		public static Calendar docCreated = new GregorianCalendar();
		public static String hin = "448000001";
		public static String address = "1234 Street";
		public static String city = "city";
		public static String province = "BC";
		public static String postal = "V8T1D6";
		public static String phoneHome = "1234567";
		public static String phoneWork = "765-4321";
		public static String email = "test@test.com";
		public static String sex = "M";
		public static String yearOfBirth = "1940";
		public static String monthOfBirth = "09";
		public static String dateOfBirth = "25";
		public static String officialLanguage = "English";
	}

	public static final class Provider {
		public static String providerId = "999998";
		public static String providerHomePhone = "3456789";
		public static String providerWorkPhone = "987-6543";
		public static String providerEmail = "test2@test2.com";
		public static String providerLastName = "oscardoc";
		public static String providerFirstName = "doctor";
	}

	public static final class Custodian {
		public static String custodianId = "123456";
		public static String cusstodianClinicName = "McMaster Hospital";
	}
}
