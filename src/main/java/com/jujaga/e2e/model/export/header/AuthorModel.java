package com.jujaga.e2e.model.export.header;

import org.apache.log4j.Logger;
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
import com.jujaga.emr.PatientExport;
import com.jujaga.emr.dao.ProviderDao;
import com.jujaga.emr.model.Provider;

public class AuthorModel {
	private static Logger log = Logger.getLogger(AuthorModel.class.getName());

	private final Provider provider;

	protected SET<II> ids;
	private SET<TEL> telecoms;
	protected Person person;
	private AuthoringDevice device;

	protected AuthorModel(String providerNo) {
		Provider provider = null;

		try {
			Integer providerId;providerId = Integer.parseInt(providerNo);
			ProviderDao providerDao = new PatientExport().getApplicationContext().getBean(ProviderDao.class);
			provider = providerDao.find(providerId);
		} catch (NumberFormatException e) {
			log.error("Provider " + providerNo + " not found");
		} finally {
			if(provider == null) {
				this.provider = new Provider();
			} else {
				this.provider = provider;
			}
		}

		constructorHelper();
	}

	public AuthorModel(Provider provider) {
		if(provider == null) {
			this.provider = new Provider();
		} else {
			this.provider = provider;
		}

		constructorHelper();
	}

	private void constructorHelper() {
		setIds();
		setTelecoms();
		setPerson();
		setDevice();
	}

	public SET<II> getIds() {
		return ids;
	}

	private void setIds() {
		String providerId = getProviderID();
		II id = new II();
		if(providerId != null && !EverestUtils.isNullorEmptyorWhitespace(providerId)) {
			id.setRoot(Constants.DocumentHeader.BC_MINISTRY_OF_HEALTH_PRACTITIONER_ID_OID);
			id.setAssigningAuthorityName(Constants.DocumentHeader.BC_MINISTRY_OF_HEALTH_PRACTITIONER_NAME);
			id.setExtension(providerId);
		} else {
			id.setNullFlavor(NullFlavor.NoInformation);
		}
		this.ids = new SET<II>(id);
	}

	private String getProviderID() {
		String id = null;
		if(!EverestUtils.isNullorEmptyorWhitespace(provider.getPractitionerNo())) {
			id = provider.getPractitionerNo();
		} else if (!EverestUtils.isNullorEmptyorWhitespace(provider.getOhipNo())) {
			id = provider.getOhipNo();
		} else if (provider.getProviderNo() != null) {
			id = provider.getProviderNo().toString();
		}
		return id;
	}

	public SET<TEL> getTelecoms() {
		return telecoms;
	}

	private void setTelecoms() {
		SET<TEL> telecoms = new SET<TEL>();
		HeaderUtils.addTelecomPart(telecoms, provider.getPhone(), TelecommunicationsAddressUse.Home, TelecomType.TELEPHONE);
		HeaderUtils.addTelecomPart(telecoms, provider.getWorkPhone(), TelecommunicationsAddressUse.WorkPlace, TelecomType.TELEPHONE);
		HeaderUtils.addTelecomPart(telecoms, provider.getEmail(), TelecommunicationsAddressUse.Home, TelecomType.EMAIL);
		if(!telecoms.isEmpty()) {
			this.telecoms = telecoms;
		} else {
			this.telecoms = null;
		}
	}

	public Person getPerson() {
		return person;
	}

	private void setPerson() {
		Person person = new Person();
		SET<PN> names = new SET<PN>();
		HeaderUtils.addNamePart(names, provider.getFirstName(), provider.getLastName(), EntityNameUse.Legal);
		if(names.isEmpty()) {
			PN pn = new PN();
			pn.setNullFlavor(NullFlavor.NoInformation);
			names.add(pn);
		}
		person.setName(names);
		this.person = person;
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
