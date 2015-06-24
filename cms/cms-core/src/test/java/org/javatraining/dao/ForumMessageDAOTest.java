package org.javatraining.dao;

import org.javatraining.entity.*;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.sql.Date;
import java.sql.Timestamp;

import static org.junit.Assert.assertEquals;

/**
 * Created by vika on 12.06.15.
 */
@RunWith(Arquillian.class)
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
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml");
        return war;
    }
    private ForumMessagesEntity forumMessagesEntityInit(ForumMessagesEntity forumMessagesEntity,PersonEntity personEntity,LessonEntity lessonEntity,CourseEntity courseEntity){
        personEntity.setName("Petro");
        personEntity.setEmail("Petrovg@mail.ru");
        personEntity.setLastName("Last Name");
        personEntity.setSecondName("Second name");
        personEntity.setPersonRole(PersonRole.TEACHER);
        personDAO.save(personEntity);
        forumMessagesEntity.setPersons(personEntity);

        courseEntity.setName("JavaEE");
        courseEntity.setStartdate(Date.valueOf("2015-10-10"));
        courseEntity.setEnddate(Date.valueOf("2016-11-11"));
        courseEntity.setDescription("Java");
        courseDAO.save(courseEntity);
        lessonEntity.setCourse(courseEntity);
        lessonEntity.setType((long) 3234);
        lessonEntity.setCreateDate(Date.valueOf("2015-10-10"));
        lessonEntity.setOrderNum((long) 67);
        lessonEntity.setTopic("topic");
        lessonEntity.setDescription("Description");
        lessonDAO.save(lessonEntity);
        forumMessagesEntity.setLessons(lessonEntity);
        forumMessagesEntity.setDescription("description");
        forumMessagesEntity.setParentId((long) 785);
        forumMessagesEntity.setTitle("title");
        forumMessagesEntity.setDate(Timestamp.valueOf("2011-10-02 12:05:12"));
        forumMessagesEntity.setLessons(lessonEntity);
        forumMessagesEntity.setPersons(personEntity);

        return forumMessagesEntity;
    }

    @Test
    public void testSaveReturnCourseEntity() {
        LessonEntity lessonEntity = new LessonEntity();
        PersonEntity personEntity = new PersonEntity();
        CourseEntity courseEntity = new CourseEntity();
        ForumMessagesEntity forumMessagesEntity = forumMessagesEntityInit(new ForumMessagesEntity(),personEntity,lessonEntity,courseEntity);
        assertEquals(forumMessagesEntity,forumMessageDAO.save(forumMessagesEntity));
    }
}


