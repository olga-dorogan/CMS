package org.javatraining.dao;


import org.javatraining.entity.LessonTypeEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by vika on 25.05.15.
 */

@Stateless
public class LessonsTypesDAO extends GenericDAO<LessonTypeEntity> {

    @PersistenceContext
    private EntityManager em;


    public LessonsTypesDAO()
    {
        setEntityClass(LessonTypeEntity.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
