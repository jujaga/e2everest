package com.jujaga.e2e.populator.body;

import org.junit.BeforeClass;
import org.junit.Test;

import com.jujaga.e2e.constant.BodyConstants.OrdersAndRequests;

public class OrdersAndRequestsPopulatorTest extends AbstractBodyPopulatorTest {
	@BeforeClass
	public static void beforeClass() {
		setupClass(OrdersAndRequests.getConstants());
	}

	@Test
	public void ordersAndRequestsComponentSectionTest() {
		componentSectionTest();
	}

	@Test
	public void ordersAndRequestsEntryCountTest() {
		entryCountTest(0);
	}
}
