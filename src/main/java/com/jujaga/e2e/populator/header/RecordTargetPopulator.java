package com.jujaga.e2e.populator.header;

import java.util.ArrayList;
import java.util.Arrays;

import org.marc.everest.datatypes.AD;
import org.marc.everest.datatypes.ADXP;
import org.marc.everest.datatypes.AddressPartType;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.PostalAddressUse;
import org.marc.everest.datatypes.TEL;
import org.marc.everest.datatypes.TelecommunicationsAddressUse;
import org.marc.everest.datatypes.generic.CS;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Patient;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.PatientRole;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.RecordTarget;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ContextControl;

import com.jujaga.e2e.StubRecord;
import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.populator.Populator;
import com.jujaga.e2e.util.EverestUtils;

public class RecordTargetPopulator extends Populator {
	//private final Integer demographicNo;
	private static enum TelecomType {
		EMAIL, TELEPHONE
	}

	public RecordTargetPopulator(Integer demographicNo) {
		//this.demographicNo = demographicNo;
	}

	@Override
	public void populate() {
		RecordTarget recordTarget = new RecordTarget();
		PatientRole patientRole = new PatientRole();

		recordTarget.setContextControlCode(ContextControl.OverridingPropagating);
		recordTarget.setPatientRole(patientRole);

		// id
		II id = new II();
		if(!EverestUtils.isNullorEmptyorWhitespace(StubRecord.Demographic.hin)) {
			id.setRoot(Constants.DocumentHeader.BC_PHN_OID);
			id.setAssigningAuthorityName(Constants.DocumentHeader.BC_PHN_OID_ASSIGNING_AUTHORITY_NAME);
			id.setExtension(StubRecord.Demographic.hin);
		} else {
			id.setNullFlavor(NullFlavor.NoInformation);
		}
		patientRole.setId(new SET<II>(id));

		// address
		ArrayList<ADXP> addrParts = new ArrayList<ADXP>();
		addAddressPart(addrParts, StubRecord.Demographic.address, AddressPartType.Delimiter);
		addAddressPart(addrParts, StubRecord.Demographic.city, AddressPartType.City);
		addAddressPart(addrParts, StubRecord.Demographic.province, AddressPartType.State);
		addAddressPart(addrParts, StubRecord.Demographic.postal, AddressPartType.PostalCode);
		if(!addrParts.isEmpty()) {
			CS<PostalAddressUse> use = new CS<PostalAddressUse>(PostalAddressUse.HomeAddress);
			AD addr = new AD(use, addrParts);
			patientRole.setAddr(new SET<AD>(addr));
		}

		// telecom
		SET<TEL> telecoms = new SET<TEL>();
		addTelecomPart(telecoms, StubRecord.Demographic.phoneHome, TelecommunicationsAddressUse.Home, TelecomType.TELEPHONE);
		addTelecomPart(telecoms, StubRecord.Demographic.phoneWork, TelecommunicationsAddressUse.WorkPlace, TelecomType.TELEPHONE);
		addTelecomPart(telecoms, StubRecord.Demographic.email, TelecommunicationsAddressUse.Home, TelecomType.EMAIL);
		if(!telecoms.isEmpty()) {
			patientRole.setTelecom(telecoms);
		}

		// patient
		Patient patient = new Patient();
		patientRole.setPatient(patient);

		// name
		// administrativeGenderCode
		// birthTime
		// languageCommunication

		clinicalDocument.setRecordTarget(new ArrayList<RecordTarget>(Arrays.asList(recordTarget)));
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
}
