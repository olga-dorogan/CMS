package org.javatraining.dao;

import org.javatraining.entity.*;
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

    private MarkEntity markInitialization(CourseEntity courseEntity,PersonEntity personEntity,LessonEntity lessonEntity,MarkEntity markEntity){
        courseEntity.setName("JavaEE");
        courseEntity.setStartdate(Date.valueOf("2015-10-10"));
        courseEntity.setEnddate(Date.valueOf("2016-11-11"));
        courseEntity.setDescription("Java");

        lessonEntity.setCourses(courseEntity);
        lessonEntity.setTopic("topic");
        lessonEntity.setType((long) 2);
        lessonEntity.setOrderNum(((long) 5));
        lessonEntity.setId((long) 2);
        lessonEntity.setDescription("description");
        lessonEntity.setCreateDate(Date.valueOf("2015-10-10"));

        PracticeLessonEntity practiceLessonEntity = new PracticeLessonEntity();
        practiceLessonEntity.setTask("task");
        practiceLessonEntity.setLesson(lessonEntity);

        personEntity.setName("Petro");
        personEntity.setEmail("Petrovgmail.ru");
        personEntity.setLastName("Last Name");
        personEntity.setSecondName("Second name");
        personEntity.setPersonRole(PersonRole.TEACHER);
        markEntity.setPersons(personEntity);
        markEntity.setMark((long) 7);
        markEntity.setPracticeLesson(practiceLessonEntity);

        return markEntity;
    }

    private MarkEntity predefinedMarkInitialization(MarkEntity markEntity){
        Long predefinedPersonId = (long) 1;
        Long predefinedMarkId = (long) 1;
        Long predefinedPracticeLessonId = (long) 1;
        PersonEntity predefinedPerson = personDAO.getById(predefinedPersonId);
       PracticeLessonEntity predefinedLesson = practiceLessonDAO.getById(predefinedPracticeLessonId);
        markEntity.setPracticeLesson(predefinedLesson);
        markEntity.setPersons(predefinedPerson);
        markEntity.setId(predefinedMarkId);
        markEntity.setMark((long)80);

        return markEntity;
    }

    @Test
    public void testMarkDAOShouldBeInjected() throws Exception {
        assertThat(markDAO, is(notNullValue()));
    }


    @Test
    public void testSaveReturnMarkEntity()
    {
        MarkEntity markForSave = predefinedMarkInitialization(new MarkEntity());
        markDAO.save(markForSave);
         }

    @Test
     public void testUpdateReturnMarkEntity() {
        MarkEntity markForUpdate = predefinedMarkInitialization(new MarkEntity());
        markForUpdate.setMark((long) 85);
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
}

