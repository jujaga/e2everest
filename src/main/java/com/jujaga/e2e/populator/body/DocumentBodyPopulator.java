package com.jujaga.e2e.populator.body;

import java.util.ArrayList;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Component2;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Component3;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.StructuredBody;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ActRelationshipHasComponent;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_BasicConfidentialityKind;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.populator.AbstractPopulator;
import com.jujaga.emr.PatientExport;

public class DocumentBodyPopulator extends AbstractPopulator {
	public DocumentBodyPopulator(PatientExport patientExport) {
		populators.add(new AdvanceDirectivesPopulator());
		populators.add(new AlertsPopulator(patientExport));
		populators.add(new AllergiesPopulator(patientExport));
		populators.add(new ClinicallyMeasuredObservationsPopulator(patientExport));
		populators.add(new EncountersPopulator(patientExport));
		populators.add(new ImmunizationsPopulator(patientExport));
		populators.add(new LabsPopulator(patientExport));
		populators.add(new MedicationsPopulator(patientExport));
		populators.add(new OrdersAndRequestsPopulator());
		populators.add(new ProblemsPopulator(patientExport));
	}

	@Override
	public void populate() {
		Component2 bodyComponent = new Component2();
		bodyComponent.setTypeCode(ActRelationshipHasComponent.HasComponent);
		bodyComponent.setContextConductionInd(true);
		bodyComponent.setBodyChoice(getStructuredBody());

		clinicalDocument.setComponent(bodyComponent);
	}

	public StructuredBody getStructuredBody() {
		StructuredBody body = new StructuredBody();
		ArrayList<Component3> sectionComponents = new ArrayList<Component3>();

		body.setConfidentialityCode(x_BasicConfidentialityKind.Normal);
		body.setLanguageCode(Constants.DocumentHeader.LANGUAGE_ENGLISH_CANADIAN);
		body.setComponent(sectionComponents);
		return body;
	}
}
