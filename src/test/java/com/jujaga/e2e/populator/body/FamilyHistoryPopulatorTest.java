package com.jujaga.e2e.populator.body;

import org.junit.BeforeClass;
import org.junit.Test;

import com.jujaga.e2e.constant.BodyConstants.FamilyHistory;

public class FamilyHistoryPopulatorTest extends AbstractBodyPopulatorTest {
	@BeforeClass
	public static void beforeClass() {
		setupClass(FamilyHistory.getConstants());
	}

	@Test
	public void familyHistoryComponentSectionTest() {
		componentSectionTest();
	}

	@Test
	public void familyHistoryEntryCountTest() {
		entryCountTest(0);
	}
}
