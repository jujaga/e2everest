package com.jujaga.e2e.populator.header;

import java.util.ArrayList;
import java.util.Arrays;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.InformationRecipient;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.IntendedRecipient;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_InformationRecipient;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_InformationRecipientRole;

import com.jujaga.e2e.populator.AbstractPopulator;

class InformationRecipientPopulator extends AbstractPopulator {
	InformationRecipientPopulator() {
	}

	@Override
	public void populate() {
		InformationRecipient informationRecipient = new InformationRecipient();
		IntendedRecipient intendedRecipient = new IntendedRecipient();

		// TODO [MARC-HI] Ask about equivalent SuppressXsiNil toggle
		//informationRecipient.setNullFlavor(NullFlavor.NotApplicable);
		informationRecipient.setIntendedRecipient(intendedRecipient);
		informationRecipient.setTypeCode(x_InformationRecipient.PRCP);

		intendedRecipient.setClassCode(x_InformationRecipientRole.HealthChart);

		clinicalDocument.setInformationRecipient(new ArrayList<InformationRecipient>(Arrays.asList(informationRecipient)));
	}
}
