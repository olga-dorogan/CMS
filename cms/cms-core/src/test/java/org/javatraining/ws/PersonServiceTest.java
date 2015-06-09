package org.javatraining.ws;

import org.javatraining.model.PersonRoleVO;
import org.javatraining.model.PersonVO;
import org.javatraining.ws.services.PersonService;
import org.junit.Before;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by asudak on 5/29/15.
 */
public class PersonServiceTest {
    private long clientId = 123;
    private PersonService service = new PersonService();
    private PersonVO person;

    @Before
    public void setUp() {
        person = new PersonVO();
        person.setId(clientId);
        person.setName("Vasya");
        person.setLastName("Pupkin");
        person.setEmail("test@gmail.com");

        Set<PersonRoleVO> roles = new HashSet<>();
        PersonRoleVO role = new PersonRoleVO();
//        role.setId(1L);
//        role.setName("student");
        roles.add(role);
        person.setPersonRole(role);
    }

    @org.junit.Test
    public void testGetPerson() {
//        Response r = service.getPerson(clientId);
//        System.out.print(r.getEntity());
    }

    @org.junit.Test
    public void testCreatePerson() throws URISyntaxException{
        String json = "{\"course\":null,\"email\":\"test@gmail.com\",\"id\":123,\"lastName\":\"Pupkin\",\"marks\":null,\"name\":\"Vasya\",\"personRole\":[{\"id\":1,\"name\":\"student\"}],\"secondName\":null}";
        UriInfo mockUriInfo = mock(UriInfo.class);
        when(mockUriInfo.getRequestUri()).thenReturn(new URI("http://www.test.com/ui/person/test"));

        int status = service.createPerson(mockUriInfo, json).getStatus();
        assertEquals(Response.Status.CREATED.getStatusCode(), status);

    }
}