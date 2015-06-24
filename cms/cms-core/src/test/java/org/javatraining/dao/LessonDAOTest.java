package org.javatraining.dao;

import org.javatraining.entity.CourseEntity;
import org.javatraining.entity.LessonEntity;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
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

    @Deployment
    public static WebArchive createDeployment() {

        WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addPackage("org.javatraining.dao")
                .addPackage("org.javatraining.entity")
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        System.out.println(war.toString(true));
        return war;
    }


    public LessonEntity lessonEntityInit(LessonEntity lessonEntity, CourseEntity courseEntity){
        courseEntity.setDescription("Java");
        courseEntity.setName("JavaEE");
        courseEntity.setStartdate(Date.valueOf("2015-11-11"));
        courseEntity.setEnddate(Date.valueOf("2016-11-11"));
        courseDAO.save(courseEntity);
        lessonEntity.setCourse(courseEntity);
        lessonEntity.setCreateDate(Date.valueOf("2016-08-11"));
        lessonEntity.setType((long) 675);
        lessonEntity.setOrderNum((long) 764);
        return lessonEntity;
    }

    @Test
    public void testSaveReturnLessonEntity() {
        CourseEntity courseEntity = new CourseEntity();
        LessonEntity lessonEntity = lessonEntityInit(new LessonEntity(), courseEntity);
        assertEquals(lessonDAO.save(lessonEntity), lessonEntity);
    }

    @Test
    public void testUpdateReturnLessonEntity() {
        CourseEntity courseEntity = new CourseEntity();
        LessonEntity lessonEntity = lessonEntityInit(new LessonEntity(), courseEntity);
        lessonDAO.save(lessonEntity);
        lessonEntity.setDescription("description other");
        assertEquals(lessonDAO.update(lessonEntity), lessonEntity);
    }
}
