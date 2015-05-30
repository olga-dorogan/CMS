package org.javatraining.dao;


import org.javatraining.entity.CourseEntity;
import org.javatraining.entity.NewsEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by vika on 26.05.15.
 */

@Stateless
public class NewsDAO extends GenericDAO<NewsEntity> {

    @PersistenceContext
    private EntityManager em;

    public NewsDAO() {
    }

    public NewsDAO(EntityManager entityClass) {
        setEntityClass(NewsEntity.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NewsEntity save(@NotNull NewsEntity newsEntity, @NotNull CourseEntity courseEntity) {

        if (em.find(NewsEntity.class, newsEntity.getId()) != null) {
            throw new EntityExistsException("This faculty is already exist is the database");
        }
        newsEntity.setCourses(courseEntity);
        em.persist(newsEntity);
        return newsEntity;
    }

    public List<NewsEntity> getAllNews() {
        Query query = em.createQuery("SELECT c FROM NewsEntity c");
        return query.getResultList();
    }

}
