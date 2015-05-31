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

    public void remove(@NotNull T entity) {
        getEntityManager().remove(entity);
    }

    public void removeById(@NotNull Object id) {
        T entity = (T) getEntityManager().find(entityClass, id);
    }

    public void save(@NotNull T entity) {
        getEntityManager().persist(entity);
    }

    public void update(@NotNull T entity) {
        getEntityManager().merge(entity);
    }


}
