package com.jujaga.e2e.util;

import org.marc.everest.formatters.interfaces.IFormatterGraphResult;
import org.marc.everest.formatters.xml.its1.XmlIts1Formatter;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.marc.everest.xml.XMLStateStreamWriter;

public class EverestUtils {
	// General Everest Utility Functions go here
	// Generate Document Function
	
	String GenerateDocumentToString(ClinicalDocument clinicaldocument, Boolean validation) {
		XmlIts1Formatter fmtr = new XmlIts1Formatter();
		fmtr.setValidateConformance(validation);
		fmtr.getGraphAides();
		fmtr.registerXSITypeName("POCD_MT000040UV.Observation", null);
		
		try {
			XMLStateStreamWriter xssw = new XMLStateStreamWriter(null);
			xssw.writeStartElement("hl7", "ClinicalDocument", "urn:hl7-org:v3");
			xssw.writeAttribute("xmlns", "xsi", null, "http://www.w3.org/2001/XMLSchema-instance");
			xssw.writeAttribute("schemaLocation", "http://www.w3.org/2001/XMLSchema-instance", "urn:hl7-org:v3 Schemas/CDA-PITO-E2E.xsd");
			xssw.writeAttribute("xmlns", null, null, "urn:hl7-org:v3");
			xssw.writeAttribute("xmlns", "hl7", null, "urn:hl7-org:v3");
			xssw.writeAttribute("xmlns", "e2e", null, "http://standards.pito.bc.ca/E2E-DTC/cda");
			xssw.writeAttribute("xmlns", "xs", null, "http://www.w3.org/2001/XMLSchema");
			
			IFormatterGraphResult result = null;//fmtr.graph(new XMLStreamWriter(), clinicaldocument);
			xssw.writeEndElement();
			xssw.flush();
		} catch (Exception E) {
			
		}

		
		return null;
	}
}
