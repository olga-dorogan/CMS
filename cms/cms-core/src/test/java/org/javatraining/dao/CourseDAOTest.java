package org.javatraining.dao;

import org.hamcrest.Matcher;
import org.hamcrest.core.IsNull;
import org.javatraining.dao.exception.EntityIsAlreadyExistException;
import org.javatraining.dao.exception.EntityNotExistException;
import org.javatraining.entity.CourseEntity;
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
import java.sql.Date;
import java.util.Set;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(Arquillian.class)
@Cleanup(phase = TestExecutionPhase.BEFORE, strategy = CleanupStrategy.STRICT)
@UsingDataSet(value = "dao-tests/course/course.json")
public class CourseDAOTest {

    @EJB
    CourseDAO courseDAO;
    private static final String DS_DIR = "dao-tests/";
    private static final String DS_EMPTY = DS_DIR + "empty.json";

    private static final String DS_COURSE = DS_DIR + "course/course.json";
    private static final String DS_COURSE_AFTER_UPDATE = DS_DIR + "course/expected-after-update.json";
    private static final String DS_COURSE_AFTER_SAVE = DS_DIR + "course/expected-after-save.json";

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
    public void testCourseDAOShouldBeInjected() throws Exception {
        assertNotNull(courseDAO);
    }

    @Test(expected = Exception.class)
    public void testGetByIdForNotExistingIdShouldReturnEntityDoesNotExistException() throws EntityIsAlreadyExistException{
        Long notExistingId = 10L;
              try {
            CourseEntity courseWithNotExistingId = courseDAO.getById(notExistingId);
                  fail("Test for mark should have thrown a EntityNotExistException");
            assertThat(courseWithNotExistingId, is(IsNull.nullValue()));
        }catch (EntityNotExistException e){
            assertThat(e.getCause(), is((Matcher)instanceOf(ConstraintViolationException.class)));
            if (checkNotNullArgumentViolationException((ConstraintViolationException) e.getCause())) {
                fail("Must throw exception");
            }
      }}


    @Test(expected = Exception.class)
    @ShouldMatchDataSet(value = {DS_EMPTY, DS_COURSE}, excludeColumns = {"id"})
    public void testSaveCourseThatExistThrowsEntityIsAlreadyExistException() throws EntityIsAlreadyExistException{
        CourseEntity courseEntity = predefinedCourseInitialization();
        courseEntity = courseDAO.getById(courseEntity.getId());
        try{
            courseDAO.save(courseEntity);
            fail("Test for mark should have thrown a EntityIsAlreadyExistException");
        }catch (EntityIsAlreadyExistException e) {
            assertEquals("Entity already exists", e.getMessage());
            assertThat(e.getCause(), is((Matcher)instanceOf(ConstraintViolationException.class)));
            if (checkNotNullArgumentViolationException((ConstraintViolationException) e.getCause())) {
                fail("Unexpected exception");
            }
        }
    }

    @Test
    public void testSaveCourseReturnCourseEntity() {
        CourseEntity predefinedCourse =courseInitializationForTests();
        assertEquals(predefinedCourse, courseDAO.save(predefinedCourse));
    }

    @Test
    public void testUpdateCourseReturnNewsEntity() {
        CourseEntity courseEntity = courseInitializationForTests();
        assertEquals(courseEntity, courseDAO.update(courseEntity));
    }
    @Test
    public void testRemoveReturnCourseEntity() {
        CourseEntity courseEntity =  predefinedCourseInitialization();
        assertEquals(courseEntity, courseDAO.remove(courseEntity));
    }

    @Test
    @ShouldMatchDataSet(value = {DS_EMPTY, DS_COURSE_AFTER_SAVE}, excludeColumns = {"id"})
    public void testSaveCourse() {
        courseDAO.save(courseInitializationForTests());
    }

    @Test
    @ShouldMatchDataSet(value = {DS_EMPTY, DS_COURSE_AFTER_UPDATE}, excludeColumns = {"id"})
    public void testUpdateCourse() {
        CourseEntity courseForUpdate = predefinedCourseInitialization();
        courseForUpdate.setName("OtherName");
        courseDAO.update(courseForUpdate);
    }

    @Test
    @ShouldMatchDataSet(value = {DS_EMPTY})
    public void testRemoveCourse() {
        CourseEntity courseForRemove = predefinedCourseInitialization();
        courseDAO.remove(courseForRemove);
    }

    @Test
    public void testSaveNullCourseTrowEJBException() throws EJBException {
        try {
            courseDAO.save(null);
            fail("Test for mark should have thrown a EJBException");
        } catch (EJBException e) {
            assertThat(e.getCause(), is((Matcher) instanceOf(ConstraintViolationException.class)));
            if (!checkNotNullArgumentViolationException((ConstraintViolationException) e.getCause())) {
                fail("Unexpected exception");
            }
        }
    }


    @Test
    public void testRemoveNullCourseTrowEJBException() throws EJBException{
        try {
            courseDAO.remove(null);
           fail("Test for mark should have thrown a EJBException");
        } catch (EJBException e) {
            assertThat(e.getCause(), is((Matcher) instanceOf(ConstraintViolationException.class)));
            if (!checkNotNullArgumentViolationException((ConstraintViolationException) e.getCause())) {
                fail("Unexpected exception");
            }
        }
    }
    @Test
    public void testUpdateNullCourseTrowEJBException() throws EJBException {
        try {
            courseDAO.update(null);
            fail("Test for mark should have thrown a EJBException");
        } catch (EJBException e) {
            assertThat(e.getCause(), is((Matcher) instanceOf(ConstraintViolationException.class)));
            if (!checkNotNullArgumentViolationException((ConstraintViolationException) e.getCause())) {
                fail("Unexpected exception");
            }
        }
    }


    @Test
    public void testGetReturnCourseEntity() {
        CourseEntity courseEntity =  courseInitializationForTests();
       courseDAO.save(courseEntity);
       assertEquals(courseEntity, courseDAO.getById(courseEntity.getId()));
    }

    @Test
    @ShouldMatchDataSet(value = {DS_EMPTY, DS_COURSE}, excludeColumns = {"id"})
    public void testGetAllCourses() {
        assertNotNull(courseDAO.getAllCourses());
    }

    @Test
    @ShouldMatchDataSet(value = DS_EMPTY)
    public void testClearDataBaseShouldBeEmpty(){
         courseDAO.clear();

    }


    private CourseEntity courseInitializationForTests(){
        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setName("courseName");
        courseEntity.setStartdate(Date.valueOf("2015-10-10"));
        courseEntity.setEnddate(Date.valueOf("2016-11-11"));
        courseEntity.setDescription("Java");
        return courseEntity;
    }

    private CourseEntity predefinedCourseInitialization(){
        CourseEntity predefinedCourse = new CourseEntity();
        predefinedCourse.setId((long) 1);
        predefinedCourse.setName("courseName");
        predefinedCourse.setStartdate(Date.valueOf("2015-10-10"));
        predefinedCourse.setEnddate(Date.valueOf("2016-11-11"));
        predefinedCourse.setDescription("courseDescription");
        return predefinedCourse;
    }

    private boolean checkNotNullArgumentViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        return !(constraintViolations.size() != 1 ||
                !constraintViolations.iterator().next().getMessage().equals("may not be null"));
    }


}