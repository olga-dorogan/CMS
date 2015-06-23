package org.javatraining.dao;


import org.javatraining.entity.CourseEntity;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by vika on 26.05.15.
 */
@Stateless
public class CourseDAO extends GenericDAO<CourseEntity> {

    public CourseDAO() {
      setEntityClass(CourseEntity.class);
       }

    public List<CourseEntity> getAllCourses() {
       Query query = getEntityManager().createQuery("SELECT c FROM CourseEntity c");
       return query.getResultList();
    }

    public void clear()
    {
        getEntityManager().createQuery("delete from CourseEntity").executeUpdate();
    }


    public CourseEntity removeCourse(@NotNull CourseEntity entity) {

        getEntityManager().remove(entity);
        return entity;
    }
}
