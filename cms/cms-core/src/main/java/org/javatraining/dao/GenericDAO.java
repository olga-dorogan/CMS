package org.javatraining.dao;

import org.javatraining.dao.exception.EntityNotExistException;
import org.javatraining.dao.exception.EntityIsAlreadyExistException;

import javax.persistence.EntityExistsException;
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

    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }


    public T remove(@NotNull T entity) {
        try {
            entity = getEntityManager().merge(entity);
            getEntityManager().remove(entity);
        } catch (IllegalArgumentException e) {
            throw new EntityNotExistException();
        }

        return entity;
    }

    public T getById(@NotNull Long id) {
        T entity = getEntityManager().find(entityClass, id);
        if (entity == null) {
            throw new EntityNotExistException();
        }
        return entity;
    }

    public T removeById(@NotNull Long id) {
        T entity = getEntityManager().find(entityClass, id);
        if (entity == null) {
            throw new EntityNotExistException();
        }
        getEntityManager().remove(entity);
        return entity;
    }

    public T save(@NotNull T entity) {
        try {
            getEntityManager().persist(entity);
        } catch (EntityExistsException e) {
            throw new EntityIsAlreadyExistException("Entity already exists");
        }
        return entity;
    }

    public T update(@NotNull T entity) {
        try {
            getEntityManager().merge(entity);
        } catch (IllegalArgumentException e) {
            throw new EntityNotExistException();
        }
        return entity;
    }


    protected EntityManager getEntityManager() {
        return em;
    }

}
