package org.javatraining.dao;


import org.javatraining.entity.ForumMassagesEntity;
import org.javatraining.entity.LessonEntity;
import org.javatraining.entity.LessonLinkEntity;
import org.javatraining.entity.PersonEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.validation.constraints.NotNull;

/**
 * Created by vika on 28.05.15.
 */

@Stateless
public class ForumMassageDAO extends GenericDAO<ForumMassagesEntity> {

    public ForumMassageDAO() {
        setEntityClass(ForumMassagesEntity.class);
    }

    public ForumMassagesEntity save(@NotNull ForumMassagesEntity forumMessagesEntity, @NotNull PersonEntity personEntity, @NotNull LessonEntity lesson) {
        if (getEntityManager().find(LessonLinkEntity.class, forumMessagesEntity.getId()) != null) {
            throw new EntityExistsException("This forumMassage is already exist is the database");
        }
        forumMessagesEntity.setPersons(personEntity);
        forumMessagesEntity.setLessons(lesson);
        getEntityManager().persist(forumMessagesEntity);
        return forumMessagesEntity;
    }
}
