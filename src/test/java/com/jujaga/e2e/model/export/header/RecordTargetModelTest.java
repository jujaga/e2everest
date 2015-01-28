package com.jujaga.e2e.model.export.header;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.marc.everest.datatypes.AD;
import org.marc.everest.datatypes.ADXP;
import org.marc.everest.datatypes.AddressPartType;
import org.marc.everest.datatypes.ENXP;
import org.marc.everest.datatypes.EntityNamePartType;
import org.marc.everest.datatypes.EntityNameUse;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.PN;
import org.marc.everest.datatypes.PostalAddressUse;
import org.marc.everest.datatypes.TEL;
import org.marc.everest.datatypes.TS;
import org.marc.everest.datatypes.generic.CE;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.LanguageCommunication;
import org.marc.everest.rmim.uv.cdar2.vocabulary.AdministrativeGender;

import com.jujaga.e2e.StubRecord;
import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.constant.Mappings;
import com.jujaga.e2e.util.EverestUtils;

// TODO Handle ignored null test cases
public class RecordTargetModelTest {
	private static RecordTargetModel recordTargetModel;

	@BeforeClass
	public static void beforeClass() {
		Integer demographicNo = StubRecord.Demographic.demographicNo;
		recordTargetModel = new RecordTargetModel(demographicNo);
	}

	@Test
	public void idTest() {
		SET<II> ids = recordTargetModel.getIds();
		assertNotNull(ids);

		II id = ids.get(0);
		assertNotNull(id);
		assertEquals(Constants.DocumentHeader.BC_PHN_OID, id.getRoot());
		assertEquals(Constants.DocumentHeader.BC_PHN_OID_ASSIGNING_AUTHORITY_NAME, id.getAssigningAuthorityName());
		assertFalse(EverestUtils.isNullorEmptyorWhitespace(id.getExtension()));
		assertEquals(StubRecord.Demographic.hin, id.getExtension());
	}

	@Ignore
	@Test
	public void idNullTest() {
		SET<II> ids = recordTargetModel.getIds();
		assertNotNull(ids);

		II id = ids.get(0);
		assertNotNull(id);
		assertTrue(id.isNull());
	}

	@Test
	public void addressFullTest() {
		SET<AD> addrSet = recordTargetModel.getAddresses();
		assertNotNull(addrSet);
		assertEquals(1, addrSet.size());

		AD addr = addrSet.get(0);
		assertNotNull(addr);
		assertEquals(1, addr.getUse().size());
		assertEquals(PostalAddressUse.HomeAddress, addr.getUse().get(0).getCode());

		assertEquals(4, addr.getPart().size());
		assertTrue(addr.getPart().contains(new ADXP(StubRecord.Demographic.address, AddressPartType.Delimiter)));
		assertTrue(addr.getPart().contains(new ADXP(StubRecord.Demographic.city, AddressPartType.City)));
		assertTrue(addr.getPart().contains(new ADXP(StubRecord.Demographic.province, AddressPartType.State)));
		assertTrue(addr.getPart().contains(new ADXP(StubRecord.Demographic.postal, AddressPartType.PostalCode)));
	}

	@Ignore
	@Test
	public void addressNullTest() {
		SET<AD> addrSet = recordTargetModel.getAddresses();
		assertNull(addrSet);
	}

	@Test
	public void telecomFullTest() {
		SET<TEL> telecoms = recordTargetModel.getTelecoms();
		assertNotNull(telecoms);
		assertEquals(3, telecoms.size());

		TEL tel0 = telecoms.get(0);
		assertNotNull(tel0);
		assertTrue(TEL.isValidPhoneFlavor(tel0));
		assertEquals("tel:" + StubRecord.Demographic.phoneHome.replaceAll("-", ""), tel0.getValue());

		TEL tel1 = telecoms.get(1);
		assertNotNull(tel1);
		assertTrue(TEL.isValidPhoneFlavor(tel1));
		assertEquals("tel:" + StubRecord.Demographic.phoneWork.replaceAll("-", ""), tel1.getValue());

		TEL tel2 = telecoms.get(2);
		assertNotNull(tel2);
		assertTrue(TEL.isValidEMailFlavor(tel2));
		assertEquals("mailto:" + StubRecord.Demographic.email, tel2.getValue());
	}

	@Ignore
	@Test
	public void telecomNullTest() {
		SET<TEL> telecoms = recordTargetModel.getTelecoms();
		assertNull(telecoms);
	}

	@Test
	public void nameFullTest() {
		SET<PN> names = recordTargetModel.getNames();
		assertNotNull(names);
		assertEquals(1, names.size());

		PN name = names.get(0);
		assertNotNull(name);
		assertEquals(EntityNameUse.OfficialRecord, name.getUse().get(0).getCode());

		List<ENXP> nameParts = name.getParts();
		assertNotNull(nameParts);
		assertEquals(2, nameParts.size());
		assertTrue(nameParts.contains(new ENXP(StubRecord.Demographic.firstName, EntityNamePartType.Given)));
		assertTrue(nameParts.contains(new ENXP(StubRecord.Demographic.lastName, EntityNamePartType.Family)));
	}

	@Ignore
	@Test
	public void nameNullTest() {
		SET<PN> names = recordTargetModel.getNames();
		assertNull(names);
	}

	@Test
	public void genderTest() {
		CE<AdministrativeGender> gender = recordTargetModel.getGender();
		assertNotNull(gender);
		String sexCode = StubRecord.Demographic.sex.toUpperCase().replace("U", "UN");
		assertEquals(Mappings.genderCode.get(sexCode), gender.getCode());
		assertEquals(Mappings.genderDescription.get(sexCode), gender.getDisplayName());
	}

	@Ignore
	@Test
	public void genderNullTest() {
		CE<AdministrativeGender> gender = recordTargetModel.getGender();
		assertNotNull(gender);
		assertTrue(gender.isNull());
	}

	@Test
	public void birthDateTest() {
		TS birthDate = recordTargetModel.getBirthDate();
		assertNotNull(birthDate);

		Calendar cal = birthDate.getDateValue();
		assertNotNull(cal);
		assertEquals(Integer.parseInt(StubRecord.Demographic.yearOfBirth), cal.get(Calendar.YEAR));
		// Month starts counting from 0, not 1
		assertEquals(Integer.parseInt(StubRecord.Demographic.monthOfBirth)-1, cal.get(Calendar.MONTH));
		assertEquals(Integer.parseInt(StubRecord.Demographic.dateOfBirth), cal.get(Calendar.DATE));
	}

	@Ignore
	@Test
	public void birthDateNullTest() {
		TS birthDate = recordTargetModel.getBirthDate();
		assertNotNull(birthDate);
		assertTrue(birthDate.isNull());
	}

	@Test
	public void languageCommunicationTest() {
		ArrayList<LanguageCommunication> languages = recordTargetModel.getLanguages();
		assertNotNull(languages);

		LanguageCommunication language = languages.get(0);
		assertNotNull(language);
		assertEquals(Mappings.languageCode.get(StubRecord.Demographic.officialLanguage), language.getLanguageCode().getCode());
	}

	@Ignore
	@Test
	public void languageCommunicationNullTest() {
		ArrayList<LanguageCommunication> languages = recordTargetModel.getLanguages();
		assertNull(languages);
	}
}
