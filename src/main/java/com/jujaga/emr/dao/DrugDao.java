package com.jujaga.emr.dao;

import org.springframework.stereotype.Repository;

import com.jujaga.emr.model.Drug;

@Repository
public class DrugDao extends AbstractDao<Drug> {
	public DrugDao() {
		super(Drug.class);
	}
}
