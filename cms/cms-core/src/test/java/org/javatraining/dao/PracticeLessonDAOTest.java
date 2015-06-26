package org.javatraining.dao;

import org.hamcrest.Matcher;
import org.hamcrest.core.IsNull;
import org.javatraining.dao.exception.EntityNotExistException;
import org.javatraining.dao.exception.EntityIsAlreadyExistException;
import org.javatraining.entity.LessonEntity;
import org.javatraining.entity.PracticeLessonEntity;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.*;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;

/**
 * Created by vika on 12.06.15.
 */
@RunWith(Arquillian.class)
@Cleanup(phase = TestExecutionPhase.BEFORE, strategy = CleanupStrategy.STRICT)
@UsingDataSet(value = "dao-tests/practice-lesson/one-practice-lesson.json")
public class PracticeLessonDAOTest {
    @EJB
    PracticeLessonDAO practiceLessonDAO;

    @EJB
    LessonDAO lessonDAO;

    private static final String DS_DIR = "dao-tests/";
    private static final String DS_EMPTY = DS_DIR + "empty.json";

    private static final String DS_PRACTICE_LESSON = DS_DIR + "practice-lesson/one-practice-lesson.json";
    private static final String DS_PRACTICE_LESSON_AFTER_UPDATE = DS_DIR + "practice-lesson/expected-after-update.json";
    private static final String DS_PRACTICE_LESSON_AFTER_SAVE = DS_DIR + "practice-lesson/expected-after-save.json";
    private static final String DS_PRACTICE_LESSON_AFTER_REMOVE = DS_DIR + "practice-lesson/expected-after-remove.json";


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

    @Test
    public void testPracticeLessonDAOShouldBeInjected() throws Exception {
        assertThat(practiceLessonDAO, is(notNullValue()));
    }

    @Test(expected = RuntimeException.class)
    public void testGetByIdForNotExistingIdShouldReturnEntityDoesNotExistException() {
        Long notExistingId = 10L;
        try {
            PracticeLessonEntity courseWithNotExistingId = practiceLessonDAO.getById(notExistingId);
            assertThat(courseWithNotExistingId, is(IsNull.nullValue()));
        } catch (EntityNotExistException e) {
            assertThat(e.getCause(), is((Matcher) instanceOf(ConstraintViolationException.class)));
            if (checkNotNullArgumentViolationException((ConstraintViolationException) e.getCause())) {
                throw e;
            }
        }
    }

    @Test(expected = RuntimeException.class)
    public void testGetByIdForNullIdShouldThrowConstraintViolationException() throws Exception {
        try {
            practiceLessonDAO.getById(null);
        } catch (EJBException e) {
            assertThat(e.getCause(), is((Matcher) instanceOf(ConstraintViolationException.class)));
            if (checkNotNullArgumentViolationException((ConstraintViolationException) e.getCause())) {
                throw e;
            }
        }
    }

    @Test(expected = RuntimeException.class)
    @ShouldMatchDataSet(value = {DS_EMPTY, DS_PRACTICE_LESSON}, excludeColumns = {"id"})
    public void testSaveLessonLinkThatAlreadyExistTrowEntityIsAlreadyExistException() {
        PracticeLessonEntity practiceLessonForSave = predefinedPracticeLessonInitialization(new PracticeLessonEntity());
        try {
            practiceLessonDAO.save(practiceLessonForSave);
        } catch (EntityIsAlreadyExistException e) {
            assertThat(e.getCause(), is((Matcher) instanceOf(ConstraintViolationException.class)));
            if (checkNotNullArgumentViolationException((ConstraintViolationException) e.getCause())) {
                throw e;
            }
        }
    }

    @Test
    public void testSaveReturnPracticeLessonEntity() {
        PracticeLessonEntity practiceLessonForSave = practiceLessonInitialisationForTests(new PracticeLessonEntity());
        assertEquals(practiceLessonForSave, practiceLessonDAO.save(practiceLessonForSave));
    }


    @ShouldMatchDataSet(value = {DS_EMPTY, DS_PRACTICE_LESSON}, excludeColumns = {"id"})
    public void testSaveForNullPracticeLesson() {
        try {
            practiceLessonDAO.save(null);
        } catch (EJBException e) {
            assertThat(e.getCause(), is((Matcher) instanceOf(ConstraintViolationException.class)));
            if (checkNotNullArgumentViolationException((ConstraintViolationException) e.getCause())) {
                throw e;
            }
        }
    }

    @Test(expected = EJBException.class)
    @ShouldMatchDataSet(value = {DS_EMPTY, DS_PRACTICE_LESSON}, excludeColumns = {"id"})
    public void testSaveForNotValidPerson() {
        practiceLessonDAO.save(new PracticeLessonEntity());
    }


    @Test
    @ShouldMatchDataSet(value = {DS_EMPTY, DS_PRACTICE_LESSON_AFTER_SAVE}, excludeColumns = {"id"})
    public void testSavePracticeLesson() {
        PracticeLessonEntity practiceLessonForSave = practiceLessonInitialisationForTests(
                new PracticeLessonEntity());
        practiceLessonDAO.save(practiceLessonForSave);
    }


    @Test
    @ShouldMatchDataSet(value = {DS_EMPTY, DS_PRACTICE_LESSON}, excludeColumns = {"id"})
    public void testRemoveNotExistedPracticeLessonShouldDoNothing() {
        PracticeLessonEntity practiceLessonForRemove = practiceLessonInitialisationForTests(
                new PracticeLessonEntity());
        practiceLessonDAO.remove(practiceLessonForRemove);
    }

    @Test
    public void testUpdateReturnPracticeLessonEntity() {
        PracticeLessonEntity practiceLessonEntity = new PracticeLessonEntity();
        predefinedPracticeLessonInitialization(practiceLessonEntity);
        practiceLessonEntity.setTask("otherTask");
        practiceLessonDAO.update(practiceLessonEntity);
    }


    private PracticeLessonEntity practiceLessonInitialisationForTests(PracticeLessonEntity practiceLessonEntity) {
        Long predefinedLessonId = 1L;
        LessonEntity predefinedLesson = lessonDAO.getById(predefinedLessonId);
        practiceLessonEntity.setTask("otherPracticeTask");
        practiceLessonEntity.setLesson(predefinedLesson);
        return practiceLessonEntity;
    }

    private PracticeLessonEntity predefinedPracticeLessonInitialization(PracticeLessonEntity practiceLessonEntity) {
        Long predefinedLessonId = 1L;
        Long predefinedPracticeLessonId = 1L;
        practiceLessonEntity.setId(predefinedPracticeLessonId);
        LessonEntity predefinedLesson = lessonDAO.getById(predefinedLessonId);
        practiceLessonEntity.setTask("practiceTask");
        practiceLessonEntity.setLesson(predefinedLesson);


        return practiceLessonEntity;
    }

    private boolean checkNotNullArgumentViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        return !(constraintViolations.size() != 1 ||
                !constraintViolations.iterator().next().getMessage().equals("may not be null"));
    }
}
