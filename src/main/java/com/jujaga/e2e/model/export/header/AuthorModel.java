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

import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.constant.Constants.TelecomType;
import com.jujaga.e2e.util.EverestUtils;
import com.jujaga.emr.model.Provider;

public class AuthorModel {
	private final Provider provider;

	private SET<II> ids;
	private SET<TEL> telecoms;
	private Person person;
	private AuthoringDevice device;

	public AuthorModel(Provider provider) {
		this.provider = provider;
		if(this.provider != null) {
			setIds();
			setTelecoms();
			setPerson();
			setDevice();
		}
	}

	public SET<II> getIds() {
		return ids;
	}

	// TODO Set extension to cpsid or other "official" number
	private void setIds() {
		II id = new II();
		if(provider.getProviderNo() != null && !EverestUtils.isNullorEmptyorWhitespace(provider.getProviderNo().toString())) {
			id.setRoot(Constants.DocumentHeader.BC_MINISTRY_OF_HEALTH_PRACTITIONER_ID_OID);
			id.setAssigningAuthorityName(Constants.DocumentHeader.BC_MINISTRY_OF_HEALTH_PRACTITIONER_NAME);
			id.setExtension(provider.getProviderNo().toString());
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
		HeaderUtil.addTelecomPart(telecoms, provider.getPhone(), TelecommunicationsAddressUse.Home, TelecomType.TELEPHONE);
		HeaderUtil.addTelecomPart(telecoms, provider.getWorkPhone(), TelecommunicationsAddressUse.WorkPlace, TelecomType.TELEPHONE);
		HeaderUtil.addTelecomPart(telecoms, provider.getEmail(), TelecommunicationsAddressUse.Home, TelecomType.EMAIL);
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
		HeaderUtil.addNamePart(names, provider.getFirstName(), provider.getLastName(), EntityNameUse.OfficialRecord);
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
