package org.javatraining.dao;


import org.javatraining.entity.PracticeLessonEntity;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by vika on 25.05.15.
 */

@Stateless
public class PracticeLessonDAO extends GenericDAO<PracticeLessonEntity> {

    public PracticeLessonDAO()
    {
        setEntityClass(PracticeLessonEntity.class);
    }

    public List<PracticeLessonEntity> getAllPracticeLesson() {
        Query query = getEntityManager().createQuery("SELECT c FROM PracticeLessonEntity c");
        return query.getResultList();
    }

}
