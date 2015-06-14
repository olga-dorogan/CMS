package org.javatraining.dao;

import org.javatraining.entity.CourseEntity;
import org.javatraining.entity.LessonEntity;
import org.javatraining.entity.PracticeLessonEntity;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.transaction.Transactional;
import java.io.File;
import java.sql.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by vika on 12.06.15.
 */
@RunWith(Arquillian.class)
@Transactional
public class PracticeLessonDAOTest {
    private PracticeLessonEntity practiceLessonEntity;
    private LessonEntity lessonEntity;
    private CourseEntity courseEntity;
    @EJB
   PracticeLessonDAO practiceLessonDAO;
    @EJB
    LessonDAO lessonDAO;
    @EJB
    CourseDAO courseDAO;

    @Deployment
    public static WebArchive createDeployment() {
        File[] files = Maven.resolver().loadPomFromFile("cms-core/pom.xml")
                .importTestDependencies().resolve().withTransitivity().asFile();
        WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addPackage("org.javatraining.dao")
                .addPackage("org.javatraining.entity")
                .addAsLibraries(files)
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        System.out.println(war.toString(true));
        return war;
    }

    private PracticeLessonEntity practiceLessonEntityInit(PracticeLessonEntity practiceLessonEntity,LessonEntity lessonEntity,CourseEntity courseEntity){
        courseEntity.setName("JavaEE");
        courseEntity.setStartdate(Date.valueOf("2015-10-10"));
        courseEntity.setEnddate(Date.valueOf("2016-11-11"));
        courseEntity.setOwner((long) 2324);
        courseEntity.setDescription("Java");
        courseDAO.save(courseEntity);
        lessonEntity.setType((long) 3234);
        lessonEntity.setOrderNum((long) 3235);
        lessonEntity.setTopic("topic");
        lessonEntity.setCreateDate(Date.valueOf("2015-10-10"));
        lessonEntity.setDescription("description");
        lessonEntity.setCourses(courseEntity);
        lessonDAO.save(lessonEntity);
      practiceLessonEntity.setTask("task");
        practiceLessonEntity.setLesson(lessonEntity);
        return practiceLessonEntity;
    }

    @Test
    public void testSaveReturnCourseEntity() {
        CourseEntity courseEntity = new CourseEntity();
        LessonEntity lessonEntity = new LessonEntity();
        PracticeLessonEntity practiceLessonEntity = new PracticeLessonEntity();
        assertEquals(practiceLessonEntityInit(practiceLessonEntity, lessonEntity, courseEntity),practiceLessonDAO.save(practiceLessonEntityInit(practiceLessonEntity, lessonEntity, courseEntity)));
    }

}
