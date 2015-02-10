package com.jujaga.e2e.populator.body;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Component3;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.director.E2ECreator;

public abstract class AbstractBodyPopulatorTest {
	protected static ClinicalDocument clinicalDocument;
	protected static ArrayList<Component3> components;

	@BeforeClass
	public static void beforeClass() {
		clinicalDocument = E2ECreator.createEmrConversionDocument(Constants.Runtime.VALID_DEMOGRAPHIC);
		assertNotNull(clinicalDocument);

		components = clinicalDocument.getComponent().getBodyChoiceIfStructuredBody().getComponent();
		assertNotNull(components);
	}
}
