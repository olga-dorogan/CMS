package org.javatraining.dao;


import org.javatraining.entity.CourseEntity;

import javax.ejb.Stateless;
import javax.persistence.Query;
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

}
