package org.javatraining.dao;


import org.javatraining.entity.ForumMassagesEntity;
import org.javatraining.entity.LessonEntity;
import org.javatraining.entity.PersonEntity;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by vika on 28.05.15.
 */

@Stateless
public class ForumMassageDAO extends GenericDAO<ForumMassagesEntity> {

    public ForumMassageDAO() {
        setEntityClass(ForumMassagesEntity.class);
    }

    public ForumMassagesEntity save(@NotNull ForumMassagesEntity forumMessagesEntity, @NotNull PersonEntity personEntity, @NotNull LessonEntity lesson) {
        forumMessagesEntity.setPersons(personEntity);
        forumMessagesEntity.setLessons(lesson);
        getEntityManager().persist(forumMessagesEntity);
        return forumMessagesEntity;
    }
    public List<ForumMassagesEntity> getAllForumMassage() {
        Query query = getEntityManager().createQuery("SELECT c FROM ForumMassagesEntity c");
        return query.getResultList();
    }
}
