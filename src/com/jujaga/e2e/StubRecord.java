package com.jujaga.e2e;

import java.util.Date;
import java.util.UUID;

// A temporary stub object holding data values necessary to populate a demographic only portion of E2E
public class StubRecord {
	Integer demographicNo = 1;
	String randomDocGUID = UUID.randomUUID().toString();
	String firstName = "John";
	String lastName = "Cleese";
	Date docCreated = new Date();
	String hin = "448000001";
	String address = "";
	String city = "";
	String province = "";
	String postal = "";
	String phoneHome = "";
	String phoneWork = "";
	String email = "";
	String gender = "M";
	String genderDesc = "Male";
	String birthDate = "19400925"; // not using date object for brevity
	String language = "EN";
	
	String providerId = "999998";
	String providerHomePhone = "";
	String providerWorkPhone = "";
	String providerEmail = "";
	String providerLastName = "oscardoc";
	String providerFirstName = "doctor";
	
	String custodianId = "123456";
	String cusstodianClinicName = "McMaster Hospital";
}
