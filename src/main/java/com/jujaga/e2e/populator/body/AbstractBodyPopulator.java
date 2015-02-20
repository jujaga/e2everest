package com.jujaga.e2e.populator.body;

import java.util.ArrayList;

import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.generic.CE;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Component3;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Entry;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Section;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ActRelationshipHasComponent;

import com.jujaga.e2e.populator.AbstractPopulator;

public abstract class AbstractBodyPopulator extends AbstractPopulator {	
	protected ArrayList<Entry> entries = new ArrayList<Entry>();

	@Override
	public void populate() {
		Component3 component = makeSectionComponent();

		if(!entries.isEmpty()) { // HL7 Level 3
			component.getSection().setEntry(entries);
		}
		else { // HL7 Level 2
			component.getSection().setEntry(null);
		}

		clinicalDocument.getComponent().getBodyChoiceIfStructuredBody().getComponent().add(component);
	}

	private Component3 makeSectionComponent()
	{
		II templateId = new II();
		ArrayList<II> templateIdList = new ArrayList<II>();
		templateIdList.add(templateId);

		Component3 component = new Component3();
		component.setTypeCode(ActRelationshipHasComponent.HasComponent);
		component.setContextConductionInd(true);

		Section section = new Section();
		section.setCode(new CE<String>());
		section.setTitle("section");
		section.setTemplateId(templateIdList);
		section.setText(null);

		component.setSection(section);

		return component;
	}
}
