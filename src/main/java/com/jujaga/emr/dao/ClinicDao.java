package com.jujaga.emr.dao;

import org.springframework.stereotype.Repository;

import com.jujaga.emr.model.Clinic;

@Repository
public class ClinicDao extends AbstractDao<Clinic> {
	public ClinicDao() {
		super(Clinic.class);
	}
}
