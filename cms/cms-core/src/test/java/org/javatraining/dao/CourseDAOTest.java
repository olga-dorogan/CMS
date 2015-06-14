package org.javatraining.dao;

import org.javatraining.entity.CourseEntity;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.transaction.Transactional;
import java.io.File;
import java.sql.Date;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
@Transactional
public class CourseDAOTest {

    @EJB
    CourseDAO courseDAO;

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

    private CourseEntity courseEntityInit(CourseEntity courseEntity){
        courseEntity.setName("JavaEE");
        courseEntity.setStartdate(  Date.valueOf("2015-10-10"));
        courseEntity.setEnddate(  Date.valueOf("2016-11-11"));
        courseEntity.setOwner((long) 2324);
        courseEntity.setDescription("Java");
        return courseEntity;
    }

//        @Test(expected = EJBException.class)
//    public void testSaveNull() {
//        CourseEntity courseEntity = null;
//        courseDAO.save(courseEntity);
//    }
//
//    @Test(expected = EJBException.class)
//    public void testSaveNullValues() {
//        CourseEntity courseEntity = new CourseEntity();
//        courseDAO.save(courseEntity);
//    }

    @Test
    public void testSaveReturnCourseEntity() {
        CourseEntity courseEntity = new CourseEntity();
        assertEquals(courseEntityInit(courseEntity),courseDAO.save(courseEntityInit(courseEntity)));
    }

    @Test
    public void testRemoveReturnCourseEntity() {
        CourseEntity courseEntity = new CourseEntity();
         courseDAO.save(courseEntityInit(courseEntity));
         assertEquals(courseDAO.remove(courseEntityInit(courseEntity)), courseEntityInit(courseEntity));
    }

    @Test
    public void testUpdateReturnCourseEntity() {
        CourseEntity courseEntity = new CourseEntity();
        courseDAO.save(courseEntityInit(courseEntity));
        courseEntityInit(courseEntity).setName("JavaEE");
        assertEquals(courseDAO.update(courseEntityInit(courseEntity)), courseEntityInit(courseEntity));
    }

    @Test
    public void testGetReturnCourseEntity() {
        CourseEntity courseEntity = new CourseEntity();
        courseDAO.save(courseEntityInit(courseEntity));
         assertEquals(courseDAO.getById(Long.valueOf(courseEntityInit(courseEntity).getId())), courseEntityInit(courseEntity));
    }

//    @Test
//    public void testGetAllCoursesNotNull() {
//        CourseEntity courseEntity = new CourseEntity();
//        courseDAO.save(courseEntityInit(courseEntity));
//        Assert.assertNotNull(courseDAO.getAllCourses());
//       }
//
//    @Test
//    public void testGetAllCourses() {
//        CourseEntity courseEntity = new CourseEntity();
//        courseDAO.save(courseEntityInit(courseEntity));
//
//       courseDAO.getAllCourses();
//    }

}