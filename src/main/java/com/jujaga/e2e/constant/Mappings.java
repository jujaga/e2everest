package com.jujaga.e2e.constant;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
}
