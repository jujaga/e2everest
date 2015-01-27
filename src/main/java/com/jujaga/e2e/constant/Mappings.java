package com.jujaga.e2e.constant;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.marc.everest.rmim.uv.cdar2.vocabulary.AdministrativeGender;

public class Mappings {
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
}
