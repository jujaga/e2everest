package com.jujaga.emr.dao;

public interface IDao<E, K> {
	void persist(E entity);
	void remove(E entity);
	E findById(K id);
}
