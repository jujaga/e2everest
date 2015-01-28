package com.jujaga.e2e.model.export.header;

import java.util.ArrayList;

import org.marc.everest.datatypes.ADXP;
import org.marc.everest.datatypes.AddressPartType;
import org.marc.everest.datatypes.ENXP;
import org.marc.everest.datatypes.EntityNamePartType;
import org.marc.everest.datatypes.EntityNameUse;
import org.marc.everest.datatypes.PN;
import org.marc.everest.datatypes.TEL;
import org.marc.everest.datatypes.TelecommunicationsAddressUse;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.LanguageCommunication;

import com.jujaga.e2e.constant.Mappings;
import com.jujaga.e2e.constant.Constants.TelecomType;
import com.jujaga.e2e.util.EverestUtils;

class HeaderUtil {
	protected static void addAddressPart(ArrayList<ADXP> addrParts, String value, AddressPartType addressPartType) {
		if(!EverestUtils.isNullorEmptyorWhitespace(value)) {
			ADXP addrPart = new ADXP(value, addressPartType);
			addrParts.add(addrPart);
		}
	}

	protected static void addTelecomPart(SET<TEL> telecoms, String value, TelecommunicationsAddressUse telecomAddressUse, TelecomType telecomType) {
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

	protected static void addNamePart(SET<PN> names, String firstName, String lastName, EntityNameUse entityNameUse) {
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

	protected static void addLanguagePart(ArrayList<LanguageCommunication> languages, String value) {
		if(!EverestUtils.isNullorEmptyorWhitespace(value)) {
			if(Mappings.languageCode.containsKey(value)) {
				LanguageCommunication language = new LanguageCommunication();
				language.setLanguageCode(Mappings.languageCode.get(value));
				languages.add(language);
			}
		}
	}
}
