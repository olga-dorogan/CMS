package org.javatraining.dao;

import org.javatraining.entity.*;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
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

    private MarkEntity markEntity;
    private LessonEntity lessonEntity;
    private PersonEntity personEntity;
    private CourseEntity courseEntity;

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addPackage("org.javatraining.dao")
                .addPackage("org.javatraining.entity")
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml");
        return war;
    }
    @Before
    public void setUp() {
        courseEntity = new CourseEntity("JavaEE",Long.valueOf(2324),"Java",
                Date.valueOf("2015-10-10"),Date.valueOf("2016-11-11"));
        courseEntity.setId(Long.valueOf(1));
        markEntity = new MarkEntity(Long.valueOf(5));
        lessonEntity = new LessonEntity(Long.valueOf(3234), Long.valueOf(3234),"topic",
                "description", Date.valueOf("2015-10-10"), courseEntity);
        lessonEntity.setId(Long.valueOf(1));
        personEntity = new PersonEntity("Person", "Petro", "Petrov", "Petrovgmail.ru", PersonRole.TEACHER);
        personEntity.setId(Long.valueOf(1));
    }

    @Test
    public void testSaveReturnMarkEntity()
    {
    assertEquals(markDAO.save(markEntity,personEntity,lessonEntity), markEntity);
}

    @Test
    public void testUpdateReturnMarkEntity() {
        markDAO.save(markEntity);
        markEntity.setMark(Long.valueOf(3245));
        assertEquals(markDAO.update(markEntity), markEntity);
    }
}

