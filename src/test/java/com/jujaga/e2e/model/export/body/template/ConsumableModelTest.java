package com.jujaga.e2e.model.export.body.template;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.datatypes.EN;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.generic.CE;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Consumable;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.LabeledDrug;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ManufacturedProduct;
import org.marc.everest.rmim.uv.cdar2.vocabulary.DrugEntity;
import org.marc.everest.rmim.uv.cdar2.vocabulary.EntityDeterminerDetermined;
import org.marc.everest.rmim.uv.cdar2.vocabulary.RoleClassManufacturedProduct;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jujaga.e2e.constant.BodyConstants.Medications;
import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.model.export.template.ConsumableModel;
import com.jujaga.emr.dao.DrugDao;
import com.jujaga.emr.model.Drug;

public class ConsumableModelTest {
	private static ApplicationContext context;
	private static DrugDao dao;
	private static Drug drug;
	private static ConsumableModel consumableModel;

	@BeforeClass
	public static void beforeClass() {
		context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		dao = context.getBean(DrugDao.class);
		consumableModel = new ConsumableModel();
	}

	@Before
	public void before() {
		drug = dao.findByDemographicId(Constants.Runtime.VALID_DEMOGRAPHIC).get(0);
	}

	@Test
	public void consumableDrugStructureTest() {
		Consumable consumable = consumableModel.getConsumable(drug);
		assertNotNull(consumable);
		assertNotNull(consumable.getTemplateId());
		assertEquals(1, consumable.getTemplateId().size());
		assertEquals(Medications.MEDICATION_IDENTIFICATION_TEMPLATE_ID, consumable.getTemplateId().get(0).getRoot());

		ManufacturedProduct manufacturedProduct = consumable.getManufacturedProduct();
		assertNotNull(manufacturedProduct);
		assertEquals(RoleClassManufacturedProduct.ManufacturedProduct, manufacturedProduct.getClassCode().getCode());

		LabeledDrug labeledDrug = manufacturedProduct.getManufacturedDrugOrOtherMaterialIfManufacturedLabeledDrug();
		assertNotNull(labeledDrug);
		assertEquals(EntityDeterminerDetermined.Described, labeledDrug.getDeterminerCode().getCode());
	}

	@Test
	public void consumableDrugDINCodeTest() {
		LabeledDrug labeledDrug = labeledDrugHelper();

		CE<DrugEntity> code = labeledDrug.getCode();
		assertNotNull(code);
		assertEquals(drug.getRegionalIdentifier().trim(), code.getCode().getCode());
		assertEquals(Constants.CodeSystems.DIN_OID, code.getCodeSystem());
		assertEquals(Constants.CodeSystems.DIN_DISPLAY_NAME, code.getCodeSystemName());
	}

	@Test
	public void consumableDrugATCCodeTest() {
		drug.setRegionalIdentifier(null);
		LabeledDrug labeledDrug = labeledDrugHelper();

		CE<DrugEntity> code = labeledDrug.getCode();
		assertNotNull(code);
		assertEquals(drug.getAtc().trim(), code.getCode().getCode());
		assertEquals(Constants.CodeSystems.ATC_OID, code.getCodeSystem());
		assertEquals(Constants.CodeSystems.ATC_DISPLAY_NAME, code.getCodeSystemName());
	}

	@Test
	public void nullConsumableDrugCodeTest() {
		drug.setRegionalIdentifier(null);
		drug.setAtc(null);
		LabeledDrug labeledDrug = labeledDrugHelper();

		CE<DrugEntity> code = labeledDrug.getCode();
		assertNotNull(code);
		assertTrue(code.isNull());
		assertEquals(NullFlavor.NoInformation, code.getNullFlavor().getCode());
	}

	@Test
	public void consumableDrugGenericNameTest() {
		LabeledDrug labeledDrug = labeledDrugHelper();

		EN name = labeledDrug.getName();
		assertNotNull(name);
		assertNotNull(name.getParts());
		assertEquals(1, name.getParts().size());
		assertEquals(drug.getGenericName(), name.getPart(0).getValue());
	}

	@Test
	public void consumableDrugBrandNameTest() {
		drug.setGenericName(null);
		LabeledDrug labeledDrug = labeledDrugHelper();

		EN name = labeledDrug.getName();
		assertNotNull(name);
		assertNotNull(name.getParts());
		assertEquals(1, name.getParts().size());
		assertEquals(drug.getBrandName(), name.getPart(0).getValue());
	}

	@Test
	public void nullConsumableDrugNameTest() {
		drug.setGenericName(null);
		drug.setBrandName(null);
		LabeledDrug labeledDrug = labeledDrugHelper();

		EN name = labeledDrug.getName();
		assertNotNull(name);
		assertTrue(name.isNull());
		assertEquals(NullFlavor.NoInformation, name.getNullFlavor().getCode());
	}

	private LabeledDrug labeledDrugHelper() {
		Consumable consumable = consumableModel.getConsumable(drug);
		return consumable.getManufacturedProduct().getManufacturedDrugOrOtherMaterialIfManufacturedLabeledDrug();
	}
}
