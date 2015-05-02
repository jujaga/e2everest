package com.jujaga.e2e.model.export.body;

import org.junit.Test;

public class BodyUtilsTest {
	@Test(expected=UnsupportedOperationException.class)
	public void instantiationTest() {
		new BodyUtils();
	}
}
