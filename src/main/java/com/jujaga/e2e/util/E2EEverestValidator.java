package com.jujaga.e2e.util;

import org.apache.log4j.Logger;
import org.marc.everest.formatters.interfaces.IFormatterGraphResult;
import org.marc.everest.interfaces.IResultDetail;

public class E2EEverestValidator {
	private static Logger log = Logger.getLogger(E2EEverestValidator.class.getName());

	E2EEverestValidator() {
		throw new UnsupportedOperationException();
	}

	public static Boolean isValidCDA(IFormatterGraphResult details) {
		Boolean result = true;

		for(IResultDetail dtl : details.getDetails()) {
			switch(dtl.getType()) {
			case ERROR:
				log.error(dtl.getMessage());
				break;
			case INFORMATION:
				log.info(dtl.getMessage());
				break;
			case WARNING:
				log.warn(dtl.getMessage());
				break;
			default:
				break;
			}

			result = false;
		}

		return result;
	}
}
