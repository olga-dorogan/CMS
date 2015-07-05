package org.javatraining.dao;


import org.javatraining.entity.CourseEntity;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.Date;
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

    public void clear() {
        getEntityManager().createQuery("delete from CourseEntity").executeUpdate();
    }

    public List<CourseEntity> getAllCoursesStartedAfterDate(Date date) {
        TypedQuery<CourseEntity> query = getEntityManager().createQuery(
                "SELECT c FROM CourseEntity c WHERE c.startdate > :date", CourseEntity.class);
        query.setParameter("date", date);
        return query.getResultList();
    }
}
