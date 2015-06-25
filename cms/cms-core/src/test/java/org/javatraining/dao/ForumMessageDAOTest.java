package org.javatraining.dao;

import org.javatraining.entity.*;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.CleanupStrategy;
import org.jboss.arquillian.persistence.TestExecutionPhase;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.sql.Date;
import java.sql.Timestamp;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;

/**
 * Created by vika on 12.06.15.
 */
@RunWith(Arquillian.class)
@Cleanup(phase = TestExecutionPhase.BEFORE, strategy = CleanupStrategy.STRICT)
@UsingDataSet(value = "dao-tests/forumMessage/one-forumMessage.json")
public class ForumMessageDAOTest {

    @EJB
    ForumMessageDAO forumMessageDAO;

    @EJB
    PersonDAO personDAO;

    @EJB
    LessonDAO lessonDAO;

    @EJB
    CourseDAO courseDAO;

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addPackage("org.javatraining.dao")
                .addPackage("org.javatraining.entity")
                .addPackage("org.javatraining.dao.exception")
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml");
        return war;
    }
    private ForumMessagesEntity forumMessagesInitialization(ForumMessagesEntity forumMessagesEntity,PersonEntity personEntity,LessonEntity lessonEntity,CourseEntity courseEntity){
        personEntity.setName("Petro");
        personEntity.setEmail("Petrovg@mail.ru");
        personEntity.setLastName("Last Name");
        personEntity.setSecondName("Second name");
        personEntity.setPersonRole(PersonRole.TEACHER);
        forumMessagesEntity.setPersons(personEntity);

        courseEntity.setName("JavaEE");
        courseEntity.setStartdate(Date.valueOf("2015-10-10"));
        courseEntity.setEnddate(Date.valueOf("2016-11-11"));
        courseEntity.setDescription("Java");
        lessonEntity.setCourses(courseEntity);
        lessonEntity.setType((long) 3234);
        lessonEntity.setCreateDate(Date.valueOf("2015-10-10"));
        lessonEntity.setOrderNum((long) 67);
        lessonEntity.setTopic("topic");
        lessonEntity.setDescription("Description");

        forumMessagesEntity.setLessons(lessonEntity);
        forumMessagesEntity.setDescription("description");
        forumMessagesEntity.setParentId((long) 785);
        forumMessagesEntity.setTitle("title");
        forumMessagesEntity.setDate(Timestamp.valueOf("2011-10-02 12:05:12"));
        forumMessagesEntity.setLessons(lessonEntity);
        forumMessagesEntity.setPersons(personEntity);

        return forumMessagesEntity;
    }

    private ForumMessagesEntity predefinedForumMessagesInitialization(ForumMessagesEntity predefinedForumMessages){
        Long predefinedLessonId = (long) 1;
        Long predefinedPersonId = (long) 1;
        LessonEntity predefinedLesson = lessonDAO.getById(predefinedLessonId);
        PersonEntity predefinedPerson = personDAO.getById(predefinedPersonId);
        predefinedForumMessages.setLessons(predefinedLesson);
        predefinedForumMessages.setPersons(predefinedPerson);


        predefinedForumMessages.setId((long)1);
        predefinedForumMessages.setDescription("description");
        predefinedForumMessages.setParentId((long) 785);
        predefinedForumMessages.setTitle("title");
        predefinedForumMessages.setDate(Timestamp.valueOf("2011-10-02 12:05:12"));

        return predefinedForumMessages;
    }
    @Test
    public void testForumMessageDAOShouldBeInjected() throws Exception {
        assertThat(forumMessageDAO, is(notNullValue()));
    }


    @Test
    public void testSaveReturnCourseEntity() {

  }

    @Test
    public void testUpdateReturnCourseEntity() {
       ForumMessagesEntity forumMessagesForUpdate = predefinedForumMessagesInitialization(new ForumMessagesEntity());
        forumMessagesForUpdate.setTitle("Other title");
      assertEquals(forumMessagesForUpdate, forumMessageDAO.update(forumMessagesForUpdate));
    }
}


