package org.javatraining.service;

import org.javatraining.dao.CourseDAO;
import org.javatraining.dao.GenericDAO;
import org.javatraining.dao.PersonDAO;
import org.javatraining.entity.PersonEntity;
import org.javatraining.entity.PersonRole;
import org.javatraining.model.CourseVO;
import org.javatraining.model.PersonVO;
import org.javatraining.model.conversion.PersonConverter;
import org.javatraining.service.exception.UnsupportedOperationException;
import org.javatraining.service.impl.PersonServiceImpl;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.*;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.Date;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

/**
 * Created by olga on 15.06.15.
 */
@RunWith(Arquillian.class)
@UsingDataSet(value = "datasets/person-service-test/persons-one-person.json")
@Cleanup(phase = TestExecutionPhase.BEFORE, strategy = CleanupStrategy.STRICT)
public class PersonServiceTest {

    private static final String DATASETS_DIR = "datasets/person-service-test";
    private static final String DATASETS_EMPTY = DATASETS_DIR + "/empty.json";
    private static final String DATASETS_PERSON = DATASETS_DIR + "/persons-one-person.json";
    private static final String DATASETS_COURSE = DATASETS_DIR + "/courses-one-course.json";
    private static final String DATASETS_COURSE_PERSON = DATASETS_DIR + "/person-course.json";
    private static final String DATASETS_PERSON_AFTER_UPDATE = DATASETS_DIR + "/expected-persons-after-update.json";
    private static final String DATASETS_PERSON_AFTER_SAVE = DATASETS_DIR + "/expected-persons-after-save.json";

    private static PersonVO predefinedPerson;
    private static CourseVO predefinedCourse;
    private static int predefinedPersonCoursesCnt;
    private static String predefinedPersonNameForUpdate;
    private static PersonVO personForSaving;
    private static String notExistingEmail;
    private static Long notExistingId;

    static {
        predefinedPerson = new PersonVO(1L, "teacherName", "teacherLastName", "teacher@gmail.com", PersonRole.TEACHER);
        predefinedPerson.setSecondName("teacherSecondName");
        personForSaving = new PersonVO(null, "testedName", "testedLastName", "tested@gmail.com", PersonRole.STUDENT);
        personForSaving.setSecondName("testedSecondName");
        predefinedPersonNameForUpdate = "updatedName";

        predefinedCourse = new CourseVO(1L, "courseName", "courseDescription");
        predefinedCourse.setStartDate(Date.valueOf("2014-01-10"));
        predefinedCourse.setEndDate(Date.valueOf("2015-07-31"));
        predefinedPersonCoursesCnt = 1;

        notExistingEmail = "12345";
        notExistingId = Long.MIN_VALUE;
    }

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackage(PersonEntity.class.getPackage())
                .addPackage(PersonVO.class.getPackage())
                .addPackage(PersonConverter.class.getPackage())
                .addClasses(PersonDAO.class, CourseDAO.class, GenericDAO.class)
                .addClasses(PersonService.class, PersonServiceImpl.class, UnsupportedOperationException.class)
                .addAsResource(DATASETS_DIR)
                .addAsResource("META-INF/test-person-service-persistence.xml", "META-INF/persistence.xml");
    }

    @EJB
    PersonService personService;

    @Test
    public void testPersonServiceShouldBeInjected() throws Exception {
        assertThat(personService, is(notNullValue()));
    }

    @Test
    public void testGetById() throws Exception {
        PersonVO predefinedPersonFromService = personService.getById(predefinedPerson.getId());
        assertThat(predefinedPersonFromService, is(equalTo(predefinedPerson)));
    }

    @Test
    public void testGetByIdForNotExistingIdShouldReturnNull() throws Exception {
        PersonVO personWithNotExistingId = personService.getById(notExistingId);
        assertThat(personWithNotExistingId, is(nullValue()));
    }

    @Test(expected = RuntimeException.class)
    public void testGetByIdForNullIdShouldThrowConstraintViolationException() throws Exception {
        try {
            personService.getById(null);
        } catch (EJBException e) {
            assertThat(e.getCause(), is(instanceOf(ConstraintViolationException.class)));
            if (checkNotNullArgumentViolationException((ConstraintViolationException) e.getCause())) {
                throw e;
            }
        }
    }

    @Test
    public void testGetByEmail() throws Exception {
        PersonVO predefinedPersonFromServiceByEmail = personService.getByEmail(predefinedPerson.getEmail());
        assertThat(predefinedPersonFromServiceByEmail, is(equalTo(predefinedPerson)));
    }

    @Test
    public void testGetByEmailForNotExistingEmailShouldReturnNull() throws Exception {
        PersonVO personWithNotExistingEmail = personService.getByEmail(notExistingEmail);
        assertThat(personWithNotExistingEmail, is(nullValue()));
    }

    @Test(expected = RuntimeException.class)
    public void testGetByEmailForNullEmailShouldThrowConstraintViolationException() throws Exception {
        try {
            personService.getByEmail(null);
        } catch (EJBException e) {
            assertThat(e.getCause(), is(instanceOf(ConstraintViolationException.class)));
            if (checkNotNullArgumentViolationException((ConstraintViolationException) e.getCause())) {
                throw e;
            }
        }
    }

    @Test
    @ShouldMatchDataSet(value = {DATASETS_EMPTY, DATASETS_PERSON_AFTER_SAVE}, excludeColumns = {"id", "phone"})
    public void testSave() throws Exception {
        personService.save(personForSaving);
    }


    @Test
    @ShouldMatchDataSet(value = {DATASETS_EMPTY, DATASETS_PERSON}, excludeColumns = {"id", "phone"})
    public void testSaveForAlreadyExistingPersonShouldDoNothing() throws Exception {
        personService.save(predefinedPerson);
    }

    @Test(expected = RuntimeException.class)
    @ShouldMatchDataSet(value = {DATASETS_EMPTY, DATASETS_PERSON}, excludeColumns = {"id", "phone"})
    public void testSaveForNullPersonShouldThrowConstraintViolationException() throws Exception {
        try {
            personService.save(null);
        } catch (EJBException e) {
            assertThat(e.getCause(), is(instanceOf(ConstraintViolationException.class)));
            if (checkNotNullArgumentViolationException((ConstraintViolationException) e.getCause())) {
                throw e;
            }
        }
    }

    @Test(expected = RuntimeException.class)
    @ShouldMatchDataSet(value = {DATASETS_EMPTY, DATASETS_PERSON}, excludeColumns = {"id", "phone"})
    public void testSaveForNotValidPersonShouldThrowConstraintViolationException() throws Exception {
        try {
            personService.save(new PersonVO());
        } catch (EJBException e) {
            assertThat(e.getCause(), is(instanceOf(ConstraintViolationException.class)));
            if (checkValidPersonViolationException((ConstraintViolationException) e.getCause())) {
                throw e;
            }
        }
    }

    @Test
    @ShouldMatchDataSet(value = {DATASETS_EMPTY, DATASETS_PERSON_AFTER_UPDATE}, excludeColumns = {"id", "phone"})
    public void testUpdate() throws Exception {
        predefinedPerson.setName(predefinedPersonNameForUpdate);
        personService.update(predefinedPerson);
        ;
    }

    @Test(expected = RuntimeException.class)
    @ShouldMatchDataSet(value = {DATASETS_EMPTY, DATASETS_PERSON}, excludeColumns = {"id", "phone"})
    public void testUpdateForNullPersonShouldThrowConstraintViolationException() throws Exception {
        try {
            personService.update(null);
        } catch (EJBException e) {
            assertThat(e.getCause(), is(instanceOf(ConstraintViolationException.class)));
            if (checkNotNullArgumentViolationException((ConstraintViolationException) e.getCause())) {
                throw e;
            }
        }
    }

    @Test(expected = RuntimeException.class)
    @ShouldMatchDataSet(value = {DATASETS_EMPTY, DATASETS_PERSON}, excludeColumns = {"id", "phone"})
    public void testUpdateForNotValidPersonShouldThrowConstraintViolationException() throws Exception {
        try {
            personService.update(new PersonVO());
        } catch (EJBException e) {
            assertThat(e.getCause(), is(instanceOf(ConstraintViolationException.class)));
            if (checkValidPersonViolationException((ConstraintViolationException) e.getCause())) {
                throw e;
            }
        }
    }

    @Test
    @ShouldMatchDataSet(value = DATASETS_EMPTY)
    public void testRemove() throws Exception {
        personService.remove(predefinedPerson);
    }

    @Test(expected = RuntimeException.class)
    @ShouldMatchDataSet(value = {DATASETS_EMPTY, DATASETS_PERSON}, excludeColumns = {"id", "phone"})
    public void testRemoveForNullPersonShouldThrowConstraintViolationException() throws Exception {
        try {
            personService.remove(null);
        } catch (EJBException e) {
            assertThat(e.getCause(), is(instanceOf(ConstraintViolationException.class)));
            if (checkNotNullArgumentViolationException((ConstraintViolationException) e.getCause())) {
                throw e;
            }
        }
    }

    @Test
    @ShouldMatchDataSet(value = DATASETS_EMPTY, excludeColumns = {"id", "phone"})
    public void testRemoveForNotValidPersonShouldRemoveCorrectly() throws Exception {
        PersonVO notValidPerson = new PersonVO();
        notValidPerson.setId(predefinedPerson.getId());
        personService.remove(notValidPerson);
    }

    @Test
    @UsingDataSet(value = {DATASETS_PERSON, DATASETS_COURSE, DATASETS_COURSE_PERSON})
    public void testGetCourses() {
        Set<CourseVO> personCourses = personService.getCourses(predefinedPerson);
        assertThat(personCourses, is(notNullValue()));
        assertThat(personCourses.size(), is(predefinedPersonCoursesCnt));
    }

    @Test
    @UsingDataSet(value = {DATASETS_PERSON, DATASETS_COURSE})
    @ShouldMatchDataSet(value = {DATASETS_EMPTY, DATASETS_PERSON, DATASETS_COURSE, DATASETS_COURSE_PERSON})
    public void testAddPersonToCourse() {
        personService.addPersonToCourse(predefinedPerson, predefinedCourse);
    }

    @Test
    @UsingDataSet(value = {DATASETS_PERSON, DATASETS_COURSE, DATASETS_COURSE_PERSON})
    @ShouldMatchDataSet(value = {DATASETS_EMPTY, DATASETS_PERSON, DATASETS_COURSE})
    public void testRemovePersonFromCourse() {
        personService.removePersonFromCourse(predefinedPerson, predefinedCourse);
    }

    @Test
    @UsingDataSet(value = {DATASETS_PERSON, DATASETS_COURSE, DATASETS_COURSE_PERSON})
    @ShouldMatchDataSet(value = {DATASETS_EMPTY, DATASETS_PERSON, DATASETS_COURSE, DATASETS_COURSE_PERSON})
    public void testAddPersonToCourseForPersonAlreadyAddedToCourse() {
        personService.addPersonToCourse(predefinedPerson, predefinedCourse);
    }

    private boolean checkNotNullArgumentViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        if (constraintViolations.size() != 1 ||
                !constraintViolations.iterator().next().getMessage().equals("may not be null")) {
            return false;
        }
        return true;
    }

    private boolean checkValidPersonViolationException(ConstraintViolationException e) {
        final int expectedViolationsCnt = 3;
        final Set<String> expectedViolationMessages = Collections.unmodifiableSet(new HashSet<>(Collections.nCopies(expectedViolationsCnt, "may not be null")));
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        if (constraintViolations.size() != expectedViolationsCnt) {
            return false;
        }
        Set<String> violationMessages = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());
        return violationMessages.equals(expectedViolationMessages);
    }

}