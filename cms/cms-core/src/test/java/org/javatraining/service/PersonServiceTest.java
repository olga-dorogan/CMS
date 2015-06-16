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
import org.jboss.arquillian.junit.InSequence;
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

/**
 * Created by olga on 15.06.15.
 */
// FIXME: set real expected exception class for all tests expect Exceptions
@RunWith(Arquillian.class)
public class PersonServiceTest {

    private static PersonVO predefinedTeacher;
    private static PersonVO predefinedStudent;
    private static PersonVO testedPerson;
    private static CourseVO predefinedCourse;
    private static int predefinedTeacherCoursesCnt;
    private static int predefinedStudentCoursesCnt;
    private static String notExistingEmail;
    private static Long notExistingId;

    static {
        predefinedTeacher = new PersonVO(1L, "teacherName", "teacherLastName", "teacher@gmail.com", PersonRole.TEACHER);
        predefinedTeacher.setSecondName("teacherSecondName");
        predefinedTeacherCoursesCnt = 1;
        predefinedStudent = new PersonVO(2L, "studentName", "studentLastName", "student@gmail.com", PersonRole.STUDENT);
        predefinedStudent.setSecondName("studentSecondName");
        predefinedStudentCoursesCnt = 0;

        predefinedCourse = new CourseVO(1L, "courseName", "courseDescription");
        predefinedCourse.setStartDate(Date.valueOf("2014-01-10"));
        predefinedCourse.setEndDate(Date.valueOf("2015-07-31"));

        notExistingEmail = "12345";
        notExistingId = Long.MIN_VALUE;

        testedPerson = new PersonVO();
        testedPerson.setName("testedName");
        testedPerson.setSecondName("testedSecondName");
        testedPerson.setLastName("testedLastName");
        testedPerson.setEmail("tested@gmail.com");
        testedPerson.setPersonRole(PersonRole.STUDENT);
    }

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackage(PersonEntity.class.getPackage())
                .addPackage(PersonVO.class.getPackage())
                .addPackage(PersonConverter.class.getPackage())
                .addClasses(PersonDAO.class, CourseDAO.class, GenericDAO.class)
                .addClasses(PersonService.class, PersonServiceImpl.class, UnsupportedOperationException.class)
                .addAsResource("META-INF/sql/test-person-service.sql", "META-INF/sql/test-person-service.sql")
                .addAsResource("META-INF/test-person-service-persistence.xml", "META-INF/persistence.xml");
    }

    @EJB
    PersonService personService;

    @InSequence(Integer.MIN_VALUE + 1)
    @Test
    public void testPersonServiceShouldBeInjected() {
        assertThat(personService, is(notNullValue()));
    }

    @InSequence(Integer.MIN_VALUE + 2)
    @Test
    public void testGetById() {
        PersonVO predefinedTeacherFromService = personService.getById(predefinedTeacher.getId());
        assertThat(predefinedTeacherFromService, is(equalTo(predefinedTeacher)));
        PersonVO predefinedStudentFromService = personService.getById(predefinedStudent.getId());
        assertThat(predefinedStudentFromService, is(equalTo(predefinedStudent)));
    }

    @InSequence(Integer.MIN_VALUE + 3)
    @Test
    public void testSave() {
        assertThat(testedPerson.getId(), is(nullValue()));
        personService.save(testedPerson);
        assertThat(testedPerson.getId(), is(notNullValue()));
        PersonVO testedPersonFromService = personService.getById(testedPerson.getId());
        assertThat(testedPersonFromService, is(equalTo(testedPerson)));
    }

    @InSequence(Integer.MIN_VALUE + 4)
    @Test
    public void testRemove() {
        PersonVO testedPersonFromService = personService.getById(testedPerson.getId());
        assertThat(testedPersonFromService, is(notNullValue()));
        personService.remove(testedPerson);
        testedPersonFromService = personService.getById(testedPerson.getId());
        assertThat(testedPersonFromService, is(nullValue()));
    }

    @InSequence(Integer.MIN_VALUE + 5)
    @Test
    public void testGetCourses() {
        Set<CourseVO> teacherCourses = personService.getCourses(predefinedTeacher);
        assertThat(teacherCourses, is(notNullValue()));
        assertThat(teacherCourses.size(), is(predefinedTeacherCoursesCnt));
    }

    @InSequence(Integer.MIN_VALUE + 6)
    @Test
    public void testAddPersonToCourse() {
        assertThat(predefinedStudentCoursesCnt, is(0));
        personService.addPersonToCourse(predefinedStudent, predefinedCourse);
        Set<CourseVO> courses = personService.getCourses(predefinedStudent);
        assertThat(courses, is(notNullValue()));
        assertThat(courses.size(), is(predefinedStudentCoursesCnt + 1));
    }

    @InSequence(Integer.MIN_VALUE + 7)
    @Test
    public void testRemovePersonFromCourse() {
        assertThat(personService.getCourses(predefinedStudent).size(), is(predefinedStudentCoursesCnt + 1));
        personService.removePersonFromCourse(predefinedStudent, predefinedCourse);
        Set<CourseVO> courses = personService.getCourses(predefinedStudent);
        assertThat(courses, is(notNullValue()));
        assertThat(courses.size(), is(predefinedStudentCoursesCnt));
    }

    @InSequence(Integer.MIN_VALUE + 8)
    @Test
    public void testAddPersonToCourseForPersonAlreadyAddedToCourse() {
        Set<CourseVO> teacherCourses = personService.getCourses(predefinedTeacher);
        assertThat(teacherCourses, is(notNullValue()));
        assertThat(teacherCourses.size(), is(predefinedTeacherCoursesCnt));
        assertThat(teacherCourses.iterator().next(), is(equalTo(predefinedCourse)));

        personService.addPersonToCourse(predefinedTeacher, predefinedCourse);
        teacherCourses = personService.getCourses(predefinedTeacher);
        assertThat(teacherCourses, is(notNullValue()));
        assertThat(teacherCourses.size(), is(predefinedTeacherCoursesCnt));
    }


    @Test
    public void testGetByIdForNotExistingIdShouldReturnNull() {
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
    public void testGetByEmail() {
        PersonVO predefinedTeacherFromServiceByEmail = personService.getByEmail(predefinedTeacher.getEmail());
        assertThat(predefinedTeacherFromServiceByEmail, is(equalTo(predefinedTeacher)));
    }

    @Test
    public void testGetByEmailForNotExistingEmailShouldBeNull() {
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
    public void testSaveForAlreadyExistingPersonShouldDoNothing() {
        final Long existingPersonId = predefinedTeacher.getId();
        PersonVO personFromService = personService.getById(existingPersonId);
        assertThat(personFromService, is(notNullValue()));
        personService.save(personFromService);
        assertThat(personFromService.getId(), is(equalTo(existingPersonId)));
    }

    @Test(expected = RuntimeException.class)
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
    public void testUpdate() {
        predefinedTeacher.setName("updated name");
        PersonVO personFromService = personService.getById(predefinedTeacher.getId());
        assertThat(personFromService, not(equalTo(predefinedTeacher)));
        personFromService = personService.update(predefinedTeacher);
        assertThat(personFromService, is(equalTo(predefinedTeacher)));
    }

    @Test(expected = RuntimeException.class)
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

    @Test(expected = RuntimeException.class)
    public void testRemoveForNullPersonShouldThrowConstraintViolationException() {
        try {
            personService.remove(null);
        } catch (EJBException e) {
            assertThat(e.getCause(), is(instanceOf(ConstraintViolationException.class)));
            if (checkNotNullArgumentViolationException((ConstraintViolationException) e.getCause())) {
                throw e;
            }
        }
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
        final Set<String> expectedViolationMessages = Collections.unmodifiableSet(new HashSet(Collections.nCopies(expectedViolationsCnt, "may not be null")));
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        if (constraintViolations.size() != expectedViolationsCnt) {
            return false;
        }
        Set<String> violationMessages = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());
        return violationMessages.equals(expectedViolationMessages);
    }
}