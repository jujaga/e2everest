package com.jujaga.e2e.populator.body;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.SD;
import org.marc.everest.datatypes.doc.StructDocElementNode;
import org.marc.everest.datatypes.doc.StructDocTextNode;
import org.marc.everest.datatypes.generic.CE;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalStatement;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Component3;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Entry;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Section;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ActRelationshipHasComponent;

import com.jujaga.e2e.constant.BodyConstants.AbstractBodyConstants;
import com.jujaga.e2e.constant.BodyConstants.SectionPriority;
import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.populator.AbstractPopulator;

public abstract class AbstractBodyPopulator<T> extends AbstractPopulator {
	protected AbstractBodyConstants bodyConstants;
	protected ArrayList<Entry> entries = new ArrayList<Entry>();

	@Override
	public void populate() {
		Component3 component = makeSectionComponent();

		if(entries.isEmpty()) { // HL7 Level 2
			if(bodyConstants.SECTION_PRIORITY == SectionPriority.SHALL) {
				// TODO Determine how required empty sections should look
				/*Entry entry = new Entry(x_ActRelationshipEntry.DRIV, new BL(true));
				entry.setClinicalStatement(populateNullFlavorClinicalStatement());

				ArrayList<Entry> nullEntries = new ArrayList<Entry>();
				nullEntries.add(entry);

				component.getSection().setEntry(nullEntries);*/
				populateNullFlavorClinicalStatement();
				clinicalDocument.getComponent().getBodyChoiceIfStructuredBody().getComponent().add(component);
			}
		} else { // HL7 Level 3
			component.getSection().setEntry(entries);
			clinicalDocument.getComponent().getBodyChoiceIfStructuredBody().getComponent().add(component);
		}
	}

	abstract public ClinicalStatement populateClinicalStatement(List<T> list);
	abstract public ClinicalStatement populateNullFlavorClinicalStatement();
	abstract public List<String> populateText();

	private Component3 makeSectionComponent() {
		List<String> texts = populateText();
		Component3 component = new Component3();
		component.setTypeCode(ActRelationshipHasComponent.HasComponent);
		component.setContextConductionInd(true);

		Section section = new Section();
		section.setCode(new CE<String>(bodyConstants.CODE, bodyConstants.CODE_SYSTEM, Constants.CodeSystems.LOINC_NAME, null));

		if(texts.isEmpty()) {
			section.setTemplateId(Arrays.asList(new II(bodyConstants.WITHOUT_ENTRIES_TEMPLATE_ID)));
			section.setTitle(bodyConstants.WITHOUT_ENTRIES_TITLE);
			section.setText(new SD(new StructDocTextNode(Constants.SectionSupport.SECTION_SUPPORTED_NO_DATA)));
		} else {
			StructDocElementNode list = new StructDocElementNode("list");
			for(String text : texts) {
				list.addElement("item", text);
			}

			section.setTemplateId(Arrays.asList(new II(bodyConstants.WITH_ENTRIES_TEMPLATE_ID)));
			section.setTitle(bodyConstants.WITH_ENTRIES_TITLE);
			section.setText(new SD(list));
		}

		component.setSection(section);

		return component;
	}
}
