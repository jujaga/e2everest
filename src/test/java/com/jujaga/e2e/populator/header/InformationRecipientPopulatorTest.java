package com.jujaga.e2e.populator.header;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.InformationRecipient;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.IntendedRecipient;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.director.E2ECreator;

public class InformationRecipientPopulatorTest {
	private static ClinicalDocument clinicalDocument;

	@BeforeClass
	public static void beforeClass() {
		clinicalDocument = E2ECreator.createEmrConversionDocument(Constants.Runtime.VALID_DEMOGRAPHIC);
	}

	@Test
	public void informationRecipientTest() {
		ArrayList<InformationRecipient> informationRecipients = clinicalDocument.getInformationRecipient();
		assertNotNull(informationRecipients);
		assertEquals(1, informationRecipients.size());
		assertNotNull(informationRecipients.get(0));
	}

	@Test
	public void intendedRecipientTest() {
		IntendedRecipient intendedRecipient = clinicalDocument.getInformationRecipient().get(0).getIntendedRecipient();
		assertNotNull(intendedRecipient);
	}
}
