package com.jujaga.emr.dao;

import org.springframework.stereotype.Repository;

import com.jujaga.emr.model.MeasurementsExt;

@Repository
public class MeasurementsExtDao extends AbstractDao<MeasurementsExt> {
	public MeasurementsExtDao() {
		super(MeasurementsExt.class);
	}
}
