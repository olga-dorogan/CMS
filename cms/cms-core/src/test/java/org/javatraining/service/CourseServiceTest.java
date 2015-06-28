package org.javatraining.service;

import org.javatraining.entity.enums.PersonRole;
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

import javax.ejb.EJBException;
import javax.inject.Inject;
import java.sql.Date;

import static org.assertj.core.api.StrictAssertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by vika on 14.06.15.
 */
@RunWith(Arquillian.class)
@Cleanup(phase = TestExecutionPhase.BEFORE, strategy = CleanupStrategy.STRICT)
@UsingDataSet(value = "datasets/course-service-test/course/one-course.json")
public class CourseServiceTest {

    private static final String DS_DIR = "course-service-test/";
    private static final String DS_EMPTY = DS_DIR + "empty.json";
    private static final String DS_COURSE = DS_DIR + "course/one-course.json";

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
                .addPackage("org.javatraining.entity.enums")
                .addPackage("org.javatraining.dao")
                .addPackage("org.assertj.core.api")
                .addPackage("org.assertj.core.error")
                .addPackage("org.assertj.core.util.introspection")
                .addPackage("org.assertj.core.util")
                .addPackage("org.assertj.core.presentation")
                .addPackage("org.assertj.core.internal")
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        System.out.println(war.toString(true));
        return war;
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
    @ShouldMatchDataSet(value = {DS_EMPTY, DS_COURSE})
    public void testUpdateNotValidCourseTrowEJBException() throws EJBException {
        assertThatThrownBy(() -> courseService.update(new CourseVO()))
                .isInstanceOf(EJBException.class);
    }

    @Test
    @ShouldMatchDataSet(value = {DS_EMPTY, DS_COURSE})
    public void testRemoveNotValidCourseTrowEJBException() throws EJBException {
        assertThatThrownBy(() -> courseService.remove(new CourseVO()))
                .isInstanceOf(EJBException.class);
    }

    @Test
    @ShouldMatchDataSet(value = {DS_EMPTY, DS_COURSE})
    public void testSaveNotValidCourseTrowEJBException() throws EJBException {
        assertThatThrownBy(() -> courseService.save(new CourseVO()))
                .isInstanceOf(EJBException.class);

    }

    @Test
    @ShouldMatchDataSet(value = {DS_EMPTY, DS_COURSE})
    public void testSaveNullCourseTrowEJBException() throws EJBException {
        assertThatThrownBy(() -> courseService.save(null))
                .isInstanceOf(EJBException.class);

    }

    @Test
    @ShouldMatchDataSet(value = {DS_EMPTY, DS_COURSE})
    public void testRemoveNullCourseTrowEJBException() throws EJBException{
        assertThatThrownBy(() -> courseService.remove(null))
                .isInstanceOf(EJBException.class);
    }

    @Test
    @ShouldMatchDataSet(value = {DS_EMPTY, DS_COURSE})
    public void testUpdateNullCourseTrowEJBException() throws EJBException {
        assertThatThrownBy(() -> courseService.update(null))
                .isInstanceOf(EJBException.class);
    }



        @Test
    public void testGetAllNewsFromCourse() {
        CourseVO courseVO = new CourseVO();
        NewsVO newsVO = new NewsVO();
        courseService.save(courseVOInitialization());
//      courseService.addNewsToCourse(courseVO,newsVO);
        // TODO: !!!

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
    private PersonVO personVOInitializationForTests(){
        PersonVO personVO = new PersonVO();
        personVO.setName("Petro");
        personVO.setEmail("Petrovg@mail.ru");
        personVO.setLastName("Last Name");
        personVO.setSecondName("Second name");
        personVO.setPersonRole(PersonRole.TEACHER);
        return personVO;
    }

}
