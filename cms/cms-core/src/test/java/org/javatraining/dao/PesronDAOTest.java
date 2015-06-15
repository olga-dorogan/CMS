package org.javatraining.dao;

import org.javatraining.entity.PersonEntity;
import org.javatraining.entity.PersonRole;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by vika on 12.06.15.
 */
@RunWith(Arquillian.class)
public class PesronDAOTest {
    @EJB
    PersonDAO personDAO;

    private PersonEntity personEntityStudent;
    private PersonEntity personEntityTeacher;

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
        personEntityStudent = new PersonEntity("Person", "Ivan", "ivanov", "Ivanovgmail.ru", PersonRole.STUDENT);
        personEntityTeacher = new PersonEntity("Person", "Petro", "Petrov", "Petrovgmail.ru", PersonRole.TEACHER);
    }

    @Test
    public void testGetPersonByEmail() {
       personDAO.save(personEntityStudent);
       assertEquals(personDAO.getByEmail("Ivanovgmail.ru"), personEntityStudent);
    }
    @Test
    public void testGetPersonByPersonRole(){
      personDAO.save(personEntityStudent);
      personDAO.save(personEntityTeacher);
        assertEquals(personDAO.getByPersonRole(PersonRole.TEACHER), Arrays.asList(personEntityTeacher));
    }
    @Test
    public void testSaveReturnPersonEntity() {
          assertEquals(personDAO.save(personEntityStudent), personEntityStudent);
    }
    @Test
    public void testUpdateReturnCourseEntity() {
        personDAO.save(personEntityStudent);
        personEntityStudent.setName("Student");
        assertEquals(personDAO.update(personEntityStudent), personEntityStudent);
    }


}
