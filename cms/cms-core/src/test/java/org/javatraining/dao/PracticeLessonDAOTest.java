package org.javatraining.dao;

import org.javatraining.entity.CourseEntity;
import org.javatraining.entity.LessonEntity;
import org.javatraining.entity.PracticeLessonEntity;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.CleanupStrategy;
import org.jboss.arquillian.persistence.TestExecutionPhase;
import org.jboss.arquillian.persistence.UsingDataSet;
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

/**
 * Created by vika on 12.06.15.
 */
@RunWith(Arquillian.class)
@Cleanup(phase = TestExecutionPhase.BEFORE, strategy = CleanupStrategy.STRICT)
@UsingDataSet(value = "dao-tests/practice-lesson/one-lesson-and-practice.json")
public class PracticeLessonDAOTest {
    @EJB
    PracticeLessonDAO practiceLessonDAO;

    @EJB
    LessonDAO lessonDAO;


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

    private PracticeLessonEntity practiceLessonEntityInitialisation(PracticeLessonEntity practiceLessonEntity, LessonEntity lessonEntity, CourseEntity courseEntity){
        courseEntity.setName("JavaEE");
        courseEntity.setStartdate(Date.valueOf("2015-10-10"));
        courseEntity.setEnddate(Date.valueOf("2016-11-11"));
        courseEntity.setDescription("Java");
        lessonEntity.setType((long) 3234);
        lessonEntity.setOrderNum((long) 3235);
        lessonEntity.setTopic("topic");
        lessonEntity.setCreateDate(Date.valueOf("2015-10-10"));
        lessonEntity.setDescription("description");
        lessonEntity.setCourses(courseEntity);
        practiceLessonEntity.setTask("task");
        practiceLessonEntity.setLesson(lessonEntity);
        return practiceLessonEntity;
    }

    private PracticeLessonEntity predefinedPracticeLessonInitialization(PracticeLessonEntity practiceLessonEntity){
        Long predefinedLessonId = (long) 1;
        Long predefinedPracticeLessonId = (long) 1;
       LessonEntity lessonEntity = lessonDAO.getById(predefinedLessonId);
       practiceLessonEntity.setTask("practiceTask");
        practiceLessonEntity.setLesson(lessonEntity);
        practiceLessonEntity.setId(predefinedPracticeLessonId);
        return practiceLessonEntity;
    }


    @Test
    public void testPracticeLessonDAOShouldBeInjected() throws Exception {
        assertThat(practiceLessonDAO, is(notNullValue()));
    }

    @Test
    public void testSaveReturnPracticeLessonEntity() {
       PracticeLessonEntity practiceLessonForSave =  predefinedPracticeLessonInitialization(new PracticeLessonEntity());
       assertEquals(practiceLessonForSave,practiceLessonDAO.save(practiceLessonForSave));
    }

    @Test
    public void testUpdateReturnPracticeLessonEntity() {
        PracticeLessonEntity practiceLessonEntity = new PracticeLessonEntity();
        predefinedPracticeLessonInitialization(practiceLessonEntity);
        practiceLessonEntity.setTask("otherTask");
        practiceLessonDAO.update(practiceLessonEntity);
           }
}
