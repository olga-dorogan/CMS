package org.javatraining.dao;

import org.hamcrest.Matcher;
import org.hamcrest.core.IsNull;
import org.javatraining.dao.exception.EntityNotExistException;
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
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.Date;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
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


    @Test
    public void testLessonDAOShouldBeInjected() {
        assertThat(lessonDAO, is(notNullValue()));
    }

    @Test(expected = RuntimeException.class)
    public void testGetByIdForNotExistingIdShouldReturnShouldReturnEntityDoesNotExistException() {
        Long notExistingId = 10L;
        try {
            LessonEntity courseWithNotExistingId = lessonDAO.getById(notExistingId);
            assertThat(courseWithNotExistingId, is(IsNull.nullValue()));
        } catch (EntityNotExistException e) {
        assertThat(e.getCause(), is((Matcher)instanceOf(ConstraintViolationException.class)));
        if (checkNotNullArgumentViolationException((ConstraintViolationException) e.getCause())) {
            throw e;
        }}}


    @Test
    public void testSaveReturnLesson() {
        LessonEntity lessonForSave = lessonInitializationForTests(new LessonEntity());
        assertEquals(lessonForSave, lessonDAO.save(lessonForSave));
    }

    @Test
    @ShouldMatchDataSet(value = {DS_EMPTY, DS_LESSON_AFTER_SAVE}, excludeColumns = {"id"})
    public void testSaveLesson() {
        LessonEntity lessonForSave = lessonInitializationForTests(new LessonEntity());
      lessonDAO.save(lessonForSave);
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
        List<LessonEntity> lessonEntities = lessonDAO.getAllLessons();
        LessonEntity predefinedLesson = predefinedLessonInitialization(new LessonEntity());
        predefinedLesson = lessonDAO.getById(predefinedLesson.getId());
        assertThat(lessonEntities, hasItem(predefinedLesson));
        assertThat(lessonEntities, is(notNullValue()));
    }


    private LessonEntity predefinedLessonInitialization(LessonEntity lessonEntity) {
        CourseEntity predefinedCourse =courseDAO.getById(1L);
        lessonEntity.setCourse(predefinedCourse);
        lessonEntity.setCreateDate(Date.valueOf("2016-08-11"));
        lessonEntity.setTopic("topic");
        lessonEntity.setDescription("Some description");
        lessonEntity.setType(1L);
        lessonEntity.setOrderNum(764L);
        lessonEntity.setId(1L);
        return lessonEntity;
    }


    public LessonEntity lessonInitializationForTests(LessonEntity lessonEntity) {
        CourseEntity predefinedCourse = courseDAO.getById(1L);

        lessonEntity.setCourse(predefinedCourse);
        lessonEntity.setCreateDate(Date.valueOf("2016-08-11"));
        lessonEntity.setTopic("topic");
        lessonEntity.setDescription("Some description");
        lessonEntity.setType(1L);
        lessonEntity.setOrderNum(764L);
        return lessonEntity;
    }

    private boolean checkNotNullArgumentViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        return !(constraintViolations.size() != 1 ||
                !constraintViolations.iterator().next().getMessage().equals("may not be null"));
    }
}
