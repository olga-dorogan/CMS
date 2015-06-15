package org.javatraining.service;

import org.javatraining.model.CourseVO;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.sql.Date;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by vika on 14.06.15.
 */
@RunWith(Arquillian.class)
public class CourseServiceImplTest {
    @Inject
    private CourseService courseService;

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addPackage("org.javatraining.service.impl")
                .addPackage("org.javatraining.service")
                .addPackage("org.javatraining.model.conversion")
                .addPackage("org.javatraining.service.exception")
                .addPackage("org.javatraining.model")
                .addPackage("org.javatraining.entity")
                .addPackage("org.javatraining.dao")
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        System.out.println(war.toString(true));
        return war;
    }

    private CourseVO courseVOInit(CourseVO courseVO){
        courseVO.setName("JavaEE");
        courseVO.setStartDate(Date.valueOf("2015-10-10"));
        courseVO.setEndDate(Date.valueOf("2016-11-11"));
        courseVO.setDescription("Java");
        return courseVO;
    }
    @Test
    public void testSaveReturnCourseVO() {
        CourseVO courseVO = courseVOInit(new CourseVO());
        assertEquals(courseVO,courseService.save(courseVO));
    }


    @Test
    public void testUpdateReturnCourseVO() {
        CourseVO courseVO = courseVOInit(new CourseVO());
        courseVO.setName("Other name");
        assertEquals(courseVO, courseService.update(courseVO));
    }

    @Test
    public void testRemoveReturnCourseVO() {
        CourseVO courseVO = courseVOInit(new CourseVO());
        courseService.save(courseVO);
        assertEquals(courseVO,courseService.remove(courseVO));
    }


    @Test
    public void testGetReturnCourseVO() {
        CourseVO courseVO = new CourseVO();
        courseService.save(courseVOInit(courseVO));
        assertEquals(courseService.getById(courseVOInit(courseVO).getId()), courseVOInit(courseVO));
    }

    @Test
    public void testGetAllCourses() {
        CourseVO courseVO = new CourseVO();
        courseService.save(courseVOInit(courseVO));
        assertEquals(courseService.getAll(), Arrays.asList(courseVO));
    }

}
