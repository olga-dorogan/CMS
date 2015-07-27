package org.javatraining.ws.service;

import flexjson.JSONException;
import org.javatraining.auth.Auth;
import org.javatraining.config.AuthRole;
import org.javatraining.config.Config;
import org.javatraining.entity.enums.PersonRole;
import org.javatraining.model.MarkVO;
import org.javatraining.model.PersonVO;
import org.javatraining.service.PersonService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by asudak on 6/11/15.
 */
@Path("person")
public class MarkWebService extends AbstractWebService<MarkVO> {
    @EJB
    private PersonService personService;

    public MarkWebService() {
        super(MarkVO.class);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{person_id}/mark")
    @Auth(roles = {AuthRole.TEACHER, AuthRole.STUDENT})
    public Response getMarks(@HeaderParam(Config.REQUEST_HEADER_ID) long clientId, @PathParam("person_id") long personId) {
        Response.ResponseBuilder r = null;
        PersonVO client = personService.getById(clientId);

        if (client == null)
            return Response.status(Response.Status.FORBIDDEN).build();

        if (client.getPersonRole() != PersonRole.TEACHER)
            if (client.getId() != personId)
                r = Response.status(Response.Status.FORBIDDEN);

        if (r == null) {
            PersonVO person = personService.getById(personId);

            if (person == null)
                r = Response.noContent();
            else
                r = Response.ok(serialize(personService.getMarks(person)));
        }

        return r.build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Auth(roles = {AuthRole.TEACHER})
    @Path("{person_id}/marks")
    public Response setMarks(@PathParam("person_id") long personId, List<MarkVO> marks) {
        PersonVO personVO = new PersonVO(personId);
        personService.setMarks(personVO, marks);
        return Response.accepted().build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Auth(roles = {AuthRole.TEACHER})
    @Path("{person_id}/mark/{mark_id}")
    public Response updateMark(@PathParam("person_id") long personId, @PathParam("mark_id") long markId, @QueryParam("mark_json") String markJson) {
        Response.ResponseBuilder r;
        PersonVO person = new PersonVO();
        person.setId(personId);
        try {
            MarkVO mark = deserialize(markJson);
            mark.setId(markId);
            // FIXME: changes in PersonService
//            personService.updateMark(person, mark);
            r = Response.ok();
        } catch (JSONException e) {
            r = Response.status(Response.Status.NOT_ACCEPTABLE);
        }
        return r.build();
    }
}
