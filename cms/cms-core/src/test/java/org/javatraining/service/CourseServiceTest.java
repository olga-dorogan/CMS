package org.javatraining.service;

import org.javatraining.entity.PersonRole;
import org.javatraining.model.CourseVO;
import org.javatraining.model.NewsVO;
import org.javatraining.model.PersonVO;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.*;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.sql.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by vika on 14.06.15.
 */
@RunWith(Arquillian.class)
@Cleanup(phase = TestExecutionPhase.BEFORE, strategy = CleanupStrategy.STRICT)
@UsingDataSet(value = "datasets/person-service-test/course/one-course.json")
public class CourseServiceTest {

    private static final String DS_DIR = "course-service-test/";
    private static final String DS_EMPTY = DS_DIR + "empty.json";
    private static final String DS_COURSE = DS_DIR + "course/course.json";

    @Inject
    private CourseService courseService;

    @Inject
    private PersonService personService;

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addPackage("org.javatraining.service.impl")
                .addPackage("org.javatraining.service")
                .addPackage("org.javatraining.model.conversion")
                .addPackage("org.javatraining.service.exception")
                .addPackage("org.javatraining.dao.exception")
                .addPackage("org.javatraining.model")
                .addPackage("org.javatraining.entity")
                .addPackage("org.javatraining.dao")
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        System.out.println(war.toString(true));
        return war;
    }

    private CourseVO courseVOInitialization(){
        CourseVO courseVO = new CourseVO();
        courseVO.setName("JavaEE");
        courseVO.setStartDate(Date.valueOf("2015-10-10"));
        courseVO.setEndDate(Date.valueOf("2016-11-11"));
        courseVO.setDescription("Java");
        return courseVO;
    }

    private CourseVO predefinedCourseVO(){
        CourseVO courseVO = new CourseVO();
        courseVO.setId(1L);
        courseVO.setName("JavaEE");
        courseVO.setStartDate(Date.valueOf("2015-10-10"));
        courseVO.setEndDate(Date.valueOf("2016-11-11"));
        courseVO.setDescription("Java");
        return courseVO;
    }
    private PersonVO personVOInitialization(PersonVO personVO){
        personVO.setName("Petro");
        personVO.setEmail("Petrovg@mail.ru");
        personVO.setLastName("Last Name");
        personVO.setSecondName("Second name");
        personVO.setPersonRole(PersonRole.TEACHER);
        return personVO;
    }
    @Test
    public void testSaveReturnCourseVO() {
        CourseVO courseVO = courseVOInitialization();
        assertEquals(courseVO,courseService.save(courseVO));
    }


    @Test
    public void testUpdateReturnCourseVO() {
        CourseVO courseVO = courseVOInitialization();
        courseVO.setName("Other name");
        assertEquals(courseVO, courseService.update(courseVO));
    }

    @Test
    public void testRemoveReturnCourseVO() {
        CourseVO courseVO = predefinedCourseVO();
        assertEquals(courseVO,courseService.remove(courseVO));
    }
    @Test
    @ShouldMatchDataSet(value = DS_EMPTY)
    public void testRemoveCourse() {
        CourseVO courseVO = predefinedCourseVO();
       courseService.remove(courseVO);
    }


    @Test
    @ShouldMatchDataSet(value = {DS_EMPTY, DS_COURSE}, excludeColumns = {"id"})
    public void testGetReturnCourseVO() {
        CourseVO predefinedCourseVO = predefinedCourseVO();
        CourseVO courseVO = courseService.getById(predefinedCourseVO.getId());
        assertNotNull(courseVO);
        }

    @Test
    public void testGetAllCourses() {
        assertNotNull(courseService.getAll());
    }
    @Test
    public void testGetAllPersonsFromCourseByRole(){
        CourseVO courseVO = predefinedCourseVO();
      assertNotNull(courseService.getAllPersonsFromCourseByRole(courseVO,PersonRole.TEACHER));
    }


    @Test
    public void testGetAllNewsFromCourse() {
        CourseVO courseVO = new CourseVO();
        NewsVO newsVO = new NewsVO();
        courseService.save(courseVOInitialization());
//      courseService.addNewsToCourse(courseVO,newsVO);
        // TODO: !!!

    }

}
