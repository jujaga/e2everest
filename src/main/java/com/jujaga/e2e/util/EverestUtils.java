package com.jujaga.e2e.util;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Comparator;

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
import org.marc.everest.formatters.interfaces.IFormatterGraphResult;
import org.marc.everest.formatters.xml.datatypes.r1.DatatypeFormatter;
import org.marc.everest.formatters.xml.its1.XmlIts1Formatter;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.marc.everest.xml.XMLStateStreamWriter;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.emr.model.Drug;

// General Everest Utility Functions
public class EverestUtils {
	private static Logger log = Logger.getLogger(EverestUtils.class.getName());

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
			e.printStackTrace();
			return null;
		}
	}

	// Temporary Everest Bugfixes
	private static String everestBugFixes(String output) {
		// TODO Notify MARC-HI of delimeter/delimiter typo
		return output.replaceAll("delimeter", "delimiter");
	}

	// Pretty Print XML
	public static String prettyFormatXML(String input, Integer indent) {
		if(isNullorEmptyorWhitespace(input)) {
			return null;
		}

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
			return null;
		}
	}

	public static class SortByDin implements Comparator<Drug> {
		public int compare(Drug one, Drug two) {
			int answer;
			try {
				answer = Integer.parseInt(one.getRegionalIdentifier()) - Integer.parseInt(two.getRegionalIdentifier());
			} catch (Exception e) {
				answer = getDrugName(one).compareTo(getDrugName(two));
			}
			return answer;
		}

		private String getDrugName(Drug drug) {
			if(drug.getBrandName() != null) {
				return drug.getBrandName();
			} else if(drug.getGenericName() != null) {
				return drug.getGenericName();
			} else {
				return "";
			}
		}
	}
}
