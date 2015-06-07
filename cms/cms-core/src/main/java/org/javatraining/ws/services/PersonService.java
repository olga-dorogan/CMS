package org.javatraining.ws.services;

import flexjson.JSONException;
import org.javatraining.auth.Auth;
import org.javatraining.config.AuthRole;
import org.javatraining.config.Config;
import org.javatraining.model.PersonRoleVO;
import org.javatraining.model.PersonVO;

import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by asudak on 5/29/15.
 */
@Stateless
@Path("person")
public class PersonService<T> extends AbstractService<PersonVO> {
    public PersonService() {
        super(PersonVO.class);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Auth(roles = {AuthRole.TEACHER})
    public Response getPersonList() {
        List<PersonVO> persons = new ArrayList<>();
        //TODO get list of persons here
        return Response.ok(serialize(persons)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{person_id}")
    @Auth(roles = {AuthRole.TEACHER, AuthRole.STUDENT})
    public Response getPerson(@HeaderParam(Config.REQUEST_HEADER_ID) long userId, @PathParam("person_id") long personId) {
        Response.ResponseBuilder r = null;
        PersonVO client = new PersonVO();
        //TODO get client

        if (client.getPersonRole() != new PersonRoleVO()) //FIXME if person role not equals teacher
            if (client.getId() != personId)
                r = Response.status(Response.Status.FORBIDDEN);

        if (r == null) {
            PersonVO person = null;
            //TODO get person from DB

            if (person == null)
                r = Response.noContent();
            else
                r = Response.ok(serialize(person));
        }

        return r.build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPerson(@Context UriInfo uriInfo, @QueryParam("person_json") String personJson) {
        Response.ResponseBuilder r;
        try {
            PersonVO person = deserialize(personJson);
            //TODO save entity here
            String personUri = uriInfo.getRequestUri().toString() + "/" + person.getId();
            r = Response.created(new URI(personUri));
        } catch (JSONException e) {
            System.out.println(e);
            r = Response.status(Response.Status.NOT_ACCEPTABLE);
        } catch (URISyntaxException e) {
            //this shouldn't happen
            r = Response.serverError();
        }

        return r.build();
    }

    @DELETE
    @Path("{person_id}")
    @Auth(roles = {AuthRole.TEACHER, AuthRole.STUDENT})
    public Response deletePerson(@HeaderParam(Config.REQUEST_HEADER_ID) long userId, @PathParam("person_id") long personId) {
        Response.ResponseBuilder r = null;
        PersonVO client = new PersonVO();
        //TODO get client

        if (client.getPersonRole() != new PersonRoleVO()) //FIXME if person role not equals teacher
            if (client.getId() != personId)
                r = Response.status(Response.Status.FORBIDDEN);

        if (r == null) {
            PersonVO person = null;

            try {
                //TODO delete entity here
                r = Response.ok();
            } catch (Exception e) {
                r = Response.noContent();
            }
        }

        return r.build();
    }
}
