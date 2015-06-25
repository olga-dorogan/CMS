package org.javatraining.dao;

import org.javatraining.entity.CourseEntity;
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
        lessonEntity.setId((long) 1);
        lessonLinkEntity.setDescription("Description");
        lessonLinkEntity.setLink("link");
        lessonLinkEntity.setLesson(lessonEntity);
        return lessonLinkEntity;
    }

    private LessonLinkEntity predefinedLessonLinkInitialization(LessonLinkEntity lessonLinkEntity){
        Long predefinedLessonId = (long) 1;
        Long predefinedLessonLinkId = (long) 1;
         LessonEntity lessonEntity = lessonDAO.getById(predefinedLessonId);
        lessonLinkEntity.setDescription("description");
        lessonLinkEntity.setLink("someLink");
        lessonLinkEntity.setLesson(lessonEntity);
        lessonLinkEntity.setId(predefinedLessonLinkId);
        return lessonLinkEntity;
    }

    @Test
    public void testLessonLinkDAOShouldBeInjected() throws Exception {
        assertThat(lessonsLinksDAO, is(notNullValue()));
    }


    @Test
    public void testSaveReturnLessonEntity() {
        LessonLinkEntity lessonLinkEntity = predefinedLessonLinkInitialization(new LessonLinkEntity());
        lessonsLinksDAO.save(lessonLinkEntity);
       assertEquals(lessonsLinksDAO.save(lessonLinkEntity), lessonLinkEntity);
    }

    @Test
    public void testUpdateReturnLessonEntity() {
       LessonLinkEntity lessonLinkEntity = predefinedLessonLinkInitialization(new LessonLinkEntity());
        lessonLinkEntity.setDescription("otherDescription");
        assertEquals(lessonLinkEntity, lessonsLinksDAO.update(lessonLinkEntity));
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
}
