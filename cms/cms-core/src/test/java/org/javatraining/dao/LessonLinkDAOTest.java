package org.javatraining.dao;

import org.javatraining.entity.CourseEntity;
import org.javatraining.entity.LessonEntity;
import org.javatraining.entity.LessonLinkEntity;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.sql.Date;

/**
 * Created by vika on 12.06.15.
 */
@RunWith(Arquillian.class)
public class LessonLinkDAOTest {
    @EJB
    LessonsLinksDAO lessonsLinksDAO;

    private LessonLinkEntity lessonLinkEntity;
    private LessonEntity lessonEntity;
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
        lessonEntity = new LessonEntity(Long.valueOf(3234), Long.valueOf(3234),"topic",
                "description",Date.valueOf("2015-10-10"), courseEntity);
        lessonLinkEntity = new LessonLinkEntity("description", "link", lessonEntity);

    }
}
