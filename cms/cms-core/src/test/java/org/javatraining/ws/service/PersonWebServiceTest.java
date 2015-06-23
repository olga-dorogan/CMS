package org.javatraining.ws.service;

import flexjson.JSONException;
import org.javatraining.auth.Auth;
import org.javatraining.config.Config;
import org.javatraining.dao.PersonDAO;
import org.javatraining.entity.PersonEntity;
import org.javatraining.entity.PersonRole;
import org.javatraining.model.PersonVO;
import org.javatraining.model.conversion.PersonConverter;
import org.javatraining.service.PersonService;
import org.javatraining.service.exception.UnsupportedOperationException;
import org.javatraining.service.impl.PersonServiceImpl;
import org.javatraining.ws.config.ServiceConfig;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.*;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Arquillian.class)
@Cleanup(phase = TestExecutionPhase.BEFORE, strategy = CleanupStrategy.STRICT)
public class PersonWebServiceTest {

    private WebTarget target;

    private static PersonVO teacherPerson;
    private static PersonVO studentPerson;
    private static PersonVO newPerson;
    static {
        teacherPerson = new PersonVO(1L, "teacherName", "teacherLastName", "teacher@gmail.com", PersonRole.TEACHER);
        studentPerson = new PersonVO(2L, "studentName", "studentLastName", "student@gmail.com", PersonRole.STUDENT);
        newPerson = new PersonVO();
        newPerson.setName("newName");
        newPerson.setLastName("newLastName");
        newPerson.setEmail("new@gmail.com");
    }
    private static String TEACHER_TOKEN = "AIzaSyDEVCJp5Hz_fSrHYeS24EcMM3FQV0GF8Do";
    private static String STUDENT_TOKEN = "AIzaSyDEVCJp5Hz_fSrHYeS24EcMM3FQV0GF8Do";


    @Deployment(testable = true)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "WebServicesTest.war")
                .addPackage(PersonEntity.class.getPackage())
                .addPackage(PersonVO.class.getPackage())
                .addPackage(PersonConverter.class.getPackage())
                .addPackage(PersonWebService.class.getPackage())
                .addPackage(PersonDAO.class.getPackage())
                .addPackage(PersonService.class.getPackage())
                .addPackage(PersonServiceImpl.class.getPackage())
                .addPackage(Auth.class.getPackage())
                .addClass(ServiceConfig.class)
                .addClasses(JSONException.class, UnsupportedOperationException.class)
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml");
    }

    @ArquillianResource
    private URL base;

    @Before
    public void setUp() throws MalformedURLException {
        Client client = ClientBuilder.newClient();
        target = client.target(URI.create(new URL(base, "resources/person").toExternalForm()));
        target.register(PersonVO.class);
    }

    @Test
    @RunAsClient
    public void testGetSamePersonByIdAsTeacher() {
        PersonVO predefinedPersonFromService = target.path("{id}")
                .resolveTemplate("id", teacherPerson.getId())
                .request(MediaType.APPLICATION_JSON)
                .header(Config.REQUEST_HEADER_ID, teacherPerson.getId())
                .header(Config.REQUEST_HEADER_TOKEN, TEACHER_TOKEN)
                .get(PersonVO.class);
        assertThat(predefinedPersonFromService, is(equalTo(teacherPerson)));
    }

    @Test
    @RunAsClient
    public void testGetAnotherPersonByIdAsTeacher() {
        PersonVO predefinedPersonFromService = target.path("{id}")
                .resolveTemplate("id", studentPerson.getId())
                .request(MediaType.APPLICATION_JSON)
                .header(Config.REQUEST_HEADER_ID, teacherPerson.getId())
                .header(Config.REQUEST_HEADER_TOKEN, TEACHER_TOKEN)
                .get(PersonVO.class);
        assertThat(predefinedPersonFromService, is(equalTo(studentPerson)));
    }

    @Test
    @RunAsClient
    public void testGetSamePersonByIdAsStudent() {
        PersonVO predefinedPersonFromService = target.path("{id}")
                .resolveTemplate("id", studentPerson.getId())
                .request(MediaType.APPLICATION_JSON)
                .header(Config.REQUEST_HEADER_ID, studentPerson.getId())
                .header(Config.REQUEST_HEADER_TOKEN, STUDENT_TOKEN)
                .get(PersonVO.class);
        assertThat(predefinedPersonFromService, is(equalTo(studentPerson)));
    }

    @Test
    @RunAsClient
    public void testGetAnotherPersonByIdAsStudent() {
        Response response = target.path("{id}")
                .resolveTemplate("id", teacherPerson.getId())
                .request(MediaType.APPLICATION_JSON)
                .header(Config.REQUEST_HEADER_ID, studentPerson.getId())
                .header(Config.REQUEST_HEADER_TOKEN, STUDENT_TOKEN)
                .get();
        assertEquals(Response.Status.FORBIDDEN.getStatusCode(), response.getStatus());
    }

    @Test
    @RunAsClient
    public void testGetPersonByIdAsGuest() {
        Response response = target.path("{id}")
                .resolveTemplate("id", teacherPerson.getId())
                .request(MediaType.APPLICATION_JSON)
                .get();
        assertEquals(Response.Status.FORBIDDEN.getStatusCode(), response.getStatus());
    }

    @Test
    @RunAsClient
    public void testCreatePerson() {
        Response response = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(
                        newPerson,
                        MediaType.APPLICATION_JSON
                ));
        assertNotNull(response.getLocation());
    }
}