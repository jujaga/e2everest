package com.jujaga.e2e.model.export.template;

import java.util.Arrays;

import org.marc.everest.datatypes.BL;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.datatypes.generic.CS;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Component4;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Observation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ActRelationshipHasComponent;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ActStatus;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActMoodDocumentObservation;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.util.EverestUtils;
import com.jujaga.emr.PatientExport.LabComponent;

public class ResultComponentModel {
	private LabComponent labComponent;

	public Component4 getComponent(LabComponent labComponent) {
		if(labComponent == null) {
			this.labComponent = new LabComponent(null, null);
		} else {
			this.labComponent = labComponent;
		}

		Component4 component = new Component4(ActRelationshipHasComponent.HasComponent, new BL(true));
		component.setTemplateId(Arrays.asList(new II(Constants.TemplateOids.RESULT_COMPONENT_TEMPLATE_ID)));

		Observation observation = new Observation();
		observation.setMoodCode(x_ActMoodDocumentObservation.Eventoccurrence);
		observation.setId(getIds());
		observation.setCode(getCode());
		// Text
		// EffectiveTime
		observation.setStatusCode(getStatusCode());

		component.setClinicalStatement(observation);
		return component;
	}

	private SET<II> getIds() {
		String accession = labComponent.getMeasurementsMap().get(Constants.MeasurementsExtKeys.accession.toString());
		II ii = new II();
		if(EverestUtils.isNullorEmptyorWhitespace(accession)) {
			ii.setNullFlavor(NullFlavor.NoInformation);
		} else {
			ii.setRoot(Constants.EMR.EMR_OID);
			ii.setExtension(accession);
		}
		return new SET<II>(ii);
	}

	private CD<String> getCode() {
		String identifier = labComponent.getMeasurementsMap().get(Constants.MeasurementsExtKeys.identifier.toString());
		CD<String> code = new CD<String>();
		if(EverestUtils.isNullorEmptyorWhitespace(identifier)) {
			code.setNullFlavor(NullFlavor.NoInformation);
		} else {
			code.setCodeEx(identifier);
			code.setCodeSystem(Constants.CodeSystems.PCLOCD_OID);
			code.setCodeSystemName(Constants.CodeSystems.PCLOCD_NAME);
		}
		return code;
	}

	private CS<ActStatus> getStatusCode() {
		String olis_status = labComponent.getMeasurementsMap().get(Constants.MeasurementsExtKeys.olis_status.toString());
		CS<ActStatus> actStatus = new CS<ActStatus>();
		if(EverestUtils.isNullorEmptyorWhitespace(olis_status)) {
			actStatus.setNullFlavor(NullFlavor.NoInformation);
		} else if(olis_status.equalsIgnoreCase("P")) {
			actStatus.setCodeEx(ActStatus.Active);
		} else {
			actStatus.setCodeEx(ActStatus.Completed);
		}
		return actStatus;
	}
}
