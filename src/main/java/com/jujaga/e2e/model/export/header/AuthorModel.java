package com.jujaga.e2e.model.export.header;

import org.marc.everest.datatypes.EntityNameUse;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.PN;
import org.marc.everest.datatypes.SC;
import org.marc.everest.datatypes.TEL;
import org.marc.everest.datatypes.TelecommunicationsAddressUse;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.AuthoringDevice;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Person;

import com.jujaga.e2e.StubRecord;
import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.constant.Constants.TelecomType;
import com.jujaga.e2e.util.EverestUtils;

public class AuthorModel {
	private SET<II> ids;
	private SET<TEL> telecoms;
	private Person person;
	private AuthoringDevice device;

	public AuthorModel(Integer demographicNo) {
		if(demographicNo <= 0) {
			System.out.println("demographicNo should be greater than 0");
		}

		setIds();
		setTelecoms();
		setPerson();
		setDevice();
	}

	public SET<II> getIds() {
		return ids;
	}

	private void setIds() {
		II id = new II();
		if(!EverestUtils.isNullorEmptyorWhitespace(StubRecord.Demographic.hin)) {
			id.setRoot(Constants.DocumentHeader.BC_MINISTRY_OF_HEALTH_PRACTITIONER_ID_OID);
			id.setAssigningAuthorityName(Constants.DocumentHeader.BC_MINISTRY_OF_HEALTH_PRACTITIONER_NAME);
			// TODO Set extension to cpsid or other "official" number
			id.setExtension(StubRecord.Provider.providerId);
		} else {
			id.setNullFlavor(NullFlavor.NoInformation);
		}
		this.ids = new SET<II>(id);
	}

	public SET<TEL> getTelecoms() {
		return telecoms;
	}

	private void setTelecoms() {
		SET<TEL> telecoms = new SET<TEL>();
		HeaderUtil.addTelecomPart(telecoms, StubRecord.Provider.providerHomePhone, TelecommunicationsAddressUse.Home, TelecomType.TELEPHONE);
		HeaderUtil.addTelecomPart(telecoms, StubRecord.Provider.providerWorkPhone, TelecommunicationsAddressUse.WorkPlace, TelecomType.TELEPHONE);
		HeaderUtil.addTelecomPart(telecoms, StubRecord.Provider.providerEmail, TelecommunicationsAddressUse.Home, TelecomType.EMAIL);
		if(!telecoms.isEmpty()) {
			this.telecoms = telecoms;
		}
		else {
			this.telecoms = null;
		}
	}

	public Person getPerson() {
		return person;
	}

	private void setPerson() {
		Person person = new Person();
		SET<PN> names = new SET<PN>();
		HeaderUtil.addNamePart(names, StubRecord.Provider.providerFirstName, StubRecord.Provider.providerLastName, EntityNameUse.OfficialRecord);
		if(!names.isEmpty()) {
			person.setName(names);
			this.person = person;
		}
		else {
			this.person = null;
		}
	}

	public AuthoringDevice getDevice() {
		return device;
	}

	private void setDevice() {
		AuthoringDevice device = new AuthoringDevice();
		device.setSoftwareName(new SC(Constants.EMR.EMR_VERSION));
		this.device = device;
	}
}
