package org.oscarehr.e2e.model.export.body;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.datatypes.ANY;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.ST;
import org.marc.everest.datatypes.TS;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.datatypes.generic.IVL;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Observation;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Participant2;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ParticipantRole;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.PlayingEntity;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ActClassObservation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ContextControl;
import org.marc.everest.rmim.uv.cdar2.vocabulary.EntityClassRoot;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ParticipationType;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActMoodDocumentObservation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntryRelationship;
import org.oscarehr.casemgmt.dao.CaseManagementNoteDao;
import org.oscarehr.casemgmt.model.CaseManagementNote;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.model.export.body.EncountersModel;
import org.oscarehr.e2e.util.EverestUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EncountersModelTest {
	private static ApplicationContext context;
	private static CaseManagementNoteDao dao;
	private static CaseManagementNote encounter;
	private static EncountersModel encountersModel;

	private static CaseManagementNote nullEncounter;
	private static EncountersModel nullEncountersModel;

	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);
		context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		dao = context.getBean(CaseManagementNoteDao.class);
		encounter = dao.getNotesByDemographic(Constants.Runtime.VALID_DEMOGRAPHIC.toString()).get(0);
		encountersModel = new EncountersModel(encounter);

		nullEncounter = new CaseManagementNote();
		nullEncountersModel = new EncountersModel(nullEncounter);
	}

	@Test
	public void encountersModelNullTest() {
		assertNotNull(new EncountersModel(null));
	}

	@Test
	public void textSummaryTest() {
		String text = encountersModel.getTextSummary();
		assertNotNull(text);
	}

	@Test
	public void textSummaryNullTest() {
		String text = nullEncountersModel.getTextSummary();
		assertNotNull(text);
	}

	@Test
	public void idTest() {
		SET<II> ids = encountersModel.getIds();
		assertNotNull(ids);

		II id = ids.get(0);
		assertNotNull(id);
		assertEquals(Constants.EMR.EMR_OID, id.getRoot());
		assertEquals(Constants.EMR.EMR_VERSION, id.getAssigningAuthorityName());
		assertFalse(EverestUtils.isNullorEmptyorWhitespace(id.getExtension()));
		assertTrue(id.getExtension().contains(Constants.IdPrefixes.Encounters.toString()));
		assertTrue(id.getExtension().contains(encounter.getId().toString()));
	}

	@Test
	public void idNullTest() {
		SET<II> ids = nullEncountersModel.getIds();
		assertNotNull(ids);
	}

	@Test
	public void effectiveTimeTest() {
		IVL<TS> ivl = encountersModel.getEffectiveTime();
		assertNotNull(ivl);
		assertEquals(EverestUtils.buildTSFromDate(encounter.getObservation_date()), ivl.getLow());
	}

	@Test
	public void effectiveTimeNullTest() {
		IVL<TS> ivl = nullEncountersModel.getEffectiveTime();
		assertNull(ivl);
	}

	@Test
	public void encounterLocationTest() {
		Participant2 participant = encountersModel.getEncounterLocation();
		assertNotNull(participant);
		assertEquals(ParticipationType.LOC, participant.getTypeCode().getCode());
		assertEquals(ContextControl.OverridingPropagating, participant.getContextControlCode().getCode());

		ParticipantRole participantRole = participant.getParticipantRole();
		assertNotNull(participantRole);
		assertEquals(Constants.RoleClass.SDLOC.toString(), participantRole.getClassCode().getCode());

		PlayingEntity playingEntity = participantRole.getPlayingEntityChoiceIfPlayingEntity();
		assertNotNull(playingEntity);
		assertEquals(EntityClassRoot.Organization, playingEntity.getClassCode().getCode());
	}

	@Test
	public void encounterLocationNullTest() {
		Participant2 participant = encountersModel.getEncounterLocation();
		assertNotNull(participant);
		assertEquals(ParticipationType.LOC, participant.getTypeCode().getCode());
		assertEquals(ContextControl.OverridingPropagating, participant.getContextControlCode().getCode());

		ParticipantRole participantRole = participant.getParticipantRole();
		assertNotNull(participantRole);
		assertEquals(Constants.RoleClass.SDLOC.toString(), participantRole.getClassCode().getCode());

		PlayingEntity playingEntity = participantRole.getPlayingEntityChoiceIfPlayingEntity();
		assertNotNull(playingEntity);
		assertEquals(EntityClassRoot.Organization, playingEntity.getClassCode().getCode());
	}

	@Test
	public void encounterProviderTest() {
		Participant2 participant = encountersModel.getEncounterProvider();
		assertNotNull(participant);
	}

	@Test
	public void encounterProviderNullTest() {
		Participant2 participant = nullEncountersModel.getEncounterProvider();
		assertNotNull(participant);
	}

	@Test
	public void encounterNoteTest() {
		EntryRelationship entryRelationship = encountersModel.getEncounterNote();
		assertNotNull(entryRelationship);
		assertEquals(x_ActRelationshipEntryRelationship.SUBJ, entryRelationship.getTypeCode().getCode());
		assertTrue(entryRelationship.getContextConductionInd().toBoolean());

		Observation observation = entryRelationship.getClinicalStatementIfObservation();
		assertNotNull(observation);
		assertEquals(ActClassObservation.OBS, observation.getClassCode().getCode());
		assertEquals(x_ActMoodDocumentObservation.Eventoccurrence, observation.getMoodCode().getCode());

		assertNotNull(observation.getId());
		assertEquals(encountersModel.getIds(), observation.getId());

		CD<String> code = observation.getCode();
		assertNotNull(code);
		assertEquals(Constants.ObservationType.COMMENT.toString(), code.getCode());
		assertEquals(Constants.CodeSystems.OBSERVATIONTYPE_CA_PENDING_OID, code.getCodeSystem());
		assertEquals(Constants.CodeSystems.OBSERVATIONTYPE_CA_PENDING_NAME, code.getCodeSystemName());

		assertNotNull(observation.getEffectiveTime());
		assertEquals(encountersModel.getEffectiveTime(), observation.getEffectiveTime());

		ANY value = observation.getValue();
		assertNotNull(value);
		assertEquals(ST.class, value.getDataType());
		assertNotNull(((ST) value).getValue());
		assertEquals(encounter.getNote().replaceAll("\\\\n", "\n"), ((ST) value).getValue());

		assertNotNull(observation.getAuthor());
		assertFalse(observation.getAuthor().isEmpty());
		assertEquals(1, observation.getAuthor().size());
	}

	@Test
	public void encounterNoteNullTest() {
		EntryRelationship entryRelationship = nullEncountersModel.getEncounterNote();
		assertNull(entryRelationship);
	}
}
