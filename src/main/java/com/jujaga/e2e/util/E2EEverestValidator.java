package com.jujaga.e2e.util;

import org.apache.log4j.Logger;
import org.marc.everest.formatters.interfaces.IFormatterGraphResult;
import org.marc.everest.interfaces.IResultDetail;
import org.marc.everest.interfaces.ResultDetailType;

public class E2EEverestValidator {
	private static Logger log = Logger.getLogger(E2EEverestValidator.class.getName());

	public static boolean isValidCDA(IFormatterGraphResult details) {
		return isValidCDA(details, false);
	}

	public static boolean isValidCDA(IFormatterGraphResult details, boolean testSuppress) {
		boolean result = true;

		for(IResultDetail dtl : details.getDetails()) {
			if(!testSuppress) {
				if(dtl.getType() == ResultDetailType.ERROR) {
					log.error(dtl.getMessage());
				}
				else if(dtl.getType() == ResultDetailType.WARNING) {
					log.warn(dtl.getMessage());
				}
				else {
					log.info(dtl.getMessage());
				}
			}
			result = false;
		}
		return result;
	}
}
