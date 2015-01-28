package com.jujaga.e2e.model.export.header;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.marc.everest.datatypes.ENXP;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.ON;
import org.marc.everest.datatypes.generic.SET;

import com.jujaga.e2e.StubRecord;
import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.util.EverestUtils;

// TODO Handle ignored null test cases
public class CustodianModelTest {
	private static CustodianModel custodianModel;

	@BeforeClass
	public static void beforeClass() {
		Integer demographicNo = StubRecord.Demographic.demographicNo;
		custodianModel = new CustodianModel(demographicNo);
	}

	@Test
	public void idTest() {
		SET<II> ids = custodianModel.getIds();
		assertNotNull(ids);

		II id = ids.get(0);
		assertNotNull(id);
		assertEquals(Constants.EMR.EMR_OID, id.getRoot());
		assertEquals(Constants.EMR.EMR_VERSION, id.getAssigningAuthorityName());
		assertFalse(EverestUtils.isNullorEmptyorWhitespace(id.getExtension()));
		assertEquals(StubRecord.Custodian.custodianId, id.getExtension());
	}

	@Ignore
	@Test
	public void idNullTest() {
		SET<II> ids = custodianModel.getIds();
		assertNotNull(ids);

		II id = ids.get(0);
		assertNotNull(id);
		assertTrue(id.isNull());
	}

	@Test
	public void nameTest() {
		ON on = custodianModel.getName();
		assertNotNull(on);

		ENXP name = on.getPart(0);
		assertNotNull(name);
		assertFalse(EverestUtils.isNullorEmptyorWhitespace(StubRecord.Custodian.cusstodianClinicName));
		assertEquals(StubRecord.Custodian.cusstodianClinicName, name.getValue());
	}

	@Ignore
	@Test
	public void nameNullTest() {
		ON on = custodianModel.getName();
		assertNull(on);
	}
}
