package org.javatraining.dao;

import org.javatraining.entity.CoursePersonStatusEntity;

import javax.ejb.Stateless;

/**
 * Created by vika on 27.06.15.
 */
@Stateless
public class CoursePersonStatusDAO extends GenericDAO<CoursePersonStatusEntity> {
    public CoursePersonStatusDAO() {
        setEntityClass(CoursePersonStatusEntity.class);
    }

}
