package com.jujaga.e2e.director;

import org.junit.Test;

public class E2EParserTest {
	@Test(expected=UnsupportedOperationException.class)
	public void instantiationTest() {
		new E2EParser();
	}

	@Test(expected=UnsupportedOperationException.class)
	public void parseEmrConversionDocumentTest() {
		E2EParser.parseEmrConversionDocument();
	}
}
