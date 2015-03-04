package com.jujaga.e2e.populator.body;

import java.util.ArrayList;

import org.marc.everest.datatypes.II;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Component3;

import com.jujaga.e2e.constant.BodyConstants.AbstractBodyConstants;
import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.director.E2ECreator;

public abstract class AbstractBodyPopulatorTest {
	private static ClinicalDocument clinicalDocument;
	private static ArrayList<Component3> components;

	protected static Component3 component;
	protected static AbstractBodyConstants bodyConstants;

	// This must be called in the BeforeClass or things will break
	protected static void setupClass(AbstractBodyConstants constants) {
		clinicalDocument = E2ECreator.createEmrConversionDocument(Constants.Runtime.VALID_DEMOGRAPHIC);
		components = clinicalDocument.getComponent().getBodyChoiceIfStructuredBody().getComponent();
		bodyConstants = constants;

		for(Component3 value : components) {
			if(value.getSection().getTemplateId().contains(new II(bodyConstants.WITH_ENTRIES_TEMPLATE_ID)) ||
					value.getSection().getTemplateId().contains(new II(bodyConstants.WITHOUT_ENTRIES_TEMPLATE_ID))) {
				component = value;
				break;
			}
		}
	}
}
