package org.javatraining.dao;


import org.javatraining.entity.ForumMessagesEntity;
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
public class ForumMessageDAO extends GenericDAO<ForumMessagesEntity> {

    public ForumMessageDAO() {
        setEntityClass(ForumMessagesEntity.class);
    }

    public ForumMessagesEntity save(@NotNull ForumMessagesEntity forumMessagesEntity, @NotNull PersonEntity personEntity, @NotNull LessonEntity lesson) {
        forumMessagesEntity.setPersons(personEntity);
        forumMessagesEntity.setLessons(lesson);
        getEntityManager().persist(forumMessagesEntity);
        return forumMessagesEntity;
    }
    public List<ForumMessagesEntity> getAllForumMassage() {
        Query query = getEntityManager().createQuery("SELECT c FROM ForumMassagesEntity c");
        return query.getResultList();
    }
}
