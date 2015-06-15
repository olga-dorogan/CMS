package org.javatraining.dao;

import org.javatraining.entity.PersonEntity;
import org.javatraining.entity.PersonRole;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by vika on 12.06.15.
 */
@RunWith(Arquillian.class)
public class PesronDAOTest {
    @EJB
    PersonDAO personDAO;

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addPackage("org.javatraining.dao")
                .addPackage("org.javatraining.entity")
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        return war;
    }

    public PersonEntity personEntityInit(PersonEntity personEntity) {
        personEntity.setName("Petro");
        personEntity.setEmail("Petrovg@mail.ru");
        personEntity.setLastName("Last Name");
        personEntity.setSecondName("Second name");
        personEntity.setPersonRole(PersonRole.TEACHER);
        return personEntity;
    }

    @Test
    @InSequence(1)
    public void testGetPersonByPersonRole() {
        personDAO.clear();
        PersonEntity personEntity = personEntityInit(new PersonEntity());
        personDAO.save(personEntity);
        assertEquals(Arrays.asList(personEntity), personDAO.getByPersonRole(PersonRole.TEACHER));
    }

    @Test
    @InSequence(2)
    public void testGetPersonByEmail() {
        personDAO.clear();
        PersonEntity personEntity = personDAO.save(personEntityInit(new PersonEntity()));
        assertEquals(personDAO.getByEmail(personEntity.getEmail()), personEntity);
    }

    @InSequence(3)
    @Test
    public void testSaveReturnPersonEntity() {
        PersonEntity personEntity = new PersonEntity();
        assertEquals(personEntity, personDAO.save(personEntityInit(personEntity)));
    }

    @InSequence(4)
    @Test(expected = EJBException.class)
    public void testSaveNullPersonEntity() {
        PersonEntity personEntity = new PersonEntity();
        personDAO.save(personEntity);
    }

    @InSequence(5)
    @Test
    public void testUpdateReturnPersonEntity() {
        PersonEntity personEntity = new PersonEntity();
        personEntity = personEntityInit(personEntity);
        personDAO.save(personEntity);
        personEntity.setName("Student");
        assertEquals(personDAO.update(personEntity), personEntity);
    }

    @InSequence(6)
    @Test
    public void testGetPersonEntity() {
        PersonEntity personEntity = personEntityInit(new PersonEntity());
        personDAO.save(personEntity);
        assertEquals(personDAO.getById(personEntity.getId()), personEntity);
    }

    @InSequence(7)
    @Test
    public void testRemovePersonEntity() {
        PersonEntity personEntity = personEntityInit(new PersonEntity());
        personDAO.remove(personEntity);
        assertEquals(personDAO.remove(personEntity), personEntity);
    }

    @InSequence(8)
    @Test
    public void testGetAllPersons() {
        personDAO.clear();
        PersonEntity personEntity = personEntityInit(new PersonEntity());
        personDAO.save(personEntity);
        assertEquals(personDAO.getAllPersons(), Arrays.asList(personEntity));
    }

}
