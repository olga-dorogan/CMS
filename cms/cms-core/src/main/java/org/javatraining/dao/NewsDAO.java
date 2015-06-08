package org.javatraining.dao;


import org.javatraining.entity.CourseEntity;
import org.javatraining.entity.NewsEntity;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by vika on 26.05.15.
 */

@Stateless
public class NewsDAO extends GenericDAO<NewsEntity> {

     public NewsDAO() {
        setEntityClass(NewsEntity.class);
    }

    public NewsEntity save(@NotNull NewsEntity newsEntity, @NotNull CourseEntity courseEntity) {
     newsEntity.setCourses(courseEntity);
        getEntityManager().persist(newsEntity);
        return newsEntity;
    }

    public List<NewsEntity> getAllNews() {
        Query query = getEntityManager().createQuery("SELECT c FROM NewsEntity c");
        return query.getResultList();
    }

}
