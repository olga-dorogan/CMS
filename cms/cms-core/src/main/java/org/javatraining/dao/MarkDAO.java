package org.javatraining.dao;


import org.javatraining.entity.LessonEntity;
import org.javatraining.entity.MarkEntity;
import org.javatraining.entity.PersonEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;

/**
 * Created by vika on 28.05.15.
 */
@Stateless
public class MarkDAO extends GenericDAO<MarkEntity> {


    @PersistenceContext
    private EntityManager em;

    public MarkDAO() {
    }

    public MarkDAO(EntityManager entityClass) {
        setEntityClass(MarkEntity.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public void save(@NotNull MarkEntity mark, @NotNull PersonEntity person, @NotNull LessonEntity lessons) {
        mark.setPersons(person);
        mark.setLessons(lessons);
        super.save(mark);
    }
}
