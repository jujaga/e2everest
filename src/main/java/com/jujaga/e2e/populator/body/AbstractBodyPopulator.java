package com.jujaga.e2e.populator.body;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.SD;
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

		if(!entries.isEmpty()) { // HL7 Level 3
			component.getSection().setEntry(entries);
			clinicalDocument.getComponent().getBodyChoiceIfStructuredBody().getComponent().add(component);
		} else { // HL7 Level 2
			component.getSection().setEntry(null);
			if(bodyConstants.SECTION_PRIORITY == SectionPriority.SHALL) { 
				clinicalDocument.getComponent().getBodyChoiceIfStructuredBody().getComponent().add(component);
			}
		}
	}

	abstract public ClinicalStatement populateClinicalStatement(List<T> list);
	abstract public ClinicalStatement populateNullFlavorClinicalStatement();
	abstract public String populateText();

	private Component3 makeSectionComponent() {
		Component3 component = new Component3();
		component.setTypeCode(ActRelationshipHasComponent.HasComponent);
		component.setContextConductionInd(true);

		Section section = new Section();
		section.setTemplateId(new ArrayList<II>(Arrays.asList(new II(bodyConstants.WITH_ENTRIES_TEMPLATE_ID))));
		section.setCode(new CE<String>(bodyConstants.CODE, bodyConstants.CODE_SYSTEM, Constants.CodeSystems.LOINC_DISPLAY_NAME, null));
		section.setTitle(bodyConstants.WITH_ENTRIES_TITLE);
		section.setText(new SD(new StructDocTextNode(populateText())));

		component.setSection(section);

		return component;
	}
}
