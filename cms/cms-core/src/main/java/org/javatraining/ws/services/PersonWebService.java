package org.javatraining.ws.services;

import flexjson.JSONException;
import org.javatraining.auth.Auth;
import org.javatraining.config.AuthRole;
import org.javatraining.config.Config;
import org.javatraining.entity.PersonRole;
import org.javatraining.model.PersonVO;
import org.javatraining.service.PersonService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.validation.ValidationException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by asudak on 5/29/15.
 */
@Stateless
@Path("person")
public class PersonWebService extends AbstractWebService<PersonVO> {
    @EJB
    private PersonService personService;

    public PersonWebService() {
        super(PersonVO.class);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{person_id}")
    @Auth(roles = {AuthRole.TEACHER, AuthRole.STUDENT})
    public Response getPerson(@HeaderParam(Config.REQUEST_HEADER_ID) long clientId, @PathParam("person_id") long personId) {
        Response.ResponseBuilder r = null;
        PersonVO client = personService.getById(clientId);

        if (client.getPersonRole() != PersonRole.TEACHER)
            if (client.getId() != personId)
                r = Response.status(Response.Status.FORBIDDEN);

        if (r == null) {
            PersonVO person = personService.getById(personId);

            if (person == null)
                r = Response.noContent();
            else
                r = Response.ok(serialize(person));
        }

        return r.build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPerson(@Context UriInfo uriInfo, PersonVO person) {
        Response.ResponseBuilder r;
        try {
            personService.save(person);
            String personUri = uriInfo.getRequestUri().toString() + "/" + person.getId();
            r = Response.created(new URI(personUri)).entity(person);
        } catch (JSONException e) {
            r = Response.status(Response.Status.NOT_ACCEPTABLE);
        } catch (URISyntaxException e) {
            //this shouldn't happen
            r = Response.serverError();
        } catch (ValidationException e) {
            r = Response.status(422); //422 Unprocessable Entity
        }

        return r.build();
    }

    @PUT
    @Path("{person_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Auth(roles = {AuthRole.TEACHER, AuthRole.STUDENT})
    public Response updatePerson(@HeaderParam(Config.REQUEST_HEADER_ID) long clientId, @PathParam("person_id") long personId, @QueryParam("person_json") String personJson) {
        Response.ResponseBuilder r = null;
        PersonVO client = personService.getById(clientId);

        if (client.getPersonRole() != PersonRole.TEACHER)
            if (client.getId() != personId)
                r = Response.status(Response.Status.FORBIDDEN);

        if (r == null) {
            PersonVO person = deserialize(personJson);
            personService.update(person);
            r = Response.ok();
        }

        return r.build();
    }

    @DELETE
    @Path("{person_id}")
    @Auth(roles = {AuthRole.TEACHER, AuthRole.STUDENT})
    public Response deletePerson(@HeaderParam(Config.REQUEST_HEADER_ID) long clientId, @PathParam("person_id") long personId) {
        Response.ResponseBuilder r = null;
        PersonVO client = personService.getById(clientId);

        if (client.getPersonRole() != PersonRole.TEACHER)
            if (client.getId() != personId)
                r = Response.status(Response.Status.FORBIDDEN);

        if (r == null) {

            try {
                PersonVO person = new PersonVO();
                person.setId(personId);
                personService.remove(person);
                r = Response.ok();
            } catch (Exception e) {
                r = Response.noContent();
            }
        }

        return r.build();
    }
}
