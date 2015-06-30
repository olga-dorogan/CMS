package org.javatraining.dao;

import org.javatraining.entity.CoursePersonStatusEntity;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by vika on 27.06.15.
 */
@Stateless
public class CoursePersonStatusDAO extends GenericDAO<CoursePersonStatusEntity> {
    public CoursePersonStatusDAO() {
        setEntityClass(CoursePersonStatusEntity.class);
    }

    public List<CoursePersonStatusEntity> getCourseStatusesForPerson(@NotNull Long personId) {
        TypedQuery<CoursePersonStatusEntity> query = getEntityManager().createQuery(
                "SELECT status FROM CoursePersonStatusEntity status WHERE PersonEntity.id = :personId",
                CoursePersonStatusEntity.class);
        query.setParameter("personId", personId);
        return query.getResultList();
    }
}
