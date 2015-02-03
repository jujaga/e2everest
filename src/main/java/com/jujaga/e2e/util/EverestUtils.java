package com.jujaga.e2e.util;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.marc.everest.formatters.interfaces.IFormatterGraphResult;
import org.marc.everest.formatters.xml.datatypes.r1.DatatypeFormatter;
import org.marc.everest.formatters.xml.its1.XmlIts1Formatter;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.marc.everest.xml.XMLStateStreamWriter;

import com.jujaga.e2e.constant.Constants;

// General Everest Utility Functions
public class EverestUtils {
	// Check String for Null, Empty or Whitespace
	public static boolean isNullorEmptyorWhitespace(String obj) {
		return obj == null || obj.isEmpty() || obj.trim().isEmpty();
	}

	// Generate Document Function
	public static String generateDocumentToString(ClinicalDocument clinicalDocument, Boolean validation) {
		StringWriter sw = new StringWriter();

		XmlIts1Formatter fmtr = new XmlIts1Formatter();
		fmtr.setValidateConformance(validation);
		fmtr.getGraphAides().add(new DatatypeFormatter());

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
	public static String prettyFormatXML(String input, int indent) {
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
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
