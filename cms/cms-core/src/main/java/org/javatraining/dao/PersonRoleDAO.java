package org.javatraining.dao;


import org.javatraining.entity.LessonLinkEntity;
import org.javatraining.entity.PersonEntity;
import org.javatraining.entity.PersonRoleEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;

/**
 * Created by vika on 26.05.15.
 */
@Stateless
public class PersonRoleDAO extends GenericDAO<PersonRoleEntity> {

    @PersistenceContext
    private EntityManager em;

    public PersonRoleDAO() {
        setEntityClass(PersonRoleEntity.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonRoleEntity save(@NotNull PersonRoleEntity personRoleEntity, @NotNull PersonEntity personEntity) {

        if (em.find(LessonLinkEntity.class, personRoleEntity.getId()) != null) {
            throw new EntityExistsException("This personRole is already exist is the database");
        }
//        personRoleEntity.setPerson(personEntity);
        em.persist(personRoleEntity);
        return personRoleEntity;
    }


}
