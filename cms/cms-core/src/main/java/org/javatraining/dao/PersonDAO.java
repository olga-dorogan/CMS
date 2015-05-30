package org.javatraining.dao;


import org.javatraining.entity.PersonEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by vika on 26.05.15.
 */
@Stateless
public class PersonDAO extends GenericDAO<PersonEntity> {

    public PersonDAO() {
    }

    @PersistenceContext
    private EntityManager em;

    public PersonDAO(EntityManager entityClass) {
        setEntityClass(PersonEntity.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }


}
