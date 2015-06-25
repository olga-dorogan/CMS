package org.javatraining.dao;

import org.hamcrest.core.IsNull;
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
import java.sql.Date;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
@Cleanup(phase = TestExecutionPhase.BEFORE, strategy = CleanupStrategy.STRICT)
@UsingDataSet(value = "dao-tests/course/one-course.json")
public class CourseDAOTest {

    @EJB
    CourseDAO courseDAO;
    private static final String DS_DIR = "dao-tests/";
    private static final String DS_EMPTY = DS_DIR + "empty.json";

    private static final String DS_COURSE = DS_DIR + "course/one-course.json";
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

    private CourseEntity courseInitialization(CourseEntity courseEntity){
        courseEntity.setName("courseName");
        courseEntity.setStartdate(Date.valueOf("2015-10-10"));
        courseEntity.setEnddate(Date.valueOf("2016-11-11"));
        courseEntity.setDescription("Java");
        return courseEntity;
    }

    private CourseEntity predefinedCourseInitialization(CourseEntity courseEntity){
        courseEntity.setId((long)1);
        courseEntity.setName("courseName");
        courseEntity.setStartdate(Date.valueOf("2015-10-10"));
        courseEntity.setEnddate(Date.valueOf("2016-11-11"));
        courseEntity.setDescription("courseDescription");
        return courseEntity;
    }

    @Test
    public void testCourseDAOShouldBeInjected() throws Exception {
        assertThat(courseDAO, is(notNullValue()));
    }

    @Test
    public void testGetByIdForNotExistingIdShouldReturnNull() {
        Long notExistingId = (long) 10;
        CourseEntity courseWithNotExistingId = courseDAO.getById(notExistingId);
        assertThat(courseWithNotExistingId, is(IsNull.nullValue()));
      }

   @Test(expected = RuntimeException.class)
    public void testGetByIdForEmptyIdShouldThrowConstraintViolationException(){
            courseDAO.getById(null);
     }

    @Test
    public void testSaveCourseReturnCourseEntity() {
        CourseEntity courseEntity = new CourseEntity();
        assertEquals(courseInitialization(courseEntity), courseDAO.save(courseInitialization(courseEntity)));
    }

    @Test
    @ShouldMatchDataSet(value = {DS_EMPTY, DS_COURSE_AFTER_SAVE}, excludeColumns = {"id"})
    public void testSaveCourse() {
        CourseEntity courseEntity = new CourseEntity();
        courseDAO.save(courseInitialization(courseEntity));
    }

    @Test
    @ShouldMatchDataSet(value = {DS_EMPTY, DS_COURSE_AFTER_UPDATE}, excludeColumns = {"id"})
    public void testUpdateCourse() {
        CourseEntity courseForUpdate = predefinedCourseInitialization(new CourseEntity());
        courseForUpdate.setName("OtherName");
        courseDAO.update(courseForUpdate);
    }

    @Test
    public void testUpdateCourseReturnNewsEntity() {
        CourseEntity courseEntity = courseInitialization(new CourseEntity());
         assertEquals(courseEntity, courseDAO.update(courseEntity));
    }
    @Test
    public void testRemoveReturnCourseEntity() {
        CourseEntity courseEntity =  predefinedCourseInitialization(new CourseEntity());
        assertEquals(courseEntity, courseDAO.remove(courseEntity));
    }

    @Test
    @ShouldMatchDataSet(value = {DS_EMPTY})
    public void testRemoveCourse() {
        CourseEntity courseForRemove = predefinedCourseInitialization(new CourseEntity());
        courseDAO.remove(courseForRemove);
    }

    @Test
    public void testGetReturnCourseEntity() {
        CourseEntity courseEntity =  courseInitialization(new CourseEntity());
       courseDAO.save(courseEntity);
       assertEquals(courseEntity,courseDAO.getById(courseEntity.getId()));
    }

    @Test
    @ShouldMatchDataSet(value = {DS_EMPTY, DS_COURSE}, excludeColumns = {"id"})
    public void testGetAllCourses() {
        assertThat(courseDAO.getAllCourses(), is(notNullValue()));
    }


}