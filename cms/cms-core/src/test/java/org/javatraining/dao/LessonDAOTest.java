package org.javatraining.dao;

import org.javatraining.entity.CourseEntity;
import org.javatraining.entity.LessonEntity;
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

/**
 * Created by vika on 12.06.15.
 */
@RunWith(Arquillian.class)
@Cleanup(phase = TestExecutionPhase.BEFORE, strategy = CleanupStrategy.STRICT)
@UsingDataSet(value = "dao-tests/lesson/one-lesson.json")
public class LessonDAOTest {

    @EJB
    LessonDAO lessonDAO;

    @EJB
    CourseDAO courseDAO;

    private static final String DS_DIR = "dao-tests/";
    private static final String DS_EMPTY = DS_DIR + "empty.json";

    private static final String DS_LESSON = DS_DIR + "lesson/one-lesson.json";
    private static final String DS_LESSON_AFTER_UPDATE = DS_DIR + "lesson/expected-after-update.json";
    private static final String DS_LESSON_AFTER_SAVE = DS_DIR + "lesson/expected-after-save.json";
    private static final String DS_LESSON_AFTER_REMOVE = DS_DIR + "lesson/expected-after-remove.json";
    @Deployment
    public static WebArchive createDeployment() {

        WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addPackage("org.javatraining.dao")
                .addPackage("org.javatraining.entity")
                .addPackage("org.javatraining.dao.exception")
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        System.out.println(war.toString(true));
        return war;
    }

    private LessonEntity predefinedLessonInitialization(LessonEntity lessonEntity){
        CourseEntity courseEntity = courseDAO.getById((long) 1);
        lessonEntity.setCourses(courseEntity);
        lessonEntity.setCreateDate(Date.valueOf("2016-08-11"));
        lessonEntity.setTopic("topic");
        lessonEntity.setDescription("Some description");
        lessonEntity.setType((long) 1);
        lessonEntity.setOrderNum((long) 764);
       lessonEntity.setId((long) 1);
        return lessonEntity;
    }



    public LessonEntity lessonEntityInitialization(LessonEntity lessonEntity){
        CourseEntity courseEntity = courseDAO.getById((long) 1);

        lessonEntity.setCourses(courseEntity);
        lessonEntity.setCreateDate(Date.valueOf("2016-08-11"));
        lessonEntity.setTopic("topic");
        lessonEntity.setDescription("Some description");
        lessonEntity.setType((long) 1);
        lessonEntity.setOrderNum((long) 764);

        return lessonEntity;
    }

    @Test
    public void testLessonDAOShouldBeInjected() throws Exception {
        assertThat(lessonDAO, is(notNullValue()));
    }

    @Test
    public void testSaveReturnLesson() {
        LessonEntity lessonForSave = lessonEntityInitialization(new LessonEntity());
        assertEquals(lessonForSave,lessonDAO.save(lessonForSave));
    }

     @Test
   @ShouldMatchDataSet(value = {DS_EMPTY, DS_LESSON_AFTER_UPDATE}, excludeColumns = {"id"})
    public void testUpdateLesson() {
        LessonEntity lessonForUpdate = predefinedLessonInitialization(new LessonEntity());
        lessonForUpdate.setDescription("Other description");
        lessonDAO.update(lessonForUpdate);
      }

    @Test
    public void testUpdateReturnLessonEntity() {
        LessonEntity lessonForUpdate = predefinedLessonInitialization(new LessonEntity());
        lessonForUpdate.setDescription("Other description");
        assertEquals(lessonForUpdate, lessonDAO.update(lessonForUpdate));
     }


    @Test
    @ShouldMatchDataSet(value = {DS_LESSON_AFTER_REMOVE})
    public void testRemoveLesson() {
        LessonEntity lessonForRemove = predefinedLessonInitialization(new LessonEntity());
        lessonDAO.remove(lessonForRemove);
    }

    @Test
    @ShouldMatchDataSet(value = {DS_EMPTY, DS_LESSON}, excludeColumns = {"id"})
    public void testGetAllCourses() {
        assertThat(lessonDAO.getAllLessons(), is(notNullValue()));
    }

}
