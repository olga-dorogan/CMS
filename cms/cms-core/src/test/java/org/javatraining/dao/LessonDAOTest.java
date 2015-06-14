package org.javatraining.dao;

import org.javatraining.entity.CourseEntity;
import org.javatraining.entity.LessonEntity;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.sql.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by vika on 12.06.15.
 */
@RunWith(Arquillian.class)
public class LessonDAOTest {

    @EJB
    LessonDAO lessonDAO;

    @EJB
    CourseDAO courseDAO;

    private LessonEntity lessonEntity;
    private CourseEntity courseEntity;

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addPackage("org.javatraining.dao")
                .addPackage("org.javatraining.entity")
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml");
        return war;
    }

    @Before
    public void setUp() {
        courseEntity = new CourseEntity("JavaEE",Long.valueOf(2324),"Java",
                Date.valueOf("2015-10-10"),Date.valueOf("2016-11-11"));
        courseEntity.setId(Long.valueOf(1));
        courseEntity.setStartdate(Date.valueOf("2015-10-10"));
        courseEntity.setEnddate(Date.valueOf("2016-11-11"));
        courseEntity.setName("JavaEE");
        courseEntity.setOwner(Long.valueOf(2324));
        courseEntity.setDescription("Java");

        lessonEntity = new LessonEntity();
        lessonEntity.setDescription("JavaEE");
        lessonEntity.setCreateDate(Date.valueOf("2016-11-11"));
        lessonEntity.setOrderNum(Long.valueOf(3234));
        lessonEntity.setType(Long.valueOf(3234));
        lessonEntity.setTopic("topic");
        courseDAO.save(courseEntity);
        lessonEntity.setCourses(courseEntity);

    }

    @Test
    public void testSaveReturnLessonEntity() {
         assertEquals(lessonDAO.save(lessonEntity), lessonEntity);
    }

    @Test
    public void testUpdateReturnLessonEntity() {
       lessonDAO.save(lessonEntity);
       lessonEntity.setDescription("description other");
        assertEquals(lessonDAO.update(lessonEntity), lessonEntity);
    }

}
