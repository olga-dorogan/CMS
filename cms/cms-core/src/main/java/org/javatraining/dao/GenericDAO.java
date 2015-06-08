package org.javatraining.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by vika on 26.05.15.
 */

public abstract class GenericDAO<T extends Serializable> {
    private Class<T> entityClass;
    @PersistenceContext
    private EntityManager em;

    public GenericDAO() {
    }

    protected EntityManager getEntityManager(){return em;}

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public T remove(@NotNull T entity) {
        getEntityManager().remove(entity);
    return entity;
    }

    public T findById(@NotNull Object id) {
        T entity = (T) getEntityManager().find(entityClass, id);
    return entity;
    }

    public T removeById(@NotNull Object id) {
        T entity = (T) getEntityManager().find(entityClass, id);
        getEntityManager().remove(entity);
        return entity;
    }

    public T save(@NotNull T entity) {
        getEntityManager().persist(entity);
        return entity;
    }

    public T update(@NotNull T entity) {
        getEntityManager().merge(entity);
        return entity;
    }

}
