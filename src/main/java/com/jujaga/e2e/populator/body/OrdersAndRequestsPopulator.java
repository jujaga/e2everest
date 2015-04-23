package com.jujaga.e2e.populator.body;

import java.util.Arrays;
import java.util.List;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalStatement;

import com.jujaga.e2e.constant.BodyConstants.OrdersAndRequests;

public class OrdersAndRequestsPopulator extends AbstractBodyPopulator<OrdersAndRequestsPopulator> {
	OrdersAndRequestsPopulator() {
		bodyConstants = OrdersAndRequests.getConstants();
		populateClinicalStatement(Arrays.asList(this));
	}

	@Override
	public ClinicalStatement populateClinicalStatement(List<OrdersAndRequestsPopulator> list) {
		return null;
	}

	@Override
	public ClinicalStatement populateNullFlavorClinicalStatement() {
		return null;
	}

	@Override
	public List<String> populateText() {
		return null;
	}
}
