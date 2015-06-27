package org.javatraining.dao;


import org.javatraining.dao.exception.EntityIsAlreadyExistException;
import org.javatraining.entity.LessonEntity;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by vika on 28.05.15.
 */
@Stateless
public class LessonDAO extends GenericDAO<LessonEntity>  {

  public LessonDAO() {
        setEntityClass(LessonEntity.class);
    }

    public List<LessonEntity> getAllLessons() {
        Query query = getEntityManager().createQuery("SELECT c FROM LessonEntity c");
        return query.getResultList();
    }
    public LessonEntity save(@NotNull LessonEntity lesson) {

        if (lesson.getId() != null && getEntityManager().find(LessonEntity.class, lesson.getId()) != null) {
            throw new EntityIsAlreadyExistException();
        }

        getEntityManager().persist(lesson);
        return lesson;
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

    public LessonEntity updateByOrderNum(Long courseId, Long orderNum, LessonEntity lesson) {
        LessonEntity lessonEntity = getByOrderNum(courseId, orderNum);
        lessonEntity.setTopic(lesson.getTopic());
        lessonEntity.setCreateDate(lesson.getCreateDate());
        lessonEntity.setDescription(lesson.getDescription());
        lessonEntity.setOrderNum(lessonEntity.getOrderNum());

        return lessonEntity;
    }

    public void removeByOrderNum(Long courseId, Long orderNum) {
        LessonEntity lessonEntity = getByOrderNum(courseId, orderNum);
        getEntityManager().remove(lessonEntity);
    }
}
