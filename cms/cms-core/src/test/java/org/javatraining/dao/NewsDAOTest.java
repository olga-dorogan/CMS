package org.javatraining.dao;

import org.javatraining.entity.CourseEntity;
import org.javatraining.entity.NewsEntity;
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
public class NewsDAOTest {

    @EJB
    NewsDAO newsDAO;

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addPackage("org.javatraining.dao")
                .addPackage("org.javatraining.entity")
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml");
        return war;
    }


    private NewsEntity newsEntityInit(NewsEntity newsEntity,CourseEntity courseEntity){
        courseEntity.setName("JavaEE");
        courseEntity.setStartdate(  Date.valueOf("2015-10-10"));
        courseEntity.setEnddate(  Date.valueOf("2016-11-11"));
        courseEntity.setOwner((long) 2324);
        courseEntity.setDescription("Java");
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
        assertEquals(newsEntityInit(newsEntity,courseEntity),newsDAO.save(newsEntityInit(newsEntity,courseEntity)));
    }
}
