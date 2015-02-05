package com.jujaga.emr.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class AbstractDao<T> {
	protected Class<T> modelClass;

	@PersistenceContext
	protected EntityManager entityManager = null;

	protected AbstractDao(Class<T> modelClass) {
		setModelClass(modelClass);
	}

	public void merge(T o) {
		entityManager.merge(o);
	}

	public void persist(T o) {
		entityManager.persist(o);
	}

	public T find(Object id) {
		return entityManager.find(modelClass, id);
	}

	private void setModelClass(Class<T> modelClass) {
		this.modelClass = modelClass;
	}
}
