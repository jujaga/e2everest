package com.jujaga.e2e.model.export.template;

import java.util.ArrayList;
import java.util.Arrays;

import org.marc.everest.datatypes.EN;
import org.marc.everest.datatypes.ENXP;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.generic.CE;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Consumable;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.LabeledDrug;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ManufacturedProduct;
import org.marc.everest.rmim.uv.cdar2.vocabulary.DrugEntity;
import org.marc.everest.rmim.uv.cdar2.vocabulary.EntityDeterminerDetermined;
import org.marc.everest.rmim.uv.cdar2.vocabulary.RoleClassManufacturedProduct;

import com.jujaga.e2e.constant.BodyConstants.Medications;
import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.util.EverestUtils;
import com.jujaga.emr.model.Drug;

// TODO Consider making this class non-static
public class ConsumableModel {
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
		labeledDrug.setCode(getDrugCode(drug));
		labeledDrug.setName(getDrugName(drug));
		// TODO Add e2e namespace extension fields

		return consumable;
	}

	private static CE<DrugEntity> getDrugCode(Drug drug) {
		CE<DrugEntity> code = new CE<DrugEntity>();

		if(!EverestUtils.isNullorEmptyorWhitespace(drug.getRegionalIdentifier())) {
			code.setCodeEx(new DrugEntity(drug.getRegionalIdentifier(), Constants.CodeSystems.DIN_OID));
			code.setCodeSystemName(Constants.CodeSystems.DIN_DISPLAY_NAME);
		} else if(!EverestUtils.isNullorEmptyorWhitespace(drug.getAtc())) {
			code.setCodeEx(new DrugEntity(drug.getAtc(), Constants.CodeSystems.ATC_OID));
			code.setCodeSystemName(Constants.CodeSystems.ATC_DISPLAY_NAME);
		} else {
			code.setNullFlavor(NullFlavor.NoInformation);
		}

		return code;
	}

	private static EN getDrugName(Drug drug) {
		EN name = new EN();

		if(!EverestUtils.isNullorEmptyorWhitespace(drug.getGenericName())) {
			name.setParts(Arrays.asList(new ENXP(drug.getGenericName())));
		} else if(!EverestUtils.isNullorEmptyorWhitespace(drug.getBrandName())) {
			name.setParts(Arrays.asList(new ENXP(drug.getBrandName())));
		} else {
			name.setNullFlavor(NullFlavor.NoInformation);
		}

		return name;
	}
}
