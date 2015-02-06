package com.jujaga.emr.dao;

import org.springframework.stereotype.Repository;

import com.jujaga.emr.model.Demographic;

@Repository
public class ProviderDao extends AbstractDao<Demographic> {
	public ProviderDao() {
		super(Demographic.class);
	}
}
