package org.javatraining.dao;


import org.javatraining.dao.exception.EntityIsAlreadyExistException;
import org.javatraining.entity.LessonEntity;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by vika on 28.05.15.
 */
@Stateless
public class LessonDAO extends GenericDAO<LessonEntity>  {

  public LessonDAO() {
        setEntityClass(LessonEntity.class);
    }

    public List<LessonEntity> getAllLessons() {
        Query query = getEntityManager().createQuery("SELECT c FROM LessonEntity c");
        return query.getResultList();
    }
    public LessonEntity save(@NotNull LessonEntity lesson) {

        if (lesson.getId() != null && getEntityManager().find(LessonEntity.class, lesson.getId()) != null) {
            throw new EntityIsAlreadyExistException();
        }

        getEntityManager().persist(lesson);
        return lesson;
    }

}
