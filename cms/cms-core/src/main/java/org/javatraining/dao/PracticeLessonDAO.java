package org.javatraining.dao;


import org.javatraining.entity.LessonEntity;
import org.javatraining.entity.PracticeLessonEntity;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by vika on 25.05.15.
 */

@Stateless
public class PracticeLessonDAO extends GenericDAO<PracticeLessonEntity> {

    public PracticeLessonDAO() {
        setEntityClass(PracticeLessonEntity.class);
    }

    public List<PracticeLessonEntity> getAllPracticeLesson() {
        Query query = getEntityManager().createQuery("SELECT c FROM PracticeLessonEntity c");
        return query.getResultList();
    }

    public List<PracticeLessonEntity> getPracticesForLesson(@NotNull LessonEntity lessonEntity) {
        TypedQuery<PracticeLessonEntity> query = getEntityManager().createQuery(
                "SELECT pr FROM PracticeLessonEntity pr WHERE pr.lesson = :lesson", PracticeLessonEntity.class);
        query.setParameter("lesson", lessonEntity);
        return query.getResultList();
    }

}
