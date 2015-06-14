package org.javatraining.dao;

import org.javatraining.entity.ForumMessagesEntity;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.sql.Timestamp;

/**
 * Created by vika on 12.06.15.
 */
@RunWith(Arquillian.class)
public class ForumMessageDAOTest {

    @EJB
    ForumMessageDAO forumMessageDAO;
    private ForumMessagesEntity forumMessagesEntity;

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
       forumMessagesEntity = new ForumMessagesEntity(Long.valueOf(21), "title",
               "description", Timestamp.valueOf("2011-10-02-18.48.05.123"));
    }
}


