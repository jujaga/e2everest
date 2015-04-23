package com.jujaga.emr.dao;

import org.springframework.stereotype.Repository;

import com.jujaga.emr.model.PatientLabRouting;

@Repository
public class PatientLabRoutingDao extends AbstractDao<PatientLabRouting> {
	public PatientLabRoutingDao() {
		super(PatientLabRouting.class);
	}
}
