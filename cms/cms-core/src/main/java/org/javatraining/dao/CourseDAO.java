package org.javatraining.dao;


import org.javatraining.entity.CourseEntity;
import org.javatraining.entity.enums.CourseStatus;
import org.javatraining.entity.util.Pair;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<CourseEntity> getAllCoursesEndedBeforeDate(Date date) {
        TypedQuery<CourseEntity> query = getEntityManager().createQuery(
                "SELECT c FROM CourseEntity c WHERE c.enddate < :date", CourseEntity.class);
        query.setParameter("date", date);
        return query.getResultList();
    }

    public List<Pair<CourseEntity, CourseStatus>> getAllCoursesWithStatusesForPerson(@NotNull Long personId) {
        TypedQuery<Object[]> query = getEntityManager().createQuery(
                "SELECT c, st.courseStatus FROM CourseEntity c, CoursePersonStatusEntity st WHERE c = st.course AND st.person.id = :personId",
                Object[].class).setParameter("personId", personId);
        List<Pair<CourseEntity, CourseStatus>> result = query.getResultList()
                .stream()
                .map((ar) -> new Pair<>((CourseEntity) ar[0], (CourseStatus) ar[1]))
                .collect(Collectors.toList());
        return result;
    }
}
