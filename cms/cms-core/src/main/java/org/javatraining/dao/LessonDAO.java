package org.javatraining.dao;


import org.javatraining.entity.LessonEntity;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by vika on 28.05.15.
 */
@Stateless
public class LessonDAO extends GenericDAO<LessonEntity> {

  public LessonDAO() {
        setEntityClass(LessonEntity.class);
    }

    public List<LessonEntity> getAllLessons() {
        Query query = getEntityManager().createQuery("SELECT c FROM LessonEntity c");
        return query.getResultList();
    }

    public LessonEntity getByOrderNum(Long courseId, Long orderNum) {
        return (LessonEntity) getEntityManager().createNamedQuery(LessonEntity.FIND_BY_COURSE_AND_ORDER_NUM)
                .setParameter("course_id", courseId)
                .setParameter("order_num", orderNum)
                .getSingleResult();
    }

    public List<LessonEntity> getByCourseId(Long courseId) {
        return (List<LessonEntity>) getEntityManager().createNamedQuery(LessonEntity.FIND_BY_COURSE)
                .setParameter("course_id", courseId)
                .getResultList();
    }
}
