package com.jujaga.e2e.populator.body;

import org.junit.BeforeClass;
import org.junit.Test;

import com.jujaga.e2e.constant.BodyConstants.Alerts;

public class AlertsPopulatorTest extends AbstractBodyPopulatorTest {
	@BeforeClass
	public static void beforeClass() {
		setupClass(Alerts.getConstants());
	}

	@Test
	public void alertsComponentSectionTest() {
		componentSectionTest();
	}

	@Test
	public void alertsEntryCountTest() {
		entryCountTest(0);
	}
}
