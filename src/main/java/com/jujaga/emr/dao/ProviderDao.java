package com.jujaga.emr.dao;

import org.springframework.stereotype.Repository;

import com.jujaga.emr.model.Provider;

@Repository
public class ProviderDao extends AbstractDao<Provider> {
	public ProviderDao() {
		super(Provider.class);
	}
}
