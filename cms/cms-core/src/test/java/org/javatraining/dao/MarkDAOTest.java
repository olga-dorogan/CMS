package org.javatraining.dao;

import org.hamcrest.Matcher;
import org.hamcrest.core.IsNull;
import org.javatraining.dao.exception.EntityDoesNotExistException;
import org.javatraining.entity.MarkEntity;
import org.javatraining.entity.PersonEntity;
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
@UsingDataSet(value = "dao-tests/mark/one-mark.json")
public class MarkDAOTest {
    @EJB
    MarkDAO markDAO;

    @EJB
    PersonDAO personDAO;

    private static final String DS_DIR = "dao-tests/";
    private static final String DS_EMPTY = DS_DIR + "empty.json";

    private static final String DS_MARK = DS_DIR + "mark/one-mark.json";
    private static final String DS_MARK_AFTER_UPDATE = DS_DIR + "mark/expected-after-update.json";
    private static final String DS_MARK_AFTER_SAVE = DS_DIR + "mark/expected-after-save.json";
    private static final String DS_MARK_AFTER_REMOVE = DS_DIR + "mark/expected-after-remove.json";

    @EJB
    PracticeLessonDAO practiceLessonDAO;
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
    public void testMarkDAOShouldBeInjected() throws Exception {
        assertThat(markDAO, is(notNullValue()));
    }

    @Test(expected = RuntimeException.class)
    public void testGetByIdForNotExistingIdShouldReturnEntityDoesNotExistException() {
        Long notExistingId = 10L;
      try{
        MarkEntity courseWithNotExistingId = markDAO.getById(notExistingId);
        assertThat(courseWithNotExistingId, is(IsNull.nullValue()));
      }catch (EntityDoesNotExistException e) {
          assertThat(e.getCause(), is((Matcher)instanceOf(ConstraintViolationException.class)));
          if (checkNotNullArgumentViolationException((ConstraintViolationException) e.getCause())) {
              throw e;
          }
      }
      }

    @Test(expected = EJBException.class)
    @ShouldMatchDataSet(value = {DS_EMPTY, DS_MARK}, excludeColumns = {"id"})
    public void testSaveForNotValidMark(){
        markDAO.save(new MarkEntity());
    }

    @Test
    public void testSaveReturnMarkEntity()
    {
        MarkEntity markForSave = markInitializationForTests(new MarkEntity());
        markDAO.save(markForSave);
         }

    @Test
     public void testUpdateReturnMarkEntity() {
        MarkEntity markForUpdate = predefinedMarkInitialization(new MarkEntity());
        markForUpdate.setMark(85L);
        markDAO.update(markForUpdate);
        assertEquals(markDAO.update(markForUpdate), markForUpdate);
    }

    @Test
    @ShouldMatchDataSet(value = {DS_MARK_AFTER_REMOVE})
    public void testRemoveMark() {
        MarkEntity markForRemove = predefinedMarkInitialization(new MarkEntity());
        markDAO.remove(markForRemove);
    }

    @Test
    @ShouldMatchDataSet(value = {DS_EMPTY, DS_MARK}, excludeColumns = {"id"})
    public void testGetAllMarks() {
        assertThat(markDAO.getAllMarks(), is(notNullValue()));
    }

    private boolean checkNotNullArgumentViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        return !(constraintViolations.size() != 1 ||
                !constraintViolations.iterator().next().getMessage().equals("may not be null"));
    }
    private MarkEntity markInitializationForTests(MarkEntity markEntity){
        Long predefinedPersonId = 1L;
         Long predefinedPracticeLessonId = 1L;
        PersonEntity predefinedPerson = personDAO.getById(predefinedPersonId);
        PracticeLessonEntity predefinedLesson = practiceLessonDAO.getById(predefinedPracticeLessonId);
        markEntity.setPracticeLesson(predefinedLesson);
        markEntity.setPersons(predefinedPerson);
        markEntity.setMark(80L);

        return markEntity;
    }

    private MarkEntity predefinedMarkInitialization(MarkEntity markEntity){
        Long predefinedPersonId = 1L;
        Long predefinedMarkId = 1L;
        Long predefinedPracticeLessonId = 1L;
        PersonEntity predefinedPerson = personDAO.getById(predefinedPersonId);
        PracticeLessonEntity predefinedLesson = practiceLessonDAO.getById(predefinedPracticeLessonId);
        markEntity.setPracticeLesson(predefinedLesson);
        markEntity.setPersons(predefinedPerson);
        markEntity.setId(predefinedMarkId);
        markEntity.setMark(80L);

        return markEntity;
    }
}

