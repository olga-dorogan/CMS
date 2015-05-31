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
    @PersistenceContext
    private EntityManager em;

    public LessonsLinksDAO(EntityManager entityClass) {
        setEntityClass(LessonLinkEntity.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }


    public LessonLinkEntity save(@NotNull LessonLinkEntity lessonLinks, @NotNull LessonEntity lesson) {
        if (em.find(LessonLinkEntity.class, lessonLinks.getId()) != null) {
            throw new EntityExistsException("This faculty is already exist is the database");
        }
        lessonLinks.setLesson(lesson);
        em.persist(lessonLinks);
        return lessonLinks;
    }


    public List<LessonLinkEntity> getAllLessonLink() {
        Query query = em.createQuery("SELECT c FROM LessonLinksEntity c");
        return query.getResultList();
    }
}
