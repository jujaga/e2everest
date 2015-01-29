package com.jujaga.emr.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Entity implementation class for Entity: Demographic
 *
 */
//@Entity
public class Demographic implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer demographicNo;
	private String title;
	private String lastName;
	private String firstName;
	private String address;
	private String city;
	private String province;
	private String postal;
	private String phone;
	private String phone2;
	private String email;

	private String myOscarUserName;

	private String yearOfBirth;
	private String monthOfBirth;
	private String dateOfBirth;
	private String hin;
	private String ver;

	private String rosterStatus;
	private Date rosterDate;
	private Date rosterTerminationDate;
	private String rosterTerminationReason;

	private String patientStatus;
	private Date patientStatusDate;
	private Date dateJoined;
	private String chartNo;
	private String officialLanguage;
	private String spokenLanguage;
	private String providerNo;

	private String sex;
	private Date endDate;
	private Date effDate;
	private String pcnIndicator;
	private String hcType;
	private Date hcRenewDate;

	private String familyDoctor;
	private String alias;
	private String previousAddress;
	private String children;
	private String sourceOfIncome;
	private String citizenship;
	private String sin;
	private String countryOfOrigin;
	private String newsletter;
	private String anonymous = null;

	private String lastUpdateUser = null;
	private Date lastUpdateDate = new Date();

	public Demographic() {
		super();
	}

}
