package com.jujaga.e2e.populator.header;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.ST;
import org.marc.everest.datatypes.TS;
import org.marc.everest.datatypes.generic.CE;
import org.marc.everest.datatypes.generic.CS;
import org.marc.everest.datatypes.generic.LIST;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.marc.everest.rmim.uv.cdar2.vocabulary.BindingRealm;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_BasicConfidentialityKind;

import com.jujaga.e2e.StubRecord;
import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.populator.EmrExportPopulator;
import com.jujaga.e2e.populator.Populator;
import com.jujaga.e2e.util.EverestUtils;

public class HeaderPopulatorTest {
	private static Integer demographicNo;
	private static CE<String> code;
	private static II templateId;
	private static ClinicalDocument clinicalDocument;

	@BeforeClass
	public static void beforeClass() {
		demographicNo = StubRecord.Demographic.demographicNo;
		code = Constants.EMRConversionDocument.CODE;
		templateId = new II(Constants.EMRConversionDocument.TEMPLATE_ID);

		Populator populator = new EmrExportPopulator(demographicNo, code, templateId);
		populator.populate();
		clinicalDocument = populator.getClinicalDocument();
	}

	@Test
	public void realmCodeTest() {
		SET<CS<BindingRealm>> realm = clinicalDocument.getRealmCode();
		assertNotNull(realm);

		CS<BindingRealm> binding = clinicalDocument.getRealmCode().get(0);
		assertNotNull(binding);

		BindingRealm code = binding.getCode();
		assertEquals(Constants.DocumentHeader.PITO_E2E_DTC_CLINICAL_DOCUMENT_TYPE_REALM_CODE,code.getCode());
		assertNull(code.getCodeSystem());
	}

	@Test
	public void typeIdTest() {
		II typeId = clinicalDocument.getTypeId();
		assertNotNull(typeId);
		assertEquals(Constants.DocumentHeader.PITO_E2E_DTC_CLINICAL_DOCUMENT_TYPE_ID, typeId.getRoot());
		assertEquals(Constants.DocumentHeader.PITO_E2E_DTC_CLINICAL_DOCUMENT_TYPE_ID_EXTENSION, typeId.getExtension());
	}

	@Test
	public void templateIdTest() {
		LIST<II> templateIds = clinicalDocument.getTemplateId();
		assertNotNull(templateIds);
		assertEquals(2, templateIds.size());
		assertEquals(Constants.DocumentHeader.TEMPLATE_ID, templateIds.get(0).getRoot());
		assertEquals(templateId, templateIds.get(1));
	}

	@Test
	public void idTest() {
		II id = clinicalDocument.getId();
		assertNotNull(id);
		assertEquals(demographicNo.toString(), id.getRoot());
		assertFalse(EverestUtils.isNullorEmptyorWhitespace(id.getExtension()));
	}

	@Test
	public void codeTest() {
		CE<String> code = clinicalDocument.getCode();
		assertNotNull(code);
		assertEquals(HeaderPopulatorTest.code, code);
	}

	@Test
	public void titleTest() {
		ST title = clinicalDocument.getTitle();
		assertNotNull(title);
		assertFalse(EverestUtils.isNullorEmptyorWhitespace(title.getValue()));
	}

	@Test
	public void effectiveTimeTest() {
		TS effectiveTime = clinicalDocument.getEffectiveTime();
		assertNotNull(effectiveTime);
		assertEquals(StubRecord.Demographic.docCreated, effectiveTime.getDateValue());
	}

	@Test
	public void confidentialityCodeTest() {
		CE<x_BasicConfidentialityKind> confidentialityCode = clinicalDocument.getConfidentialityCode();
		assertNotNull(confidentialityCode);
		assertEquals(x_BasicConfidentialityKind.Normal, confidentialityCode.getCode());
	}

	@Test
	public void languageCodeTest() {
		CS<String> languageCode = clinicalDocument.getLanguageCode();
		assertNotNull(languageCode);
		assertEquals(Constants.DocumentHeader.LANGUAGE_ENGLISH_CANADIAN, languageCode.getCode());
	}
}