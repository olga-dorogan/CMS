package org.javatraining.dao;


import org.javatraining.entity.CourseEntity;
import org.javatraining.entity.LessonEntity;
import org.javatraining.entity.LessonTypeEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;

/**
 * Created by vika on 28.05.15.
 */
public class LessonDAO extends GenericDAO<LessonEntity> {

    @PersistenceContext
    private EntityManager em;

    public LessonDAO() {
        setEntityClass(LessonEntity.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public void save(@NotNull LessonTypeEntity lessonTypes, @NotNull CourseEntity course, @NotNull LessonEntity lessons) {
        lessons.setLessonTypes(lessonTypes);
        lessons.setCourses(course);
        super.save(lessons);
    }
}
