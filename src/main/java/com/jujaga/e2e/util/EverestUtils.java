package com.jujaga.e2e.util;

import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.marc.everest.datatypes.ADXP;
import org.marc.everest.datatypes.AddressPartType;
import org.marc.everest.datatypes.ENXP;
import org.marc.everest.datatypes.EntityNamePartType;
import org.marc.everest.datatypes.EntityNameUse;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.PN;
import org.marc.everest.datatypes.TEL;
import org.marc.everest.datatypes.TS;
import org.marc.everest.datatypes.TelecommunicationsAddressUse;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.formatters.interfaces.IFormatterGraphResult;
import org.marc.everest.formatters.xml.datatypes.r1.DatatypeFormatter;
import org.marc.everest.formatters.xml.its1.XmlIts1Formatter;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.LanguageCommunication;
import org.marc.everest.xml.XMLStateStreamWriter;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.constant.Constants.IdPrefixes;
import com.jujaga.e2e.constant.Constants.TelecomType;
import com.jujaga.e2e.constant.Mappings;
import com.jujaga.emr.PatientExport;
import com.jujaga.emr.dao.DemographicDao;
import com.jujaga.emr.model.Demographic;

public class EverestUtils {
	private static Logger log = Logger.getLogger(EverestUtils.class.getName());

	EverestUtils() {
		throw new UnsupportedOperationException();
	}

	/**
	 * General Everest Utility Functions
	 */
	// Check String for Null, Empty or Whitespace
	public static Boolean isNullorEmptyorWhitespace(String obj) {
		return obj == null || obj.isEmpty() || obj.trim().isEmpty();
	}

	// Generate Document Function
	public static String generateDocumentToString(ClinicalDocument clinicalDocument, Boolean validation) {
		StringWriter sw = new StringWriter();

		XmlIts1Formatter fmtr = new XmlIts1Formatter();
		fmtr.setValidateConformance(validation);
		fmtr.getGraphAides().add(new DatatypeFormatter());

		if(clinicalDocument == null) {
			return null;
		}

		try {
			XMLOutputFactory fact = XMLOutputFactory.newInstance();
			XMLStateStreamWriter xssw = new XMLStateStreamWriter(fact.createXMLStreamWriter(sw));

			xssw.writeStartDocument(Constants.XML.ENCODING, Constants.XML.VERSION);
			xssw.writeStartElement("hl7", "ClinicalDocument", "urn:hl7-org:v3");
			xssw.writeDefaultNamespace("urn:hl7-org:v3"); // Default hl7 namespace
			xssw.writeAttribute("xmlns", "xs", "xs", "http://www.w3.org/2001/XMLSchema");
			xssw.writeAttribute("xmlns", "xsi", "xsi", "http://www.w3.org/2001/XMLSchema-instance");
			xssw.writeAttribute("xmlns", "hl7", "hl7", "urn:hl7-org:v3");
			xssw.writeAttribute("xmlns", "e2e", "e2e", "http://standards.pito.bc.ca/E2E-DTC/cda");
			xssw.writeAttribute("xsi", "schemaLocation", "schemaLocation", "urn:hl7-org:v3 Schemas/CDA-PITO-E2E.xsd");

			IFormatterGraphResult details = fmtr.graph(xssw, clinicalDocument);

			xssw.writeEndElement();
			xssw.writeEndDocument();
			xssw.close();

			String output = prettyFormatXML(sw.toString(), Constants.XML.INDENT).replaceFirst("<Clin", "\n<Clin");

			// Temporary Everest Bugfixes
			output = everestBugFixes(output);

			if(validation) {
				E2EEverestValidator.isValidCDA(details);
				E2EXSDValidator.isValidXML(output);
			}

			return output;
		} catch (XMLStreamException e) {
			log.error(e.toString());
			return null;
		}
	}

	// Temporary Everest Bugfixes
	private static String everestBugFixes(String output) {
		// TODO Ask MARC-HI about equivalent SuppressXsiNil toggle
		String result = output.replaceAll("xsi:nil=\"true\" ", "");
		return result.replaceAll("delimeter", "delimiter");
	}

	// Pretty Print XML
	public static String prettyFormatXML(String input, Integer indent) {
		if(!isNullorEmptyorWhitespace(input)) {
			try {
				Source xmlInput = new StreamSource(new StringReader(input));
				StreamResult xmlOutput = new StreamResult(new StringWriter());

				Transformer tf = TransformerFactory.newInstance().newTransformer();
				tf.setOutputProperty(OutputKeys.ENCODING, Constants.XML.ENCODING);
				tf.setOutputProperty(OutputKeys.INDENT, "yes");
				tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", String.valueOf(indent));
				tf.transform(xmlInput, xmlOutput);

				return xmlOutput.getWriter().toString();
			} catch (TransformerException e) {
				log.error(e.toString());
			}
		}

		return null;
	}

	/**
	 * Header Utility Functions
	 */
	// Add an address to the addrParts list
	public static void addAddressPart(ArrayList<ADXP> addrParts, String value, AddressPartType addressPartType) {
		if(!isNullorEmptyorWhitespace(value)) {
			ADXP addrPart = new ADXP(value, addressPartType);
			addrParts.add(addrPart);
		}
	}

	// Add a telecom element to the telecoms set
	public static void addTelecomPart(SET<TEL> telecoms, String value, TelecommunicationsAddressUse telecomAddressUse, TelecomType telecomType) {
		if(!isNullorEmptyorWhitespace(value)) {
			switch(telecomType) {
			case TELEPHONE:
				telecoms.add(new TEL(Constants.DocumentHeader.TEL_PREFIX + value.replaceAll("-", ""), telecomAddressUse));
				break;
			case EMAIL:
				telecoms.add(new TEL(Constants.DocumentHeader.EMAIL_PREFIX + value, telecomAddressUse));
				break;
			default:
				break;
			}
		}
	}

	// Add a name to the names set
	public static void addNamePart(SET<PN> names, String firstName, String lastName, EntityNameUse entityNameUse) {
		ArrayList<ENXP> name = new ArrayList<ENXP>();
		if(!isNullorEmptyorWhitespace(firstName)) {
			name.add(new ENXP(firstName, EntityNamePartType.Given));
		}
		if(!isNullorEmptyorWhitespace(lastName)) {
			name.add(new ENXP(lastName, EntityNamePartType.Family));
		}
		if(!name.isEmpty()) {
			names.add(new PN(entityNameUse, name));
		}
	}

	// Add a language to the languages list
	public static void addLanguagePart(ArrayList<LanguageCommunication> languages, String value) {
		if(!isNullorEmptyorWhitespace(value) && Mappings.languageCode.containsKey(value)) {
			LanguageCommunication language = new LanguageCommunication();
			language.setLanguageCode(Mappings.languageCode.get(value));
			languages.add(language);
		}
	}

	/**
	 * Body Utility Functions
	 */
	// Create Prefix-number id object
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

	// Create a TS object from a Java Date
	public static TS buildTSFromDate(Date date) {
		if(date == null) {
			return null;
		}

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return new TS(calendar, TS.DAY);
	}

	// Create a Date object from dateString
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

	// Find the provider of a given demographicNo
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
