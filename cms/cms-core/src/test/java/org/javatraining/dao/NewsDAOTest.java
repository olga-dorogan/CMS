package org.javatraining.dao;

import org.hamcrest.Matcher;
import org.hamcrest.core.IsNull;
import org.javatraining.dao.exception.EntityDoesNotExistException;
import org.javatraining.entity.CourseEntity;
import org.javatraining.entity.NewsEntity;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.*;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
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
@UsingDataSet(value = "dao-tests/news/one-news.json")
public class NewsDAOTest {

    @EJB
    NewsDAO newsDAO;

    @EJB
    CourseDAO courseDAO;

    private static final String DS_DIR = "dao-tests/";
    private static final String DS_EMPTY = DS_DIR + "empty.json";

    private static final String DS_NEWS = DS_DIR + "news/one-news.json";
    private static final String DS_NEWS_AFTER_UPDATE = DS_DIR + "news/expected-after-update.json";
    private static final String DS_NEWS_AFTER_SAVE = DS_DIR + "news/expected-after-save.json";
    private static final String DS_NEWS_AFTER_REMOVE = DS_DIR + "news/expected-after-remove.json";


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
    public void testPersonDAOShouldBeInjected() throws Exception {
        assertThat(newsDAO, is(notNullValue()));
    }

    @Test(expected = RuntimeException.class)
    public void testGetByIdForNotExistingIdShouldReturnEntityDoesNotExistException() {
        Long notExistingId = (long) 10;
        try {
            NewsEntity courseWithNotExistingId = newsDAO.getById(notExistingId);
            assertThat(courseWithNotExistingId, is(IsNull.nullValue()));
        }catch (EntityDoesNotExistException e) {
            assertThat(e.getCause(), is((Matcher)instanceOf(ConstraintViolationException.class)));
            if (checkNotNullArgumentViolationException((ConstraintViolationException) e.getCause())) {
                throw e;
            }
        }
    }

    @Test
    @ShouldMatchDataSet(value = {DS_EMPTY, DS_NEWS_AFTER_SAVE}, excludeColumns = {"id"})
    public void testSaveNews() {
        NewsEntity newsForSave =newsEntityInitialization(new NewsEntity());
        newsDAO.save(newsForSave);
    }

    @Test
    public void testSaveReturnNewsEntity() {
        NewsEntity newsForSave =newsEntityInitialization(new NewsEntity());
        assertEquals(newsForSave, newsDAO.save(newsForSave));
    }

    @Test
    @ShouldMatchDataSet(value = DS_NEWS_AFTER_REMOVE)
    public void testRemoveNews() {
        NewsEntity newsForRemove= predefinedNewsInitializationForTests(new NewsEntity());
        newsDAO.remove(newsForRemove);
      }

    @Test
    public void testRemoveReturnNewsEntity() {
        NewsEntity newsForRemove= predefinedNewsInitializationForTests(new NewsEntity());
        assertEquals(newsForRemove, newsDAO.remove(newsForRemove));
         }

    @Test
    public void testUpdateReturnNewsEntity() {
         NewsEntity newsForUpdate= predefinedNewsInitializationForTests(new NewsEntity());
         newsForUpdate.setTitle("otherTitle");
        assertEquals(newsForUpdate,newsDAO.update(newsForUpdate));
    }

    @Test
    @ShouldMatchDataSet(value = {DS_EMPTY, DS_NEWS_AFTER_UPDATE}, excludeColumns = {"id"})
    public void testUpdatePositive() {
        NewsEntity newsForUpdate= predefinedNewsInitializationForTests(new NewsEntity());
        newsForUpdate.setTitle("otherTitle");
        newsDAO.update(newsForUpdate);
    }


    @Test
    public void testGetReturnNewsEntity() {
        NewsEntity newsForGet= predefinedNewsInitializationForTests(new NewsEntity());
        assertThat(newsForGet, is(equalTo( newsDAO.getById(newsForGet.getId()))));
      }

    @Test
    @ShouldMatchDataSet(value = {DS_EMPTY, DS_NEWS}, excludeColumns = {"id"})
    public void testGetNewsPositive() {
        NewsEntity newsForGet= predefinedNewsInitializationForTests(new NewsEntity());
        newsDAO.getById(newsForGet.getId());
    }

    private boolean checkNotNullArgumentViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        return !(constraintViolations.size() != 1 ||
                !constraintViolations.iterator().next().getMessage().equals("may not be null"));
    }

    private NewsEntity newsEntityInitialization(NewsEntity newsEntity){
        Long predefinedCourseId = (long) 1;
        CourseEntity courseEntity = courseDAO.getById(predefinedCourseId);
        newsEntity.setCourses(courseEntity);
        newsEntity.setDescription("newsDescription");
        newsEntity.setTitle("Sometitle");
        newsEntity.setDate(Timestamp.valueOf("2015-10-02 18:48:05"));
        return newsEntity;
    }

    public CourseEntity predefinedCourse(){
        Long predefinedCourseId = 1L;
        CourseEntity predefinedCourse = new CourseEntity("courseName","courseDescription",Date.valueOf("2014-01-10"),Date.valueOf("2015-07-31"));
        predefinedCourse.setId(predefinedCourseId);
        return predefinedCourse;
    }

    private NewsEntity predefinedNewsInitializationForTests(NewsEntity newsEntity){
        Long predefinedNewsId = 1L;
        CourseEntity courseEntity = predefinedCourse();
        newsEntity.setId(predefinedNewsId);
        newsEntity.setCourses(courseEntity);
        newsEntity.setDescription("newsDescription");
        newsEntity.setTitle("title");
        newsEntity.setDate(Timestamp.valueOf("2015-10-02 18:48:05"));
        return newsEntity;
    }

}
