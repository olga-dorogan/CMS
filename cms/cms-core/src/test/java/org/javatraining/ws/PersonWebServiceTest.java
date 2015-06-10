package org.javatraining.ws;

import org.javatraining.ws.services.PersonWebService;
import org.junit.Test;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by asudak on 5/29/15.
 */
public class PersonWebServiceTest {
    private PersonWebService service = new PersonWebService();

    @Test
    public void testGetPersonThatNotExist() {
        Response r = service.getPerson(-1, -1);
        assertEquals(r.getStatus(), Response.Status.NO_CONTENT.getStatusCode());
    }

    @Test
    public void testGetPersonByTheSamePerson() {
        Response r = service.getPerson(1, 1);
        assertEquals(r.getStatus(), Response.Status.NO_CONTENT.getStatusCode()); //FIXME change to 200
    }

    @Test
    public void testGetPersonByTheDifferentPerson() {
        Response r = service.getPerson(1, 2);
        assertEquals(r.getStatus(), Response.Status.FORBIDDEN.getStatusCode());
    }

    @Test
    public void testCreatePerson() throws URISyntaxException{
        String json = "{\"email\":\"test@gmail.com\",\"id\":123,\"lastName\":\"Pupkin\",\"name\":\"Vasya\",\"personRole\":{\"role\":\"TEACHER\"},\"secondName\":null}";
        UriInfo mockUriInfo = mock(UriInfo.class);
        when(mockUriInfo.getRequestUri()).thenReturn(new URI("http://www.test.com/resources/person/"));

        int status = service.createPerson(mockUriInfo, json).getStatus();
        assertEquals(Response.Status.CREATED.getStatusCode(), status);

    }
}