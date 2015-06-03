package org.javatraining.dao;


import org.javatraining.entity.LessonEntity;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by vika on 28.05.15.
 */
public class LessonDAO extends GenericDAO<LessonEntity> {

  public LessonDAO() {
        setEntityClass(LessonEntity.class);
    }

    public List<LessonEntity> getAllLessons() {
        Query query = getEntityManager().createQuery("SELECT c FROM LessonEntity c");
        return query.getResultList();
    }

}
