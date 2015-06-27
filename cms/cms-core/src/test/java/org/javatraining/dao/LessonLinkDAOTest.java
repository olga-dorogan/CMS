package org.javatraining.dao;

import org.hamcrest.Matcher;
import org.hamcrest.core.IsNull;
import org.javatraining.dao.exception.EntityDoesNotExistException;
import org.javatraining.dao.exception.EntityIsAlreadyExistException;
import org.javatraining.entity.LessonEntity;
import org.javatraining.entity.LessonLinkEntity;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.*;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
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
@UsingDataSet(value = "dao-tests/lesson-link/one-lesson-link.json")
public class LessonLinkDAOTest {
    @EJB
    LessonsLinksDAO lessonsLinksDAO;

    @EJB
    LessonDAO lessonDAO;


    private static final String DS_DIR = "dao-tests/";
    private static final String DS_EMPTY = DS_DIR + "empty.json";

    private static final String DS_LESSON_LINK = DS_DIR + "lesson-link/one-lesson-link.json";
    private static final String DS_LESSON_LINK_AFTER_UPDATE = DS_DIR + "lesson-link/expected-after-update.json";
    private static final String DS_LESSON_LINK_AFTER_SAVE = DS_DIR + "lesson-link/expected-after-save.json";
    private static final String DS_LESSON_LINK_AFTER_REMOVE = DS_DIR + "lesson-link/expected-after-remove.json";

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addPackage("org.javatraining.dao")
                .addPackage("org.javatraining.entity")
                .addPackage("org.javatraining.dao.exception")
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml");
        return war;
    }


    @Test
    public void testLessonLinkDAOShouldBeInjected() throws Exception {
        assertThat(lessonsLinksDAO, is(notNullValue()));
    }

    @Test(expected = RuntimeException.class)
    public void testGetByIdForNotExistingIdShouldReturnNull() {
        Long notExistingId = 10L;
       try {
           LessonLinkEntity courseWithNotExistingId = lessonsLinksDAO.getById(notExistingId);
           assertThat(courseWithNotExistingId, is(IsNull.nullValue()));
       } catch (EntityDoesNotExistException e) {
        assertThat(e.getCause(), is((Matcher)instanceOf(ConstraintViolationException.class)));
        if (checkNotNullArgumentViolationException((ConstraintViolationException) e.getCause())) {
            throw e;
        }
    }
    }

    @Test(expected = RuntimeException.class)
    @ShouldMatchDataSet(value = {DS_EMPTY, DS_LESSON_LINK}, excludeColumns = {"id"})
    public void testSaveLessonLinkThatAlreadyExistTrowEntityIsAlreadyExistException() {
        LessonLinkEntity lessonLinkForSave = predefinedLessonLinkInitialization(new LessonLinkEntity());
        try{
            lessonsLinksDAO.save(lessonLinkForSave);}
        catch (EntityIsAlreadyExistException e) {
            assertThat(e.getCause(), is((Matcher)instanceOf(ConstraintViolationException.class)));
            if (checkNotNullArgumentViolationException((ConstraintViolationException) e.getCause())) {
                throw e;
            }
        }
    }

    @Test
    @ShouldMatchDataSet(value = {DS_EMPTY, DS_LESSON_LINK_AFTER_SAVE}, excludeColumns = {"id"})
    public void testSaveLesson() {
        LessonLinkEntity lessonLinkEntity = lessonLinkEntityInitializationForTests(new LessonLinkEntity());
        lessonsLinksDAO.save(lessonLinkEntity);
    }


    @Test
    public void testSaveReturnLessonEntity() {
        LessonLinkEntity lessonLinkEntity = lessonLinkEntityInitializationForTests(new LessonLinkEntity());
       assertEquals(lessonsLinksDAO.save(lessonLinkEntity), lessonLinkEntity);
    }

    @Test
    public void testUpdateReturnLessonEntity() {
       LessonLinkEntity lessonLinkEntity = predefinedLessonLinkInitialization(new LessonLinkEntity());
        lessonLinkEntity.setDescription("otherDescription");
        assertEquals(lessonLinkEntity, lessonsLinksDAO.update(lessonLinkEntity));
    }

    @Test
    @ShouldMatchDataSet(value = {DS_EMPTY, DS_LESSON_LINK_AFTER_UPDATE}, excludeColumns = {"id"})
    public void testUpdateLesson() {
        LessonLinkEntity lessonLinkEntity = predefinedLessonLinkInitialization(new LessonLinkEntity());
        lessonLinkEntity.setDescription("Other description");
       lessonsLinksDAO.update(lessonLinkEntity);
    }

    @Test
    public void testRemoveReturnLessonEntity() {
        LessonLinkEntity lessonLinkEntity = predefinedLessonLinkInitialization(new LessonLinkEntity());
        assertEquals(lessonsLinksDAO.remove(lessonLinkEntity), lessonLinkEntity);
    }

    @Test
    @ShouldMatchDataSet(value = {DS_LESSON_LINK_AFTER_REMOVE})
    public void testRemoveLessonLink() {
        LessonLinkEntity lessonLinkEntity = predefinedLessonLinkInitialization(new LessonLinkEntity());
        lessonsLinksDAO.remove(lessonLinkEntity);
    }

    private LessonLinkEntity lessonLinkEntityInitializationForTests(LessonLinkEntity lessonLinkEntity) {
        Long predefinedLessonId = 1L;
        LessonEntity lessonEntity = lessonDAO.getById(predefinedLessonId);
        lessonLinkEntity.setDescription("description");
        lessonLinkEntity.setLink("otherLink");
        lessonLinkEntity.setLesson(lessonEntity);
          return lessonLinkEntity;
    }

    private LessonLinkEntity predefinedLessonLinkInitialization(LessonLinkEntity lessonLinkEntity){
        Long predefinedLessonId = 1L;
        Long predefinedLessonLinkId = 1L;
        LessonEntity lessonEntity = lessonDAO.getById(predefinedLessonId);
        lessonLinkEntity.setDescription("description");
        lessonLinkEntity.setLink("someLink");
        lessonLinkEntity.setLesson(lessonEntity);
        lessonLinkEntity.setId(predefinedLessonLinkId);
        return lessonLinkEntity;
    }

    private boolean checkNotNullArgumentViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        return !(constraintViolations.size() != 1 ||
                !constraintViolations.iterator().next().getMessage().equals("may not be null"));
    }
}
