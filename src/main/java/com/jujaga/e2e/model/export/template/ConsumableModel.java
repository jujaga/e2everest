package com.jujaga.e2e.model.export.template;

import java.util.ArrayList;
import java.util.Arrays;

import org.marc.everest.datatypes.II;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Consumable;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.LabeledDrug;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ManufacturedProduct;
import org.marc.everest.rmim.uv.cdar2.vocabulary.EntityDeterminerDetermined;
import org.marc.everest.rmim.uv.cdar2.vocabulary.RoleClassManufacturedProduct;

import com.jujaga.e2e.constant.BodyConstants.Medications;
import com.jujaga.emr.model.Drug;

public class ConsumableModel {
	// TODO Add static entry signature for Immunizations
	/*public static Consumable getConsumable(Immunization immunization) {
		return null;
	}*/

	public static Consumable getConsumable(Drug drug) {
		Consumable consumable = new Consumable();
		ManufacturedProduct manufacturedProduct = new ManufacturedProduct();
		LabeledDrug labeledDrug = new LabeledDrug();

		consumable.setTemplateId(new ArrayList<II>(Arrays.asList(new II(Medications.MEDICATION_IDENTIFICATION_TEMPLATE_ID))));
		consumable.setManufacturedProduct(manufacturedProduct);

		manufacturedProduct.setClassCode(RoleClassManufacturedProduct.ManufacturedProduct);
		manufacturedProduct.setManufacturedDrugOrOtherMaterial(labeledDrug);

		labeledDrug.setDeterminerCode(EntityDeterminerDetermined.Described);
		// TODO CDA only allows one LabeledDrug code CE type - How to represent both DIN and ATC?

		return consumable;
	}
}
