package org.javatraining.dao;

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
import java.sql.Timestamp;

import static org.hamcrest.CoreMatchers.equalTo;
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


    private NewsEntity newsEntityInitialization(NewsEntity newsEntity){
        Long predefinedCourseId = (long) 1;
        CourseEntity courseEntity = courseDAO.getById(predefinedCourseId);
        newsEntity.setCourses(courseEntity);
        newsEntity.setDescription("newsDescription");
        newsEntity.setTitle("Sometitle");
        newsEntity.setDate(Timestamp.valueOf("2015-10-02 18:48:05"));
        return newsEntity;
    }

    private NewsEntity predefinedNewsInitialization(NewsEntity newsEntity){
        Long predefinedCourseId = (long) 1;
        Long predefinedNewsId = (long) 1;
       CourseEntity courseEntity = courseDAO.getById(predefinedCourseId);
        newsEntity.setId(predefinedNewsId);
        newsEntity.setCourses(courseEntity);
        newsEntity.setDescription("newsDescription");
        newsEntity.setTitle("title");
        newsEntity.setDate(Timestamp.valueOf("2015-10-02 18:48:05"));
        return newsEntity;
    }

    @Test
    public void testPersonDAOShouldBeInjected() throws Exception {
        assertThat(newsDAO, is(notNullValue()));
    }

    @Test
    @ShouldMatchDataSet(value = {DS_EMPTY, DS_NEWS_AFTER_SAVE}, excludeColumns = {"id"})
    public void testSavePositive() {
        NewsEntity newsForSave =newsEntityInitialization(new NewsEntity());
        newsDAO.save(newsForSave);
    }

    @Test
    public void testSaveReturnNewsEntity() {
        NewsEntity newsForSave =predefinedNewsInitialization(new NewsEntity());
        assertEquals(newsForSave, newsDAO.save(newsForSave));
    }

    @Test
    @ShouldMatchDataSet(value = DS_NEWS_AFTER_REMOVE)
    public void testRemovePositive() {
        NewsEntity newsForRemove=predefinedNewsInitialization(new NewsEntity());
        newsDAO.remove(newsForRemove);
      }

    @Test
    public void testRemoveReturnNewsEntity() {
        NewsEntity newsForRemove=predefinedNewsInitialization(new NewsEntity());
        assertEquals(newsForRemove, newsDAO.remove(newsForRemove));
         }

    @Test
    public void testUpdateReturnNewsEntity() {
         NewsEntity newsForUpdate=predefinedNewsInitialization(new NewsEntity());
         newsForUpdate.setTitle("otherTitle");
        assertEquals(newsForUpdate,newsDAO.update(newsForUpdate));
    }

    @Test
    @ShouldMatchDataSet(value = {DS_EMPTY, DS_NEWS_AFTER_UPDATE}, excludeColumns = {"id"})
    public void testUpdatePositive() {
        NewsEntity newsForUpdate=predefinedNewsInitialization(new NewsEntity());
        newsForUpdate.setTitle("otherTitle");
        newsDAO.update(newsForUpdate);
    }


    @Test
    public void testGetReturnNewsEntity() {
        NewsEntity newsForGet=predefinedNewsInitialization(new NewsEntity());
        assertThat(newsForGet, is(equalTo( newsDAO.getById(newsForGet.getId()))));
      }

    @Test
    @ShouldMatchDataSet(value = {DS_EMPTY, DS_NEWS}, excludeColumns = {"id"})
    public void testGetNewsPositive() {
        NewsEntity newsForGet=predefinedNewsInitialization(new NewsEntity());
        newsDAO.getById(newsForGet.getId());
    }
}
