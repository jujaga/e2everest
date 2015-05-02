package com.jujaga.e2e.director;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.jujaga.e2e.constant.Constants;

public class E2ECreatorTest {
	@Test(expected=UnsupportedOperationException.class)
	public void instantiationTest() {
		new E2ECreator();
	}

	@Test
	public void createEmrConversionDocumentTest() {
		assertNotNull(E2ECreator.createEmrConversionDocument(Constants.Runtime.VALID_DEMOGRAPHIC));
	}

	@Test
	public void emptyCreateEmrConversionDocumentTest() {
		assertNotNull(E2ECreator.createEmrConversionDocument(Constants.Runtime.EMPTY_DEMOGRAPHIC));
	}
}
