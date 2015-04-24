package com.jujaga.emr.dao;

import org.springframework.stereotype.Repository;

import com.jujaga.emr.model.Measurement;

@Repository
public class MeasurementDao extends AbstractDao<Measurement> {
	public MeasurementDao() {
		super(Measurement.class);
	}
}
