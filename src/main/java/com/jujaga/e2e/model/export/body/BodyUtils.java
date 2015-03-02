package com.jujaga.e2e.model.export.body;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.TS;
import org.marc.everest.datatypes.generic.SET;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.constant.Constants.IdPrefixes;

public class BodyUtils {
	private BodyUtils() {}

	public static SET<II> buildUniqueId(IdPrefixes prefix, Integer id) {
		if(id == null) {
			id = 0;
		}

		StringBuilder sb = new StringBuilder();
		sb.append(prefix).append("-").append(id.toString());

		II ii = new II();
		ii.setRoot(Constants.EMR.EMR_OID);
		ii.setAssigningAuthorityName(Constants.EMR.EMR_VERSION);
		ii.setExtension(sb.toString());
		return new SET<II>(ii);
	}

	public static TS buildTSFromDate(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return new TS(calendar, TS.DAY);
	}
}
