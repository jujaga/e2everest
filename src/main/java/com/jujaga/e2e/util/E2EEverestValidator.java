package com.jujaga.e2e.util;

import org.apache.log4j.Logger;
import org.marc.everest.formatters.interfaces.IFormatterGraphResult;
import org.marc.everest.interfaces.IResultDetail;

public class E2EEverestValidator {
	private static Logger log = Logger.getLogger(E2EEverestValidator.class.getName());

	public static boolean isValidCDA(IFormatterGraphResult details) {
		return isValidCDA(details, false);
	}

	public static boolean isValidCDA(IFormatterGraphResult details, boolean testSuppress) {
		boolean result = true;

		for(IResultDetail dtl : details.getDetails()) {
			if(!testSuppress) {
				switch(dtl.getType()) {
				case ERROR:
					log.error(dtl.getMessage());
					break;
				case INFORMATION:
					log.warn(dtl.getMessage());
					break;
				case WARNING:
					log.info(dtl.getMessage());
					break;
				default:
					break;
				}
			}

			result = false;
		}

		return result;
	}
}
