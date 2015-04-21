package com.jujaga.e2e.constant;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.PQ;
import org.marc.everest.datatypes.SetOperator;
import org.marc.everest.datatypes.TS;
import org.marc.everest.datatypes.generic.DomainTimingEvent;
import org.marc.everest.datatypes.generic.EIVL;
import org.marc.everest.datatypes.generic.IVL;
import org.marc.everest.datatypes.generic.PIVL;
import org.marc.everest.datatypes.interfaces.ISetComponent;
import org.marc.everest.rmim.uv.cdar2.vocabulary.AdministrativeGender;

public class Mappings {
	private Mappings() {}

	public static final Map<String, AdministrativeGender> genderCode;
	static {
		Map<String, AdministrativeGender> map = new HashMap<String, AdministrativeGender>();
		map.put(Constants.DocumentHeader.MALE_ADMINISTRATIVE_GENDER_CODE, AdministrativeGender.Male);
		map.put(Constants.DocumentHeader.FEMALE_ADMINISTRATIVE_GENDER_CODE, AdministrativeGender.Female);
		map.put(Constants.DocumentHeader.UNDIFFERENTIATED_ADMINISTRATIVE_GENDER_CODE, AdministrativeGender.Undifferentiated);
		genderCode = Collections.unmodifiableMap(map);
	}

	public static final Map<String, String> genderDescription;
	static {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constants.DocumentHeader.MALE_ADMINISTRATIVE_GENDER_CODE, Constants.DocumentHeader.MALE_ADMINISTRATIVE_GENDER_DESCRIPTION);
		map.put(Constants.DocumentHeader.FEMALE_ADMINISTRATIVE_GENDER_CODE, Constants.DocumentHeader.FEMALE_ADMINISTRATIVE_GENDER_DESCRIPTION);
		map.put(Constants.DocumentHeader.UNDIFFERENTIATED_ADMINISTRATIVE_GENDER_CODE, Constants.DocumentHeader.UNDIFFERENTIATED_ADMINISTRATIVE_GENDER_DESCRIPTION);
		genderDescription = Collections.unmodifiableMap(map);
	}

	public static final Map<String, String> languageCode;
	static {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constants.DocumentHeader.HUMANLANGUAGE_ENGLISH_DESCRIPTION, Constants.DocumentHeader.HUMANLANGUAGE_ENGLISH_CODE);
		map.put(Constants.DocumentHeader.HUMANLANGUAGE_FRENCH_DESCRIPTION, Constants.DocumentHeader.HUMANLANGUAGE_FRENCH_CODE);
		languageCode = Collections.unmodifiableMap(map);
	}

	// Mock icd9 description mapping
	public static final Map<String, String> icd9Map;
	static {
		Map<String, String> map = new HashMap<String, String>();
		map.put("428", "HEART FAILURE*");
		map.put("401", "ESSENTIAL HYPERTENSION*");
		map.put("250", "DIABETES MELLITUS*");
		map.put("491", "CHRONIC BRONCHITIS*");
		icd9Map = Collections.unmodifiableMap(map);
	}

	public static final Map <String, String> formCode;
	static {
		Map<String, String> map = new HashMap<String, String>();
		map.put("AEROSOL", Constants.FormCodes.AER.toString());
		map.put("AEROSOL, FOAM", Constants.FormCodes.FOAM.toString());
		map.put("AEROSOL, METERED DOSE", Constants.FormCodes.AER.toString());
		map.put("BAR (CHEWABLE)", Constants.FormCodes.BAR.toString());
		map.put("CAPSULE", Constants.FormCodes.CAP.toString());
		map.put("CAPSULE (CONTROLLED-DELIVERY)", Constants.FormCodes.ERCAP.toString());
		map.put("CAPSULE (DELAYED RELEASE)", Constants.FormCodes.ERCAP.toString());
		map.put("CAPSULE (ENTERIC-COATED)", Constants.FormCodes.ENTCAP.toString());
		map.put("CAPSULE (EXTENDED RELEASE)", Constants.FormCodes.ERCAP.toString());
		map.put("CAPSULE (IMMEDIATE AND DELAYED RELEASE)", Constants.FormCodes.ERCAP.toString());
		map.put("CAPSULE (IMMEDIATE AND EXTENDED RELEASE)", Constants.FormCodes.ERCAP.toString());
		map.put("CAPSULE (IMMEDIATE RELEASE)", Constants.FormCodes.ERCAP.toString());
		map.put("CAPSULE (SUSTAINED-RELEASE)", Constants.FormCodes.ERCAP.toString());
		map.put("CREAM", Constants.FormCodes.CRM.toString());
		map.put("DISC", Constants.FormCodes.DISK.toString());
		map.put("DISC (EXTENDED-RELEASE)", Constants.FormCodes.DISK.toString());
		map.put("DOUCHE", Constants.FormCodes.DOUCHE.toString());
		map.put("DROPS", Constants.FormCodes.DROP.toString());
		map.put("ELIXIR", Constants.FormCodes.ELIXIR.toString());
		map.put("ENEMA", Constants.FormCodes.ENEMA.toString());
		map.put("FILM, SOLUBLE", Constants.FormCodes.PATCH.toString());
		map.put("GAS", Constants.FormCodes.GASINHL.toString());
		map.put("GEL", Constants.FormCodes.GEL.toString());
		map.put("GEL (CONTROLLED-RELEASE)", Constants.FormCodes.GELAPL.toString());
		map.put("GRANULES", Constants.FormCodes.GRAN.toString());
		map.put("GRANULES FOR SOLUTION", Constants.FormCodes.GRAN.toString());
		map.put("GRANULES FOR SUSPENSION", Constants.FormCodes.GRAN.toString());
		map.put("GRANULES FOR SUSPENSION, DELAYED RELEASE", Constants.FormCodes.GRAN.toString());
		map.put("GRANULES FOR SUSPENSION,EXTENDED RELEASE", Constants.FormCodes.GRAN.toString());
		map.put("GUM", Constants.FormCodes.GUM.toString());
		map.put("LOTION", Constants.FormCodes.LTN.toString());
		map.put("LOZENGE", Constants.FormCodes.ORTROCHE.toString());
		map.put("METERED-DOSE AEROSOL", Constants.FormCodes.MDINHL.toString());
		map.put("METERED-DOSE PUMP", Constants.FormCodes.MDINHL.toString());
		map.put("MOUTHWASH/GARGLE", Constants.FormCodes.RINSE.toString());
		map.put("OINTMENT", Constants.FormCodes.OINT.toString());
		map.put("PAD", Constants.FormCodes.PAD.toString());
		map.put("PASTE", Constants.FormCodes.PASTE.toString());
		map.put("PATCH", Constants.FormCodes.PATCH.toString());
		map.put("PATCH (EXTENDED RELEASE)", Constants.FormCodes.PATCH.toString());
		map.put("PELLET", Constants.FormCodes.PELLET.toString());
		map.put("PELLET (DENTAL)", Constants.FormCodes.PELLET.toString());
		map.put("PILL", Constants.FormCodes.PILL.toString());
		map.put("POWDER", Constants.FormCodes.POWD.toString());
		map.put("POWDER (EFFERVESCENT)", Constants.FormCodes.POWD.toString());
		map.put("POWDER (ENTERIC-COATED)", Constants.FormCodes.POWD.toString());
		map.put("POWDER (EXTENDED RELEASE)", Constants.FormCodes.POWD.toString());
		map.put("POWDER (METERED DOSE)", Constants.FormCodes.POWD.toString());
		map.put("POWDER FOR SOLUTION", Constants.FormCodes.POWD.toString());
		map.put("POWDER FOR SUSPENSION", Constants.FormCodes.POWD.toString());
		map.put("POWDER FOR SUSPENSION, SUSTAINED-RELEASE", Constants.FormCodes.POWD.toString());
		map.put("RING (SLOW-RELEASE)", Constants.FormCodes.VAGSUPP.toString());
		map.put("SHAMPOO", Constants.FormCodes.SHMP.toString());
		map.put("SOAP BAR", Constants.FormCodes.BARSOAP.toString());
		map.put("SOLUTION", Constants.FormCodes.SOL.toString());
		map.put("SOLUTION (EXTENDED RELEASE)", Constants.FormCodes.SOL.toString());
		map.put("SOLUTION (LONG-ACTING)", Constants.FormCodes.SOL.toString());
		map.put("SPRAY", Constants.FormCodes.SPRYADAPT.toString());
		map.put("SPRAY, BAG-ON-VALVE", Constants.FormCodes.SPRYADAPT.toString());
		map.put("SPRAY, METERED DOSE", Constants.FormCodes.SPRYADAPT.toString());
		map.put("SUPPOSITORY", Constants.FormCodes.SUPP.toString());
		map.put("SUPPOSITORY (SUSTAINED-RELEASE)", Constants.FormCodes.SUPP.toString());
		map.put("SUSPENSION", Constants.FormCodes.SUSP.toString());
		map.put("SUSPENSION (EXTENDED-RELEASE)", Constants.FormCodes.ERSUSP.toString());
		map.put("SUSPENSION (LENTE)", Constants.FormCodes.ERSUSP.toString());
		map.put("SUSPENSION (ULTRA-LENTE)", Constants.FormCodes.ERSUSP.toString());
		map.put("SWAB", Constants.FormCodes.SWAB.toString());
		map.put("SYRUP", Constants.FormCodes.SYRUP.toString());
		map.put("SYRUP (EXTENDED-RELEASE)", Constants.FormCodes.SYRUP.toString());
		map.put("TABLET", Constants.FormCodes.TAB.toString());
		map.put("TABLET (CHEWABLE)", Constants.FormCodes.TAB.toString());
		map.put("TABLET (COMBINED RELEASE)", Constants.FormCodes.TAB.toString());
		map.put("TABLET (DELAYED AND EXTENDED RELEASE)", Constants.FormCodes.TAB.toString());
		map.put("TABLET (DELAYED-RELEASE)", Constants.FormCodes.TAB.toString());
		map.put("TABLET (EFFERVESCENT)", Constants.FormCodes.TAB.toString());
		map.put("TABLET (ENTERIC-COATED)", Constants.FormCodes.ECTAB.toString());
		map.put("TABLET (EXTENDED-RELEASE)", Constants.FormCodes.ERTAB.toString());
		map.put("TABLET (IMMEDIATE AND DELAYED-RELEASE)", Constants.FormCodes.TAB.toString());
		map.put("TABLET (IMMEDIATE RELEASE)", Constants.FormCodes.TAB.toString());
		map.put("TABLET (ORALLY DISINTEGRATING)", Constants.FormCodes.SLTAB.toString());
		map.put("TABLET (SLOW-RELEASE)", Constants.FormCodes.TAB.toString());
		map.put("TABLET FOR SUSPENSION", Constants.FormCodes.TAB.toString());
		map.put("TINCTURE", Constants.FormCodes.TINC.toString());
		map.put("TOOTHPASTE", Constants.FormCodes.TPASTE.toString());
		map.put("VAGINAL TABLET", Constants.FormCodes.VAGTAB.toString());
		map.put("VAGINAL TABLET, EFFERVESCENT", Constants.FormCodes.VAGTAB.toString());
		map.put("WAFER", Constants.FormCodes.WAFER.toString());
		map.put("WIPE", Constants.FormCodes.SWAB.toString());
		formCode = Collections.unmodifiableMap(map);
	}

	public static final Map<String, ISetComponent<TS>> frequencyInterval;
	static {
		Map<String, ISetComponent<TS>> map = new TreeMap<String, ISetComponent<TS>>(String.CASE_INSENSITIVE_ORDER);

		PIVL<TS> onedt = new PIVL<TS>(null, new PQ(BigDecimal.ONE, Constants.TimeUnit.d.toString()));
		onedt.setOperator(SetOperator.Intersect);
		onedt.setInstitutionSpecified(true);

		PIVL<TS> onehf = new PIVL<TS>(null, new PQ(BigDecimal.ONE, Constants.TimeUnit.h.toString()));
		onehf.setOperator(SetOperator.Intersect);
		onehf.setInstitutionSpecified(false);

		PIVL<TS> onemot = new PIVL<TS>(null, new PQ(BigDecimal.ONE, Constants.TimeUnit.mo.toString()));
		onemot.setOperator(SetOperator.Intersect);
		onemot.setInstitutionSpecified(true);

		PIVL<TS> onewkt = new PIVL<TS>(null, new PQ(BigDecimal.ONE, Constants.TimeUnit.wk.toString()));
		onewkt.setOperator(SetOperator.Intersect);
		onewkt.setInstitutionSpecified(true);

		PIVL<TS> twodt = new PIVL<TS>(null, new PQ(new BigDecimal(2), Constants.TimeUnit.d.toString()));
		twodt.setOperator(SetOperator.Intersect);
		twodt.setInstitutionSpecified(true);

		PIVL<TS> twohf = new PIVL<TS>(null, new PQ(new BigDecimal(2), Constants.TimeUnit.h.toString()));
		twohf.setOperator(SetOperator.Intersect);
		twohf.setInstitutionSpecified(false);

		PIVL<TS> twowkt = new PIVL<TS>(null, new PQ(new BigDecimal(2), Constants.TimeUnit.wk.toString()));
		twowkt.setOperator(SetOperator.Intersect);
		twowkt.setInstitutionSpecified(true);

		PIVL<TS> threehf = new PIVL<TS>(null, new PQ(new BigDecimal(3), Constants.TimeUnit.h.toString()));
		threehf.setOperator(SetOperator.Intersect);
		threehf.setInstitutionSpecified(false);

		PIVL<TS> threemot = new PIVL<TS>(null, new PQ(new BigDecimal(3), Constants.TimeUnit.mo.toString()));
		threemot.setOperator(SetOperator.Intersect);
		threemot.setInstitutionSpecified(true);

		PIVL<TS> fourhf = new PIVL<TS>(null, new PQ(new BigDecimal(4), Constants.TimeUnit.h.toString()));
		fourhf.setOperator(SetOperator.Intersect);
		fourhf.setInstitutionSpecified(false);

		PIVL<TS> sixht = new PIVL<TS>(null, new PQ(new BigDecimal(6), Constants.TimeUnit.h.toString()));
		sixht.setOperator(SetOperator.Intersect);
		sixht.setInstitutionSpecified(true);

		PIVL<TS> sixhf = new PIVL<TS>(null, new PQ(new BigDecimal(6), Constants.TimeUnit.h.toString()));
		sixhf.setOperator(SetOperator.Intersect);
		sixhf.setInstitutionSpecified(false);

		PIVL<TS> eightht = new PIVL<TS>(null, new PQ(new BigDecimal(8), Constants.TimeUnit.h.toString()));
		eightht.setOperator(SetOperator.Intersect);
		eightht.setInstitutionSpecified(true);

		PIVL<TS> eighthf = new PIVL<TS>(null, new PQ(new BigDecimal(8), Constants.TimeUnit.h.toString()));
		eighthf.setOperator(SetOperator.Intersect);
		eighthf.setInstitutionSpecified(false);

		PIVL<TS> twelveht = new PIVL<TS>(null, new PQ(new BigDecimal(12), Constants.TimeUnit.h.toString()));
		twelveht.setOperator(SetOperator.Intersect);
		twelveht.setInstitutionSpecified(true);

		PIVL<TS> twelvehf = new PIVL<TS>(null, new PQ(new BigDecimal(12), Constants.TimeUnit.h.toString()));
		twelvehf.setOperator(SetOperator.Intersect);
		twelvehf.setInstitutionSpecified(false);

		PIVL<TS> twentyfourhf = new PIVL<TS>(null, new PQ(new BigDecimal(24), Constants.TimeUnit.h.toString()));
		twentyfourhf.setOperator(SetOperator.Intersect);
		twentyfourhf.setInstitutionSpecified(false);

		EIVL<TS> hourOfSleep = new EIVL<TS>(DomainTimingEvent.HourOfSleep, null, SetOperator.Intersect);
		// TODO Notify MARC-HI about error "When the Event property implies before, after or between meals the Offset property must not be populated"
		hourOfSleep.setOffset(new IVL<PQ>() {{setNullFlavor(NullFlavor.NotApplicable);}});

		EIVL<TS> betweenDinnerAndSleep = new EIVL<TS>(DomainTimingEvent.BetweenDinnerAndSleep, null, SetOperator.Intersect);

		map.put("daily", onedt);
		map.put("once daily", onedt);
		map.put("OD", onedt);
		map.put("QD", onedt);
		map.put("Q1H", onehf);
		map.put("Q1Month", onemot);
		map.put("monthly", onemot);
		map.put("QM", onemot);
		map.put("weekly", onewkt);
		map.put("Q1Week", onewkt);
		map.put("QOD", twodt);
		map.put("Q2H", twohf);
		map.put("Q2Week", twowkt);
		map.put("Q3Month", threemot);
		map.put("Q4H", fourhf);
		map.put("QID", sixht);
		map.put("4x day", sixht);
		map.put("Q6H", sixhf);
		map.put("TID", eightht);
		map.put("3x day", eightht);
		map.put("3x daily", eightht);
		map.put("4x daily", eightht);
		map.put("Q8H", eighthf);
		map.put("BID", twelveht);
		map.put("twice daily", twelveht);
		map.put("Q12H", twelvehf);
		map.put("Q24H", twentyfourhf);

		// TODO Wait on MARC-HI answer on IVL based PIVL_TS implementation
		map.put("Q1-2H", onehf);
		map.put("Q3-4H", threehf);
		map.put("Q4-6H", fourhf);

		// TODO Resolve QAM to EIVL_TS DomainTimingEvent mapping
		map.put("QAM", onedt);
		map.put("QPM", betweenDinnerAndSleep);
		map.put("QHS", hourOfSleep);
		frequencyInterval = Collections.unmodifiableMap(map);
	}
}
