package com.jujaga.e2e.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Scanner;

import org.junit.BeforeClass;
import org.junit.Test;

public class E2EXSDValidatorTest {
	private static String s;

	@SuppressWarnings("resource")
	@BeforeClass
	public static void beforeClass() {
		s = new Scanner(E2EXSDValidatorTest.class.getResourceAsStream("/e2e/validatorTest.xml"), "UTF-8").useDelimiter("\\Z").next();
		assertNotNull(s);
		assertFalse(s.isEmpty());
	}

	@Test
	public void isWellFormedXMLTest() {
		assertTrue(E2EXSDValidator.isWellFormedXML(s));
	}

	@Test
	public void isWellFormedXMLOnNonWellFormedDocumentTest() {
		assertFalse(E2EXSDValidator.isWellFormedXML(s.replace("</ClinicalDocument>", "</clinicalDocument>"), true));
	}

	@Test
	public void testIsValidXML() {
		assertTrue(E2EXSDValidator.isValidXML(s));
	}

	@Test
	public void testIsValidXMLOnNonValidDocument() {
		assertFalse(E2EXSDValidator.isValidXML(s.replace("DOCSECT", "DOXSECT"), true));
	}
}
