package org.javatraining.ws.services;

import javax.enterprise.context.Dependent;
import flexjson.JSONException;
import org.javatraining.model.PersonRoleVO;
import org.javatraining.model.PersonVO;

import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by asudak on 5/29/15.
 */
@Stateless
@Path("person")
@Dependent
public class PersonService<T> extends AbstractService<PersonVO> {
    public PersonService() {
        super(PersonVO.class);
    }

    @GET
    @Produces("application/json")
    @Path("{client_id}")
    public Response getPerson(@PathParam("client_id") long clientId) {
        PersonVO person = new PersonVO();
        person.setId(clientId);
        //TODO get person from DB
        Response r;
        if (person == null)
            r = Response.noContent().build();
        else
            r = Response.ok(serialize(person)).build();
        return r;
    }

    @PUT
    @Consumes("application/json")
    public Response createPerson(@Context UriInfo uriInfo, @QueryParam("person_json") String personJson) {
        Response r;
        try {
            PersonVO person = deserialize(personJson);
            //TODO save entity here
            String personUri = uriInfo.getRequestUri().toString() + "/" + person.getId();
            r = Response.created(new URI(personUri)).build();
        } catch (JSONException e) {
            System.out.println(e);
            r = Response.status(Response.Status.NOT_ACCEPTABLE).build();
        } catch (URISyntaxException e) {
            //this shouldn't happen
            r = Response.serverError().build();
        }

        return r;
    }

    @DELETE
    @Path("{client_id}")
    public Response deletePerson(@PathParam("client_id") long clientId) {
        Response r;
        PersonVO person = new PersonVO();
        person.setId(clientId);
        try {
            //TODO delete entity here
            r = Response.ok().build();
        } catch (Exception e) {
            r = Response.noContent().build();
        }
        return r;
    }
}
