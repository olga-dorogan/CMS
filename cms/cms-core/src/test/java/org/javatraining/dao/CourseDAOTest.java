package org.javatraining.dao;

import org.javatraining.entity.CourseEntity;
import org.javatraining.entity.NewsEntity;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.sql.Date;
import java.sql.Timestamp;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class CourseDAOTest {
    @EJB
    NewsDAO newsDAO;

    @EJB
    CourseDAO courseDAO;
    @Deployment
    public static WebArchive createDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addPackage("org.javatraining.dao")
                .addPackage("org.javatraining.entity")
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        return war;
    }


    private NewsEntity newsEntityInit(NewsEntity newsEntity,CourseEntity courseEntity){
        courseEntity.setName("JavaEE");
        courseEntity.setStartdate(Date.valueOf("2015-10-10"));
        courseEntity.setEnddate(  Date.valueOf("2016-11-11"));
        courseEntity.setDescription("Java");
        courseDAO.save(courseEntity);
        newsEntity.setCourses(courseEntity);
        newsEntity.setDescription("description");
        newsEntity.setTitle("title");
        newsEntity.setDate(Timestamp.valueOf("2015-10-02 18:48:05"));
        return newsEntity;
    }

    @Test
    public void testSaveReturnNewsEntity() {
        CourseEntity courseEntity = new CourseEntity();
        NewsEntity newsEntity =  new NewsEntity();
        assertEquals(newsEntityInit(newsEntity, courseEntity), newsDAO.save(newsEntityInit(newsEntity, courseEntity)));
    }

    @Test
    public void testUpdateReturnNewsEntity() {
        CourseEntity courseEntity = new CourseEntity();
        NewsEntity newsEntity = new NewsEntity();
        newsEntity =newsEntityInit(newsEntity, courseEntity);
        newsDAO.save(newsEntity);
        newsEntity.setTitle("other title");
        assertEquals(newsEntity, newsDAO.update(newsEntity));
    }
    @Test
    public void testRemoveReturnNewsEntity() {
        CourseEntity courseEntity = new CourseEntity();
        NewsEntity newsEntity = new NewsEntity();
        newsEntity =newsEntityInit(newsEntity,courseEntity);
        newsDAO.save(newsEntity);
        assertEquals(newsEntity, newsDAO.remove(newsEntity));
    }

    @Test
    public void testGetReturnNewsEntity() {
        CourseEntity courseEntity = new CourseEntity();
        NewsEntity newsEntity = new NewsEntity();
        newsEntity =newsEntityInit(newsEntity,courseEntity);
        newsDAO.save(newsEntity);
        assertEquals(newsEntity,newsDAO.getById(newsEntity.getId()));

    }
}