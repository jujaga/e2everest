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
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.LanguageCommunication;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Patient;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.PatientRole;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.RecordTarget;
import org.marc.everest.rmim.uv.cdar2.vocabulary.AdministrativeGender;

import com.jujaga.e2e.StubRecord;
import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.constant.Mappings;
import com.jujaga.e2e.populator.EmrExportPopulator;
import com.jujaga.e2e.populator.Populator;
import com.jujaga.e2e.util.EverestUtils;

// TODO Handle Ignored Null Value Test Cases
public class RecordTargetModelTest {
	private static ClinicalDocument clinicalDocument;
	private static PatientRole patientRole;

	@BeforeClass
	public static void beforeClass() {
		Integer demographicNo = StubRecord.Demographic.demographicNo;
		CE<String> code = Constants.EMRConversionDocument.CODE;
		II templateId = new II(Constants.EMRConversionDocument.TEMPLATE_ID);

		Populator populator = new EmrExportPopulator(demographicNo, code, templateId);
		populator.populate();
		clinicalDocument = populator.getClinicalDocument();
		patientRole = clinicalDocument.getRecordTarget().get(0).getPatientRole();
	}

	@Test
	public void recordTargetTest() {
		ArrayList<RecordTarget> recordTargets = clinicalDocument.getRecordTarget();
		assertNotNull(recordTargets);
		assertEquals(1, recordTargets.size());

		RecordTarget recordTarget = recordTargets.get(0);
		assertNotNull(recordTarget);

		assertNotNull(patientRole);
	}

	@Test
	public void idTest() {
		II id = patientRole.getId().get(0);
		assertNotNull(id);
		assertEquals(Constants.DocumentHeader.BC_PHN_OID, id.getRoot());
		assertEquals(Constants.DocumentHeader.BC_PHN_OID_ASSIGNING_AUTHORITY_NAME, id.getAssigningAuthorityName());
		assertFalse(EverestUtils.isNullorEmptyorWhitespace(id.getExtension()));
		assertEquals(StubRecord.Demographic.hin, id.getExtension());
	}

	@Ignore
	@Test
	public void idNullTest() {
		II id = patientRole.getId().get(0);
		assertNotNull(id);
		assertTrue(id.isNull());
	}

	@Test
	public void addressFullTest() {
		SET<AD> addrSet = patientRole.getAddr();
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
		SET<AD> addrSet = patientRole.getAddr();
		assertNull(addrSet);
	}

	@Test
	public void telecomFullTest() {
		SET<TEL> telecoms = patientRole.getTelecom();
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
		SET<TEL> telecoms = patientRole.getTelecom();
		assertNull(telecoms);
	}

	@Test
	public void patientTest() {
		Patient patient = patientRole.getPatient();
		assertNotNull(patient);
	}

	@Test
	public void nameFullTest() {
		SET<PN> names = patientRole.getPatient().getName();
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
		SET<PN> names = patientRole.getPatient().getName();
		assertNull(names);
	}

	@Test
	public void genderTest() {
		CE<AdministrativeGender> gender = patientRole.getPatient().getAdministrativeGenderCode();
		assertNotNull(gender);
		String sexCode = StubRecord.Demographic.sex.toUpperCase().replace("U", "UN");
		assertEquals(Mappings.genderCode.get(sexCode), gender.getCode());
		assertEquals(Mappings.genderDescription.get(sexCode), gender.getDisplayName());
	}

	@Ignore
	@Test
	public void genderNullTest() {
		CE<AdministrativeGender> gender = patientRole.getPatient().getAdministrativeGenderCode();
		assertNotNull(gender);
		assertTrue(gender.isNull());
	}

	@Test
	public void birthDateTest() {
		TS birthDate = patientRole.getPatient().getBirthTime();
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
		TS birthDate = patientRole.getPatient().getBirthTime();
		assertNotNull(birthDate);
		assertTrue(birthDate.isNull());
	}

	@Test
	public void languageCommunicationTest() {
		ArrayList<LanguageCommunication> languages = patientRole.getPatient().getLanguageCommunication();
		assertNotNull(languages);

		LanguageCommunication language = languages.get(0);
		assertNotNull(language);
		assertEquals(Mappings.languageCode.get(StubRecord.Demographic.officialLanguage), language.getLanguageCode().getCode());
	}

	@Ignore
	@Test
	public void languageCommunicationNullTest() {
		ArrayList<LanguageCommunication> languages = patientRole.getPatient().getLanguageCommunication();
		assertNull(languages);
	}
}