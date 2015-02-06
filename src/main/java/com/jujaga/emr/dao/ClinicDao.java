package com.jujaga.emr.dao;

import org.springframework.stereotype.Repository;

import com.jujaga.emr.model.Demographic;

@Repository
public class ClinicDao extends AbstractDao<Demographic> {
	public ClinicDao() {
		super(Demographic.class);
	}
}
