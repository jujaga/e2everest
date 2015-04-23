package com.jujaga.emr.dao;

import org.springframework.stereotype.Repository;

import com.jujaga.emr.model.Hl7TextInfo;

@Repository
public class Hl7TextInfoDao extends AbstractDao<Hl7TextInfo> {
	public Hl7TextInfoDao() {
		super(Hl7TextInfo.class);
	}
}
