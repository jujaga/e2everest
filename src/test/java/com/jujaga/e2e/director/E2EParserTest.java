package com.jujaga.e2e.director;

import static org.junit.Assert.assertNull;

import org.junit.Test;

public class E2EParserTest {
	@Test(expected=UnsupportedOperationException.class)
	public void instantiationTest() {
		new E2EParser();
	}

	@Test
	public void parseEmrConversionDocumentTest() {
		assertNull(E2EParser.parseEmrConversionDocument());
	}
}
