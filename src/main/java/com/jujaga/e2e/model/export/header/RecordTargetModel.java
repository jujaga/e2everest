package com.jujaga.e2e.model.export.header;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.marc.everest.datatypes.AD;
import org.marc.everest.datatypes.ADXP;
import org.marc.everest.datatypes.AddressPartType;
import org.marc.everest.datatypes.ENXP;
import org.marc.everest.datatypes.EntityNamePartType;
import org.marc.everest.datatypes.EntityNameUse;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.PN;
import org.marc.everest.datatypes.PostalAddressUse;
import org.marc.everest.datatypes.TEL;
import org.marc.everest.datatypes.TS;
import org.marc.everest.datatypes.TelecommunicationsAddressUse;
import org.marc.everest.datatypes.generic.CE;
import org.marc.everest.datatypes.generic.CS;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.LanguageCommunication;
import org.marc.everest.rmim.uv.cdar2.vocabulary.AdministrativeGender;

import com.jujaga.e2e.StubRecord;
import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.constant.Constants.TelecomType;
import com.jujaga.e2e.constant.Mappings;
import com.jujaga.e2e.util.EverestUtils;

public class RecordTargetModel {
	private SET<II> ids;
	private SET<AD> addresses;
	private SET<TEL> telecoms;
	private SET<PN> names;
	private CE<AdministrativeGender> gender;
	private TS birthDate;
	private ArrayList<LanguageCommunication> languages;

	public RecordTargetModel(Integer demographicNo) {
		if(demographicNo <= 0) {
			System.out.println("demographicNo should be greater than 0");
		}

		setIds();
		setAddresses();
		setTelecoms();
		setNames();
		setGender();
		setBirthDate();
		setLanguages();
	}

	public SET<II> getIds() {
		return ids;
	}

	private void setIds() {
		II id = new II();
		if(!EverestUtils.isNullorEmptyorWhitespace(StubRecord.Demographic.hin)) {
			id.setRoot(Constants.DocumentHeader.BC_PHN_OID);
			id.setAssigningAuthorityName(Constants.DocumentHeader.BC_PHN_OID_ASSIGNING_AUTHORITY_NAME);
			id.setExtension(StubRecord.Demographic.hin);
		} else {
			id.setNullFlavor(NullFlavor.NoInformation);
		}
		this.ids = new SET<II>(id);
	}

	public SET<AD> getAddresses() {
		return addresses;
	}

	private void setAddresses() {
		ArrayList<ADXP> addrParts = new ArrayList<ADXP>();
		addAddressPart(addrParts, StubRecord.Demographic.address, AddressPartType.Delimiter);
		addAddressPart(addrParts, StubRecord.Demographic.city, AddressPartType.City);
		addAddressPart(addrParts, StubRecord.Demographic.province, AddressPartType.State);
		addAddressPart(addrParts, StubRecord.Demographic.postal, AddressPartType.PostalCode);
		if(!addrParts.isEmpty()) {
			CS<PostalAddressUse> use = new CS<PostalAddressUse>(PostalAddressUse.HomeAddress);
			AD addr = new AD(use, addrParts);
			this.addresses = new SET<AD>(addr);
		}
		else {
			this.addresses = null;
		}
	}

	public SET<TEL> getTelecoms() {
		return telecoms;
	}

	private void setTelecoms() {
		SET<TEL> telecoms = new SET<TEL>();
		addTelecomPart(telecoms, StubRecord.Demographic.phoneHome, TelecommunicationsAddressUse.Home, TelecomType.TELEPHONE);
		addTelecomPart(telecoms, StubRecord.Demographic.phoneWork, TelecommunicationsAddressUse.WorkPlace, TelecomType.TELEPHONE);
		addTelecomPart(telecoms, StubRecord.Demographic.email, TelecommunicationsAddressUse.Home, TelecomType.EMAIL);
		if(!telecoms.isEmpty()) {
			this.telecoms = telecoms;
		}
		else {
			this.telecoms = null;
		}
	}

	public SET<PN> getNames() {
		return names;
	}

	private void setNames() {
		SET<PN> names = new SET<PN>();
		addNamePart(names, StubRecord.Demographic.firstName, StubRecord.Demographic.lastName, EntityNameUse.OfficialRecord);
		if(!names.isEmpty()) {
			this.names = names;
		}
		else {
			this.names = null;
		}
	}

	public CE<AdministrativeGender> getGender() {
		return gender;
	}

	private void setGender() {
		CE<AdministrativeGender> gender = new CE<AdministrativeGender>();
		String sexCode = StubRecord.Demographic.sex.toUpperCase().replace("U", "UN");
		if(Mappings.genderCode.containsKey(sexCode)) {
			gender.setCodeEx(Mappings.genderCode.get(sexCode));
			gender.setDisplayName(Mappings.genderDescription.get(sexCode));
		}
		else {
			gender.setNullFlavor(NullFlavor.NoInformation);
		}
		this.gender = gender;
	}

	public TS getBirthDate() {
		return birthDate;
	}

	private void setBirthDate() {
		String dob = StubRecord.Demographic.yearOfBirth + StubRecord.Demographic.monthOfBirth + StubRecord.Demographic.dateOfBirth;
		Calendar cal = Calendar.getInstance();
		TS birthDate = new TS();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			cal.setTime(sdf.parse(dob));
			birthDate.setDateValue(cal);
			birthDate.setDateValuePrecision(TS.DAY);
		} catch (ParseException e) {
			e.printStackTrace();
			birthDate.setNullFlavor(NullFlavor.NoInformation);
		}
		this.birthDate = birthDate;
	}

	public ArrayList<LanguageCommunication> getLanguages() {
		return languages;
	}

	private void setLanguages() {
		ArrayList<LanguageCommunication> languages = new ArrayList<LanguageCommunication>();
		addLanguagePart(languages, StubRecord.Demographic.officialLanguage);
		if(!languages.isEmpty()) {
			this.languages = languages;
		}
		else {
			this.languages = null;
		}

	}

	private void addAddressPart(ArrayList<ADXP> addrParts, String value, AddressPartType addressPartType) {
		if(!EverestUtils.isNullorEmptyorWhitespace(value)) {
			ADXP addrPart = new ADXP(value, addressPartType);
			addrParts.add(addrPart);
		}
	}

	private void addTelecomPart(SET<TEL> telecoms, String value, TelecommunicationsAddressUse telecomAddressUse, TelecomType telecomType) {
		if(!EverestUtils.isNullorEmptyorWhitespace(value)) {
			switch(telecomType) {
			case TELEPHONE:
				telecoms.add(new TEL("tel:" + value.replaceAll("-", ""), telecomAddressUse));
				break;
			case EMAIL:
				telecoms.add(new TEL("mailto:" + value, telecomAddressUse));
				break;
			default:
				break;
			}
		}
	}

	private void addNamePart(SET<PN> names, String firstName, String lastName, EntityNameUse entityNameUse) {
		ArrayList<ENXP> name = new ArrayList<ENXP>();
		if(!EverestUtils.isNullorEmptyorWhitespace(firstName)) {
			name.add(new ENXP(firstName, EntityNamePartType.Given));
		}
		if(!EverestUtils.isNullorEmptyorWhitespace(lastName)) {
			name.add(new ENXP(lastName, EntityNamePartType.Family));
		}
		if(!name.isEmpty()) {
			names.add(new PN(entityNameUse, name));
		}
	}

	private void addLanguagePart(ArrayList<LanguageCommunication> languages, String value) {
		if(!EverestUtils.isNullorEmptyorWhitespace(value)) {
			if(Mappings.languageCode.containsKey(value)) {
				LanguageCommunication language = new LanguageCommunication();
				language.setLanguageCode(Mappings.languageCode.get(value));
				languages.add(language);
			}
		}
	}
}
