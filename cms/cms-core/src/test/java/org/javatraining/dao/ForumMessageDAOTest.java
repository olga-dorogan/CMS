package org.javatraining.dao;

import org.javatraining.entity.ForumMessagesEntity;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Before;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.io.File;
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
        File[] files = Maven.resolver().loadPomFromFile("cms-core/pom.xml")
                .importTestDependencies().resolve().withTransitivity().asFile();
        WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addPackage("org.javatraining.dao")
                .addPackage("org.javatraining.entity")
                .addAsLibraries(files)
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        System.out.println(war.toString(true));
        return war;
    }
    @Before
    public void setUp() {
       forumMessagesEntity = new ForumMessagesEntity(Long.valueOf(21), "title",
               "description", Timestamp.valueOf("2011-10-02-18.48.05.123"));
    }
}


