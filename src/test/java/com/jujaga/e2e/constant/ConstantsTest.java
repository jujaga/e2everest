package com.jujaga.e2e.constant;

import org.junit.Test;

public class ConstantsTest {
	@Test(expected=UnsupportedOperationException.class)
	public void bodyConstantsInstantiationTest() {
		new BodyConstants();
	}

	@Test(expected=UnsupportedOperationException.class)
	public void constantsInstantiationTest() {
		new Constants();
	}

	@Test(expected=UnsupportedOperationException.class)
	public void mappingsInstantiationTest() {
		new Mappings();
	}
}
