package org.javatraining.dao;


import org.javatraining.entity.LessonEntity;
import org.javatraining.entity.MarkEntity;
import org.javatraining.entity.PersonEntity;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by vika on 28.05.15.
 */
@Stateless
public class MarkDAO extends GenericDAO<MarkEntity> {

     public MarkDAO() {
        setEntityClass(MarkEntity.class);
    }

    public MarkEntity save(@NotNull MarkEntity mark, @NotNull PersonEntity person, @NotNull LessonEntity lessons) {
        mark.setPersons(person);
        mark.setLessons(lessons);
        super.save(mark);
        return mark;
    }
    public List<MarkEntity> getAllMarks() {
        Query query = getEntityManager().createQuery("SELECT c FROM MarkEntity c");
        return query.getResultList();
    }
}
