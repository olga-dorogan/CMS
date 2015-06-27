package org.javatraining.service;

import org.javatraining.entity.PersonRole;
import org.javatraining.model.CourseVO;
import org.javatraining.model.NewsVO;
import org.javatraining.model.PersonVO;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.CleanupStrategy;
import org.jboss.arquillian.persistence.TestExecutionPhase;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.sql.Date;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;

/**
 * Created by vika on 14.06.15.
 */
@RunWith(Arquillian.class)
@Cleanup(phase = TestExecutionPhase.BEFORE, strategy = CleanupStrategy.STRICT)
public class CourseServiceTest {
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

    private CourseVO courseVOInitialization(CourseVO courseVO){
        courseVO.setName("JavaEE");
        courseVO.setStartDate(Date.valueOf("2015-10-10"));
        courseVO.setEndDate(Date.valueOf("2016-11-11"));
        courseVO.setDescription("Java");
        return courseVO;
    }
    private PersonVO personVOInit(PersonVO personVO){
        personVO.setName("Petro");
        personVO.setEmail("Petrovg@mail.ru");
        personVO.setLastName("Last Name");
        personVO.setSecondName("Second name");
        personVO.setPersonRole(PersonRole.TEACHER);
        return personVO;
    }
    @Test
    public void testSaveReturnCourseVO() {
        CourseVO courseVO = courseVOInitialization(new CourseVO());
        assertEquals(courseVO,courseService.save(courseVO));
    }


    @Test
    public void testUpdateReturnCourseVO() {
        CourseVO courseVO = courseVOInitialization(new CourseVO());
        courseVO.setName("Other name");
        assertEquals(courseVO, courseService.update(courseVO));
    }

    @Test
    public void testRemoveReturnCourseVO() {
        CourseVO courseVO = courseVOInitialization(new CourseVO());
        courseService.save(courseVO);
        assertEquals(courseVO,courseService.remove(courseVO));
    }


    @Test
    public void testGetReturnCourseVO() {
        CourseVO courseVOForSave = new CourseVO();
        courseService.save(courseVOInitialization(courseVOForSave));
        assertEquals(courseService.getById(courseVOInitialization(courseVOForSave).getId()), courseVOInitialization(courseVOForSave));
    }

    @Test
    public void testGetAllCourses() {
        assertThat(courseService.getAll(), is(notNullValue()));
    }
    @Test
    public void testGetAllPersonsFromCourseByRole(){
        CourseVO courseVO = courseVOInitialization(new CourseVO());
        PersonVO personVOStudent = personVOInit(new PersonVO());
        PersonVO personVOTeacher = personVOInit(new PersonVO());
        personVOTeacher.setPersonRole(PersonRole.TEACHER);
        personService.save(personVOStudent);
        personService.save(personVOTeacher);
        courseService.save(courseVO);
        courseService.getAllPersonsFromCourseByRole(courseVO,PersonRole.TEACHER);
    }


    @Test
    public void testGetAllNewsFromCourse() {
        CourseVO courseVO = new CourseVO();
        NewsVO newsVO = new NewsVO();
        courseService.save(courseVOInitialization(courseVO));
//      courseService.addNewsToCourse(courseVO,newsVO);
        // TODO: !!! need to use NewsService

    }

}
