package org.javatraining.ws;

import org.javatraining.stubs.Person;
import org.junit.Before;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by asudak on 5/29/15.
 */
public class PersonServiceTest {
    private String clientId = "test";
    private PersonService service = new PersonService();
    private Person person;

    @Before
    public void setUp() {
        person = new Person(clientId);

    }

    @org.junit.Test
    public void testGetPerson() {
        Response r = service.getPerson(clientId);
        System.out.print(r.getEntity());
    }

    @org.junit.Test
    public void testCreatePerson() throws URISyntaxException{
        String json = "{\"firstname\":null,\"lastname\":null,\"role\":null,\"secondname\":null}";
        UriInfo mockUriInfo = mock(UriInfo.class);
        when(mockUriInfo.getRequestUri()).thenReturn(new URI("http://www.test.com/ui/person/test"));

        int status = service.createPerson(mockUriInfo, clientId, json).getStatus();
        assertEquals(Response.Status.CREATED.getStatusCode(), status);

    }
}