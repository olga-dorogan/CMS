package org.javatraining.ws;

import flexjson.JSONException;
import org.javatraining.stubs.Person;

import javax.ejb.Stateless;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by asudak on 5/29/15.
 */
@Stateless
@Path("person")
public class PersonService<T> extends AbstractService<Person> {
    public PersonService() {
        super(Person.class);
    }

    @GET
    @Produces("application/json")
    @Path("{client_id}")
    public Response getPerson(
            //FIXME Proper regex to validate clientId
            @PathParam("client_id") @Pattern(regexp = ".*") String clientId) {
        Person p = new Person(clientId);
        //TODO get person from DB
        Response r;
        if (p == null)
            r = Response.noContent().build();
        else
            r = Response.ok(serialize(p)).build();
        return r;
    }

    @PUT
    @Produces("application/json")
    @Path("{client_id}")
    public Response createPerson(
            @Context UriInfo uriInfo,
            //FIXME Proper regex to validate clientId
            @PathParam("client_id") @Pattern(regexp = ".*") String clientId,
            @QueryParam("person_json") String personJson
    ) {
        Person person = null;
        Response r;
        try {
            person = deserialize(personJson);
            person.setClientId(clientId);
            //TODO save entity here
            r = Response.created(uriInfo.getRequestUri()).build();
        } catch (JSONException e) {
            System.out.println(e);
            r = Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        System.out.println(person.getFirstname());

        return r;
    }
}
