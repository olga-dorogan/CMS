package org.javatraining.dao;

import org.javatraining.entity.*;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.sql.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by vika on 12.06.15.
 */
@RunWith(Arquillian.class)
public class MarkDAOTest {
    @EJB
    MarkDAO markDAO;

    @EJB
    CourseDAO courseDAO;

    @EJB
    LessonDAO lessonDAO;

    @EJB
    PersonDAO personDAO;

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addPackage("org.javatraining.dao")
                .addPackage("org.javatraining.entity")
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
         return war;
    }

    private MarkEntity markEntityInit(CourseEntity courseEntity,PersonEntity personEntity,LessonEntity lessonEntity,MarkEntity markEntity){
        courseEntity.setName("JavaEE");
        courseEntity.setStartdate(Date.valueOf("2015-10-10"));
        courseEntity.setEnddate(Date.valueOf("2016-11-11"));
        courseEntity.setDescription("Java");
        courseDAO.save(courseEntity);
        lessonEntity.setCourses(courseEntity);
        lessonEntity.setTopic("topic");
        lessonEntity.setType((long) 2);
        lessonEntity.setOrderNum(((long) 5));
        lessonEntity.setDescription("description");
        lessonEntity.setCreateDate(Date.valueOf("2015-10-10"));
        lessonDAO.save(lessonEntity);
        markEntity.setMark((long) 7);
        markEntity.setLessons(lessonEntity);
        personEntity.setName("Petro");
        personEntity.setEmail("Petrovgmail.ru");
        personEntity.setLastName("Last Name");
        personEntity.setSecondName("Second name");
        personEntity.setPersonRole(PersonRole.TEACHER);
        personDAO.save(personEntity);
        markEntity.setPersons(personEntity);

        return markEntity;
    }
    @Test
    public void testSaveReturnMarkEntity()
    {
        MarkEntity markEntity = new MarkEntity();
        PersonEntity personEntity = new PersonEntity();
        LessonEntity lessonEntity = new LessonEntity();
        CourseEntity courseEntity = new CourseEntity();
        assertEquals(markDAO.save(markEntityInit(courseEntity,personEntity,lessonEntity,markEntity)), markEntityInit(courseEntity,personEntity,lessonEntity,markEntity));
    }

    @Test
    public void testUpdateReturnMarkEntity() {
        PersonEntity personEntity = new PersonEntity();
        LessonEntity lessonEntity = new LessonEntity();
        CourseEntity courseEntity = new CourseEntity();
        MarkEntity markEntity = markEntityInit(courseEntity,personEntity,lessonEntity,new MarkEntity());
        markDAO.save(markEntity);
        markEntity.setMark(Long.valueOf(3245));
        assertEquals(markDAO.update(markEntity), markEntity);
    }
}

