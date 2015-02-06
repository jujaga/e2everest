package com.jujaga.emr.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class AbstractDao<T> {
	protected Class<T> modelClass;

	@PersistenceContext
	protected EntityManager entityManager;

	protected AbstractDao(Class<T> modelClass) {
		this.modelClass = modelClass;
	}

	// Create
	public T persist(T o) {
		entityManager.persist(o);
		return o;
	}

	// Read
	public T find(Object id) {
		return entityManager.find(modelClass, id);
	}

	// Update
	public T merge(T o) {
		return entityManager.merge(o);
	}

	// Delete
	public void remove(T o) {
		o = entityManager.merge(o);
		entityManager.remove(o);
	}
}
