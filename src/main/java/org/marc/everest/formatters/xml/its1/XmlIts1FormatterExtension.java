package org.marc.everest.formatters.xml.its1;

import javax.xml.stream.XMLStreamException;

import org.marc.everest.formatters.FormatterUtil;
import org.marc.everest.interfaces.IGraphable;
import org.marc.everest.xml.XMLStateStreamWriter;

public class XmlIts1FormatterExtension extends XmlIts1Formatter {
	/**
	 * Write a null flavor to the wire
	 * @throws XMLStreamException
	 */
	@Override
	void writeNullFlavorUtil(XMLStateStreamWriter xw, IGraphable nullFlavor) throws XMLStreamException {
		//this.throwIfDisposed();
		//xw.writeAttribute("xsi", XmlIts1Formatter.NS_XSI, "nil", "true");
		xw.writeAttribute("nullFlavor", FormatterUtil.toWireFormat(nullFlavor));
	}
}
