package org.javatraining.dao;

import org.hamcrest.Matcher;
import org.hamcrest.core.IsNull;
import org.javatraining.dao.exception.EntityDoesNotExistException;
import org.javatraining.entity.ForumMessagesEntity;
import org.javatraining.entity.LessonEntity;
import org.javatraining.entity.PersonEntity;
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
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.Timestamp;
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

    @Test
    public void testForumMessageDAOShouldBeInjected() throws Exception {
        assertThat(forumMessageDAO, is(notNullValue()));
    }

    @Test(expected = RuntimeException.class)
    public void testGetByIdForNotExistingIdShouldReturnEntityDoesNotExistException() {
        Long notExistingId = 10L;
       try{
        ForumMessagesEntity courseWithNotExistingId = forumMessageDAO.getById(notExistingId);
        assertThat(courseWithNotExistingId, is(IsNull.nullValue()));
    }catch (EntityDoesNotExistException e) {
        assertThat(e.getCause(), is((Matcher)instanceOf(ConstraintViolationException.class)));
        if (checkNotNullArgumentViolationException((ConstraintViolationException) e.getCause())) {
            throw e;
        }
    }
    }

    @Test
    public void testUpdateReturnCourseEntity() {
       ForumMessagesEntity forumMessagesForUpdate = predefinedForumMessagesInitialization(new ForumMessagesEntity());
        forumMessagesForUpdate.setTitle("Other title");
      assertEquals(forumMessagesForUpdate, forumMessageDAO.update(forumMessagesForUpdate));
    }


    private ForumMessagesEntity predefinedForumMessagesInitialization(ForumMessagesEntity predefinedForumMessages){

        LessonEntity predefinedLesson = lessonDAO.getById(1L);
        PersonEntity predefinedPerson = personDAO.getById(1L);
        predefinedForumMessages.setLessons(predefinedLesson);
        predefinedForumMessages.setPersons(predefinedPerson);
        predefinedForumMessages.setId(1L);
        predefinedForumMessages.setDescription("description");
        predefinedForumMessages.setParentId(785L);
        predefinedForumMessages.setTitle("title");
        predefinedForumMessages.setDate(Timestamp.valueOf("2011-10-02 12:05:12"));

        return predefinedForumMessages;
    }
    private boolean checkNotNullArgumentViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        return !(constraintViolations.size() != 1 ||
                !constraintViolations.iterator().next().getMessage().equals("may not be null"));
    }
}


