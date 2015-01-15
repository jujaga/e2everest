package com.jujaga.e2e.util;

import java.io.StringWriter;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;

import org.marc.everest.formatters.interfaces.IFormatterGraphResult;
import org.marc.everest.formatters.xml.datatypes.r1.DatatypeFormatter;
import org.marc.everest.formatters.xml.its1.XmlIts1Formatter;
import org.marc.everest.interfaces.IResultDetail;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.marc.everest.xml.XMLStateStreamWriter;

@SuppressWarnings("restriction")
public class EverestUtils {
	// General Everest Utility Functions go here
	// Generate Document Function

	public static String GenerateDocumentToString(ClinicalDocument clinicalDocument, Boolean validation) {
		StringWriter sw = new StringWriter();

		XmlIts1Formatter fmtr = new XmlIts1Formatter();
		fmtr.setValidateConformance(validation);
		fmtr.getGraphAides().add(new DatatypeFormatter());
		//fmtr.registerXSITypeName("POCD_MT000040UV.Observation", null);

		XMLStateStreamWriter xssw = null;
		
		try {
			XMLOutputFactory fact = XMLOutputFactory.newInstance();
			xssw = new XMLStateStreamWriter(fact.createXMLStreamWriter(sw));

			//xssw.writeStartElement("hl7", "ClinicalDocument", "urn:hl7-org:v3");
			/*xssw.writeAttribute("xmlns", "xsi", null, "http://www.w3.org/2001/XMLSchema-instance");
			xssw.writeAttribute("schemaLocation", "http://www.w3.org/2001/XMLSchema-instance", "urn:hl7-org:v3 Schemas/CDA-PITO-E2E.xsd");
			xssw.writeAttribute("xmlns", null, null, "urn:hl7-org:v3");
			xssw.writeAttribute("xmlns", "hl7", null, "urn:hl7-org:v3");
			xssw.writeAttribute("xmlns", "e2e", null, "http://standards.pito.bc.ca/E2E-DTC/cda");
			xssw.writeAttribute("xmlns", "xs", null, "http://www.w3.org/2001/XMLSchema");*/

			IFormatterGraphResult details = fmtr.graph(xssw, clinicalDocument);
			//System.out.println(xssw.getCurrentElementXPath());
			//xssw.writeEndElement();
			xssw.close();
			
			if(validation) {
				for(IResultDetail dtl : details.getDetails()) {
					System.out.printf("%s : %s\r\n", dtl.getType(), dtl.getMessage());
				}
			}
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}

		return sw.toString();
	}
}
