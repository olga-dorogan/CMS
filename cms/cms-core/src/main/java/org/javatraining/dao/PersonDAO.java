package org.javatraining.dao;


import org.javatraining.entity.PersonEntity;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by vika on 26.05.15.
 */
@Stateless
public class PersonDAO extends GenericDAO<PersonEntity> {

    public PersonDAO() {
        setEntityClass(PersonEntity.class);
    }

    public List<PersonEntity> getAllPersons() {
        Query query = getEntityManager().createQuery("SELECT c FROM PersonEntity c");
        return query.getResultList();
    }



}
