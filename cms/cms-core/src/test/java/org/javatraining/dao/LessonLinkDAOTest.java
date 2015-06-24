package org.javatraining.dao;

import org.javatraining.entity.CourseEntity;
import org.javatraining.entity.LessonEntity;
import org.javatraining.entity.LessonLinkEntity;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
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
public class LessonLinkDAOTest {
    @EJB
    LessonsLinksDAO lessonsLinksDAO;

    @EJB
    LessonDAO lessonDAO;

    @EJB
    CourseDAOTest courseDAO;

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addPackage("org.javatraining.dao")
                .addPackage("org.javatraining.entity")
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml");
        return war;
    }
    public LessonLinkEntity lessonLinkEntityInit(LessonEntity lessonEntity, LessonLinkEntity lessonLinkEntity, CourseEntity courseEntity) {
        courseEntity.setName("JavaEE");
        courseEntity.setStartdate(Date.valueOf("2015-10-10"));
        courseEntity.setEnddate(Date.valueOf("2016-11-11"));
        courseEntity.setDescription("Java");
        lessonEntity.setCourse(courseEntity);
        lessonEntity.setType((long) 3234);
        lessonEntity.setCreateDate(Date.valueOf("2015-10-10"));
        lessonEntity.setOrderNum((long) 67);
        lessonEntity.setTopic("topic");
        lessonEntity.setDescription("Description");
        lessonDAO.save(lessonEntity);
        lessonLinkEntity.setDescription("Description");
        lessonLinkEntity.setLink("link");
        lessonLinkEntity.setLesson(lessonEntity);
        return lessonLinkEntity;
    }


    @Test
    public void testSaveReturnLessonEntity() {
        LessonLinkEntity lessonLinkEntity = lessonLinkEntityInit(new LessonEntity(), new LessonLinkEntity(), new CourseEntity());
        assertEquals(lessonsLinksDAO.save(lessonLinkEntity), lessonLinkEntity);
    }

    @Test
    public void testUpdateReturnLessonEntity() {
        LessonLinkEntity lessonLinkEntity = lessonLinkEntityInit(new LessonEntity(), new LessonLinkEntity(), new CourseEntity());
        assertEquals(lessonsLinksDAO.update(lessonLinkEntity), lessonLinkEntity);
    }

    @Test
    public void testRemoveReturnLessonEntity() {
        LessonLinkEntity lessonLinkEntity = lessonLinkEntityInit(new LessonEntity(), new LessonLinkEntity(), new CourseEntity());
        assertEquals(lessonsLinksDAO.remove(lessonLinkEntity), lessonLinkEntity);
    }
}
