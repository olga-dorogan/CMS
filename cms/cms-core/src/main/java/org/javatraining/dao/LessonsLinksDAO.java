package org.javatraining.dao;


import org.javatraining.entity.LessonEntity;
import org.javatraining.entity.LessonLinkEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by vika on 25.05.15.
 */


@Stateless
public class LessonsLinksDAO extends GenericDAO<LessonLinkEntity> {

    public LessonsLinksDAO() {
        setEntityClass(LessonLinkEntity.class);
    }

    public LessonLinkEntity save(@NotNull LessonLinkEntity lessonLinks, @NotNull LessonEntity lesson) {
       lessonLinks.setLesson(lesson);
        getEntityManager().persist(lessonLinks);
        return lessonLinks;
    }

    public List<LessonLinkEntity> getAllLessonLink() {
        Query query = getEntityManager().createQuery("SELECT c FROM LessonLinksEntity c");
        return query.getResultList();
    }
}
