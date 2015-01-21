package com.jujaga.e2e;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;

// A temporary stub object holding data values necessary to populate a demographic only portion of E2E
public class StubRecord {
	public static Integer demographicNo = 1;
	public static String randomDocGUID = UUID.randomUUID().toString();
	public static String firstName = "John";
	public static String lastName = "Cleese";
	public static Calendar docCreated = new GregorianCalendar();
	public static String hin = "448000001";
	public static String address = "";
	public static String city = "";
	public static String province = "";
	public static String postal = "";
	public static String phoneHome = "";
	public static String phoneWork = "";
	public static String email = "";
	public static String gender = "M";
	public static String genderDesc = "Male";
	public static String birthDate = "19400925"; // not using date object for brevity
	public static String language = "EN";

	public static String providerId = "999998";
	public static String providerHomePhone = "";
	public static String providerWorkPhone = "";
	public static String providerEmail = "";
	public static String providerLastName = "oscardoc";
	public static String providerFirstName = "doctor";

	public static String custodianId = "123456";
	public static String cusstodianClinicName = "McMaster Hospital";
}
