package com.jujaga.e2e.model.export.body;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.TS;
import org.marc.everest.datatypes.generic.SET;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.constant.Constants.IdPrefixes;
import com.jujaga.emr.PatientExport;
import com.jujaga.emr.dao.DemographicDao;
import com.jujaga.emr.model.Demographic;

public class BodyUtils {
	private static Logger log = Logger.getLogger(BodyUtils.class.getName());

	BodyUtils() {
		throw new UnsupportedOperationException();
	}

	public static SET<II> buildUniqueId(IdPrefixes prefix, Integer id) {
		if(id == null) {
			id = 0;
		}

		StringBuilder sb = new StringBuilder();
		sb.append(prefix).append("-").append(id.toString());

		II ii = new II(Constants.EMR.EMR_OID, sb.toString());
		ii.setAssigningAuthorityName(Constants.EMR.EMR_VERSION);
		return new SET<II>(ii);
	}

	public static TS buildTSFromDate(Date date) {
		if(date == null) {
			return null;
		}

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return new TS(calendar, TS.DAY);
	}

	public static Date stringToDate(String dateString) {
		String[] formatStrings = {"yyyy-MM-dd hh:mm:ss", "yyyy-MM-dd hh:mm", "yyyy-MM-dd"};
		Integer i = 0;
		while(i < formatStrings.length) {
			try {
				return new SimpleDateFormat(formatStrings[i]).parse(dateString);
			} catch (Exception e) {
				i++;
			}
		}

		log.warn("stringToDate - Can't parse dateString");
		return null;
	}

	public static String getDemographicProviderNo(Integer demographicNo) {
		try {
			DemographicDao demographicDao = new PatientExport().getApplicationContext().getBean(DemographicDao.class);
			Demographic demographic = demographicDao.find(demographicNo);
			return demographic.getProviderNo();
		} catch (Exception e) {
			log.error("Demographic " + demographicNo + " not found");
			return null;
		}
	}
}
