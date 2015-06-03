package org.javatraining.dao;


import org.javatraining.entity.PersonEntity;
import org.javatraining.entity.PersonRoleEntity;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by vika on 26.05.15.
 */
@Stateless
public class PersonRoleDAO extends GenericDAO<PersonRoleEntity> {

    public PersonRoleDAO() {
        setEntityClass(PersonRoleEntity.class);
    }

    public PersonRoleEntity save(@NotNull PersonRoleEntity personRoleEntity, @NotNull PersonEntity personEntity) {
           personRoleEntity.setPerson(personEntity);
        getEntityManager().persist(personRoleEntity);
        return personRoleEntity;
    }
    public List<PersonEntity> getAllPersonsRole() {
        Query query = getEntityManager().createQuery("SELECT c FROM PersonRoleEntity c");
        return query.getResultList();
    }

}
