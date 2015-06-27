package org.javatraining.dao;

import org.hamcrest.Matcher;
import org.hamcrest.core.IsNull;
import org.javatraining.dao.exception.EntityIsAlreadyExistException;
import org.javatraining.dao.exception.EntityNotExistException;
import org.javatraining.entity.PersonEntity;
import org.javatraining.entity.PersonRole;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.*;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;

/**
 * Created by vika on 12.06.15.
 */
@RunWith(Arquillian.class)
@UsingDataSet(value = "dao-tests/person/one-person.json")
@Cleanup(phase = TestExecutionPhase.BEFORE, strategy = CleanupStrategy.STRICT)
public class PersonDAOTest {

    @EJB
    PersonDAO personDAO;

    private static final String DS_DIR = "dao-tests/";
    private static final String DS_EMPTY = DS_DIR + "empty.json";

    private static final String DS_PERSON = DS_DIR + "person/one-person.json";
    private static final String DS_PERSON_AFTER_UPDATE = DS_DIR + "person/expected-after-update.json";
    private static final String DS_PERSON_AFTER_SAVE = DS_DIR + "person/expected-after-save.json";



    @Deployment
    public static WebArchive createDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addPackage("org.javatraining.dao")
                .addPackage("org.javatraining.entity")
                .addPackage("org.javatraining.dao.exception")
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        return war;
    }


    @Test
    public void testPersonDAOShouldBeInjected() throws Exception {
        assertThat(personDAO, is(notNullValue()));
    }

    @Test(expected = Exception.class)
    public void testGetByIdForNotExistingIdShouldReturnEntityDoesNotExistException() {
        Long notExistingId = 10L;
        try{
            PersonEntity personWithNotExistingId = personDAO.getById(notExistingId);
            assertThat(personWithNotExistingId, is(IsNull.nullValue()));
        }
        catch (EntityNotExistException e) {
            assertThat(e.getCause(), is((Matcher)instanceOf(ConstraintViolationException.class)));
            if (checkNotNullArgumentViolationException((ConstraintViolationException) e.getCause())) {
                throw e;
            }
        }
    }


    @Test
    @ShouldMatchDataSet(value = {DS_EMPTY, DS_PERSON_AFTER_SAVE}, excludeColumns = {"id", "phone"})
    public void testSave() {
        PersonEntity personForSaving = personEntityInitializeForTests(new PersonEntity());
        personDAO.save(personForSaving);
    }

    @Test(expected = RuntimeException.class)
    @ShouldMatchDataSet(value = {DS_EMPTY, DS_PERSON}, excludeColumns = {"id", "phone"})
    public void testSavePersonThatExistTrowEntityIsAlreadyExistException() {
        PersonEntity personForSaving = predefinedPersonInitializationForTests(new PersonEntity());
          try{
        personDAO.save(personForSaving);
}
       catch (EntityIsAlreadyExistException e) {
        assertThat(e.getCause(), is((Matcher)instanceOf(ConstraintViolationException.class)));
        if (checkNotNullArgumentViolationException((ConstraintViolationException) e.getCause())) {
            throw e;
        }
    }
    }

    @Test(expected = RuntimeException.class)
    public void testGetByIdForNullIdShouldThrowConstraintViolationException() throws Exception {
        try {
            personDAO.getById(null);
        } catch (EJBException e) {
            assertThat(e.getCause(), is((Matcher)instanceOf(ConstraintViolationException.class)));
            if (checkNotNullArgumentViolationException((ConstraintViolationException) e.getCause())) {
                throw e;
            }
        }
    }

    @Test(expected = Exception.class)
    @ShouldMatchDataSet(value = {DS_EMPTY, DS_PERSON}, excludeColumns = {"id", "phone"})
    public void testSaveForNullPerson(){
        try {
            personDAO.save(null);
        }catch (EJBException e){
            assertThat(e.getCause(), is((Matcher)instanceOf(ConstraintViolationException.class)));
            if (checkNotNullArgumentViolationException((ConstraintViolationException) e.getCause())) {
                throw e;
        }
    }}

    @Test(expected = EJBException.class)
    @ShouldMatchDataSet(value = {DS_EMPTY, DS_PERSON}, excludeColumns = {"id", "phone"})
    public void testSaveForNotValidPerson(){
        personDAO.save(new PersonEntity());
    }

    @Test
    public void testGetPersonByPersonRole() {
        final int predefinedPersonsCnt = 1;
        PersonEntity predefinedPerson = predefinedPersonInitializationForTests(new PersonEntity());
        predefinedPerson= personDAO.getById(predefinedPerson.getId());
        List<PersonEntity> personsByRole = personDAO.getByPersonRole(predefinedPerson.getPersonRole());
        assertThat(personsByRole, hasItem(predefinedPerson));
        assertThat(personsByRole.size(), is(predefinedPersonsCnt));
    }

    @Test
    public void testGetPersonByEmail() {
        PersonEntity personEntity = personDAO.save(personEntityInitializeForTests(new PersonEntity()));
        PersonEntity otherPersonEntity = personEntityInitializeForTests(new PersonEntity());
        otherPersonEntity.setEmail("OtherEmeil@gmail.com");
        personDAO.save(otherPersonEntity);
        assertEquals(personDAO.getByEmail(personEntity.getEmail()), personEntity);
        assertEquals(personDAO.getByEmail(otherPersonEntity.getEmail()), otherPersonEntity);
    }

    @Test
    public void testSaveReturnPersonEntity() {
        PersonEntity personEntity = new PersonEntity();
        assertEquals(personEntity, personDAO.save(personEntityInitializeForTests(personEntity)));
    }

    @Test
    public void testUpdateReturnPersonEntity() {
        PersonEntity personEntity = personEntityInitializeForTests(new PersonEntity());
        personDAO.save(personEntity);
        personEntity.setName("Student");
        assertEquals(personDAO.update(personEntity), personEntity);
    }

    @Test
    @ShouldMatchDataSet(value = {DS_EMPTY, DS_PERSON_AFTER_UPDATE}, excludeColumns = {"id", "phone"})
  public void testUpdatePositive() {
        PersonEntity personEntity = predefinedPersonInitializationForTests(new PersonEntity());
        personEntity.setName("Student");
        assertEquals(personDAO.update(personEntity), personEntity);
    }

    @Test(expected = EJBException.class)
    public void testUpdateNotValidPersonEntityTrowEJBException() {
        personDAO.update(new PersonEntity());
    }

    @Test(expected = Exception.class)
    public void testUpdateNullPersonEntityTrowEJBException() {
        personDAO.update(null);
    }




    @Test
    public void testGetPersonEntity() {
        PersonEntity predefinedPerson = predefinedPersonInitializationForTests(new PersonEntity());
       assertThat(predefinedPerson, is(equalTo(predefinedPerson)));
    }

    @Test
    @ShouldMatchDataSet(value = DS_EMPTY)
    public void testRemovePerson() {
        PersonEntity personEntity = predefinedPersonInitializationForTests(new PersonEntity());
        personDAO.remove(personEntity);
     }

    @Test
    @ShouldMatchDataSet(value = DS_EMPTY)
    public void testClearDataBaseShouldBeEmpty(){
        personDAO.clear();
    }


    @Test
    @ShouldMatchDataSet(value = {DS_EMPTY, DS_PERSON}, excludeColumns = {"id", "phone"})
    public void testGetAllPersons() {
        assertThat(personDAO.getAllPersons(), is(notNullValue()));

    }

    private boolean checkNotNullArgumentViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        return !(constraintViolations.size() != 1 ||
                !constraintViolations.iterator().next().getMessage().equals("may not be null"));
    }

    public PersonEntity personEntityInitializeForTests(PersonEntity personEntity) {
        personEntity.setName("Petro");
        personEntity.setEmail("Petrovg@mail.ru");
        personEntity.setPhone("0933122345");
        personEntity.setLastName("Last Name");
        personEntity.setSecondName("Second name");
        personEntity.setPersonRole(PersonRole.TEACHER);
        return personEntity;
    }

    public PersonEntity predefinedPersonInitializationForTests(PersonEntity personEntity) {
        personEntity.setId(1L);
        personEntity.setName("teacherName");
        personEntity.setLastName("teacherLastName");
        personEntity.setEmail("teacher@gmail.com");
        personEntity.setSecondName("teacherSecondName");
        personEntity.setPersonRole(PersonRole.TEACHER);
        return personEntity;
    }


}
