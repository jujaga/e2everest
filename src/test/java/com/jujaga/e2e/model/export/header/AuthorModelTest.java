package com.jujaga.e2e.model.export.header;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.marc.everest.datatypes.ENXP;
import org.marc.everest.datatypes.EntityNamePartType;
import org.marc.everest.datatypes.EntityNameUse;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.PN;
import org.marc.everest.datatypes.TEL;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.AuthoringDevice;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Person;

import com.jujaga.e2e.StubRecord;
import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.util.EverestUtils;

// TODO Handle ignored null test cases
public class AuthorModelTest {
	private static AuthorModel authorModel;

	@BeforeClass
	public static void beforeClass() {
		Integer demographicNo = StubRecord.Demographic.demographicNo;
		authorModel = new AuthorModel(demographicNo);
	}

	@Test
	public void idTest() {
		SET<II> ids = authorModel.getIds();
		assertNotNull(ids);

		II id = ids.get(0);
		assertNotNull(id);
		assertEquals(Constants.DocumentHeader.BC_MINISTRY_OF_HEALTH_PRACTITIONER_ID_OID, id.getRoot());
		assertEquals(Constants.DocumentHeader.BC_MINISTRY_OF_HEALTH_PRACTITIONER_NAME, id.getAssigningAuthorityName());
		assertFalse(EverestUtils.isNullorEmptyorWhitespace(id.getExtension()));
		assertEquals(StubRecord.Provider.providerId, id.getExtension());
	}

	@Ignore
	@Test
	public void idNullTest() {
		SET<II> ids = authorModel.getIds();
		assertNotNull(ids);

		II id = ids.get(0);
		assertNotNull(id);
		assertTrue(id.isNull());
	}

	@Test
	public void telecomFullTest() {
		SET<TEL> telecoms = authorModel.getTelecoms();
		assertNotNull(telecoms);
		assertEquals(3, telecoms.size());

		TEL tel0 = telecoms.get(0);
		assertNotNull(tel0);
		assertTrue(TEL.isValidPhoneFlavor(tel0));
		assertEquals("tel:" + StubRecord.Provider.providerHomePhone.replaceAll("-", ""), tel0.getValue());

		TEL tel1 = telecoms.get(1);
		assertNotNull(tel1);
		assertTrue(TEL.isValidPhoneFlavor(tel1));
		assertEquals("tel:" + StubRecord.Provider.providerWorkPhone.replaceAll("-", ""), tel1.getValue());

		TEL tel2 = telecoms.get(2);
		assertNotNull(tel2);
		assertTrue(TEL.isValidEMailFlavor(tel2));
		assertEquals("mailto:" + StubRecord.Provider.providerEmail, tel2.getValue());
	}

	@Ignore
	@Test
	public void telecomNullTest() {
		SET<TEL> telecoms = authorModel.getTelecoms();
		assertNull(telecoms);
	}

	@Test
	public void personFullTest() {
		Person person = authorModel.getPerson();
		assertNotNull(person);

		SET<PN> names = person.getName();
		assertNotNull(names);
		assertEquals(1, names.size());

		PN name = names.get(0);
		assertNotNull(name);
		assertEquals(EntityNameUse.OfficialRecord, name.getUse().get(0).getCode());

		List<ENXP> nameParts = name.getParts();
		assertNotNull(nameParts);
		assertEquals(2, nameParts.size());
		assertTrue(nameParts.contains(new ENXP(StubRecord.Provider.providerFirstName, EntityNamePartType.Given)));
		assertTrue(nameParts.contains(new ENXP(StubRecord.Provider.providerLastName, EntityNamePartType.Family)));
	}

	@Ignore
	@Test
	public void personNullTest() {
		Person person = authorModel.getPerson();
		assertNotNull(person);

		SET<PN> names = person.getName();
		assertNull(names);
	}

	@Test
	public void deviceTest() {
		AuthoringDevice device = authorModel.getDevice();
		assertNotNull(device);	
		assertEquals(Constants.EMR.EMR_VERSION, device.getSoftwareName().getValue());
	}
}
