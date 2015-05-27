package com.jujaga.e2e.model.export.body;

import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.TS;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.datatypes.generic.IVL;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Participant2;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ParticipantRole;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.PlayingEntity;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ContextControl;
import org.marc.everest.rmim.uv.cdar2.vocabulary.EntityClassRoot;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ParticipationType;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.util.EverestUtils;
import com.jujaga.emr.model.CaseManagementNote;

public class EncountersModel {
	private CaseManagementNote encounter;

	private SET<II> ids;
	private IVL<TS> effectiveTime;
	private Participant2 encounterLocation;

	public EncountersModel(CaseManagementNote encounter) {
		if(encounter == null) {
			this.encounter = new CaseManagementNote();
		} else {
			this.encounter = encounter;
		}

		setIds();
		setEffectiveTime();
		setEncounterLocation();
	}

	public String getTextSummary() {
		StringBuilder sb = new StringBuilder();

		if(encounter.getObservation_date() != null) {
			sb.append(encounter.getObservation_date());
		}
		if(!EverestUtils.isNullorEmptyorWhitespace(encounter.getNote())) {
			sb.append(" ".concat(encounter.getNote().replaceAll("\\\\n", "\n")));
		}

		return sb.toString();
	}

	public SET<II> getIds() {
		return ids;
	}

	private void setIds() {
		this.ids = EverestUtils.buildUniqueId(Constants.IdPrefixes.Encounters, encounter.getId());
	}

	public IVL<TS> getEffectiveTime() {
		return effectiveTime;
	}

	private void setEffectiveTime() {
		IVL<TS> ivl = null;
		TS startTime = EverestUtils.buildTSFromDate(encounter.getObservation_date());
		if(startTime != null) {
			ivl = new IVL<TS>(startTime, null);
		}

		this.effectiveTime = ivl;
	}

	public Participant2 getEncounterLocation() {
		return encounterLocation;
	}

	private void setEncounterLocation() {
		Participant2 participant = new Participant2(ParticipationType.LOC, ContextControl.OverridingPropagating);
		ParticipantRole participantRole = new ParticipantRole(new CD<String>(Constants.RoleClass.SDLOC.toString()));
		PlayingEntity playingEntity = new PlayingEntity(EntityClassRoot.Organization);

		participantRole.setPlayingEntityChoice(playingEntity);
		participant.setParticipantRole(participantRole);

		this.encounterLocation = participant;
	}
}
