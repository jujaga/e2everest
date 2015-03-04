package com.jujaga.e2e.populator.body;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;

import com.jujaga.e2e.constant.BodyConstants.Medications;

// TODO MedicationsPopulatorTest
public class MedicationsPopulatorTest extends AbstractBodyPopulatorTest {
	@BeforeClass
	public static void beforeClass() {
		setupClass(Medications.getConstants());
	}

	@Test
	public void medicationsComponentTest() {
		assertNotNull(component);
	}
}
